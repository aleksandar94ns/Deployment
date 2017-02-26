package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.BidDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Alek on 2/26/2017.
 */
@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    UserDao userDao;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    BidDao bidDao;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(bidDao.findBySupplyRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/supply/{id}")
    public ResponseEntity read(@PathVariable long id){
        return new ResponseEntity<>(bidDao.findBySupplyId(id), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/seller/{id}")
    public ResponseEntity listBySeller(@PathVariable long id){
        return new ResponseEntity<>(bidDao.findBySellerId(id), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity edit(@RequestBody Bid updatedBid) {
        final Bid bid = bidDao.findById(updatedBid.getId()).orElseThrow(NotFoundException::new);
        bidDao.save(updatedBid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

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
