package com.ftn.controller;

import com.ftn.exception.AuthenticationException;
import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.BidDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.SupplyDao;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 2/25/17.
 */
@RestController
@RequestMapping("/api/supplies")
public class SupplyController {

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final SupplyDao supplyDao;

    private final BidDao bidDao;

    @Autowired
    public SupplyController(UserDao userDao, RestaurantDao restaurantDao, SupplyDao supplyDao, BidDao bidDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.supplyDao = supplyDao;
        this.bidDao = bidDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userDao.findByEmail(authentication.getName());
        if (user.getRole().equals(User.Role.MANAGER)) {
            final Manager manager = (Manager) user;
            final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
            return new ResponseEntity<>(supplyDao.findByRestaurantIdAndExpirationBefore(restaurant.getId(), new Date()), HttpStatus.OK);
        } else if (user.getRole().equals(User.Role.SELLER)) {
            final Seller seller = (Seller) user;
            final List<Supply> allSupplies = supplyDao.findAll();
            final List<Bid> bids = bidDao.findBySellerId(seller.getId());
            bids.forEach(bid -> allSupplies.remove(bid.getSupply()));
            return new ResponseEntity<>(allSupplies, HttpStatus.OK);
        } else {
            throw new AuthenticationException();
        }
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Supply supply) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        supply.setRestaurant(manager.getRestaurant());
        restaurantDao.findById(supply.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        supplyDao.save(supply);
        return new ResponseEntity<>(supply, HttpStatus.CREATED);
    }
}
