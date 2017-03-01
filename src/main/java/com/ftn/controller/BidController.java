package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.BidDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.SupplyDao;
import com.ftn.repository.UserDao;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Alek on 2/26/2017.
 */
@RestController
@RequestMapping("/api/bids")
public class BidController {

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final SupplyDao supplyDao;

    private final BidDao bidDao;

    @Autowired
    public BidController(UserDao userDao, RestaurantDao restaurantDao, SupplyDao supplyDao, BidDao bidDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.supplyDao = supplyDao;
        this.bidDao = bidDao;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(bidDao.findBySupplyRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity read(@RequestBody Bid bid) {
        Boolean used = false;
        bid.setStatus("ACCEPTED");
        try {
            bidDao.save(bid);
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final Manager manager = userDao.findByEmail(authentication.getName());
            final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
            List<Bid> bids = bidDao.findBySupplyIdAndSupplyRestaurantId(supplyDao.findById(bid.getSupply().getId()).getId(), restaurant.getId());
            for (Bid declinedBid : bids) {
                if (declinedBid.getId() != bid.getId()) {
                    declinedBid.setStatus("DECLINED");
                    bidDao.save(declinedBid);
                }
            }
            return new ResponseEntity<>(used, HttpStatus.OK);
        } catch (Exception e) {
            used = true;
            return new ResponseEntity<>(used, HttpStatus.IM_USED);
        }
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/seller/{id}")
    public ResponseEntity listBySeller(@PathVariable long id) {
        return new ResponseEntity<>(bidDao.findBySellerId(id), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity edit(@RequestBody Bid updatedBid) {
        Boolean used = false;
        bidDao.findById(updatedBid.getId()).orElseThrow(NotFoundException::new);
        try {
            bidDao.save(updatedBid);
            return new ResponseEntity<>(used, HttpStatus.OK);
        } catch (Exception e) {
            used = true;
            return new ResponseEntity<>(used, HttpStatus.IM_USED);
        }
    }

    @Transactional
    @PreAuthorize("hasAuthority('SELLER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Bid bid) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Seller seller = userDao.findByEmail(authentication.getName());
        bid.setSeller(seller);
        bid.setTimestamp(new Date());
        bid.setStatus("PENDING");
        bidDao.save(bid);
        return new ResponseEntity<>(bid, HttpStatus.CREATED);
    }
}
