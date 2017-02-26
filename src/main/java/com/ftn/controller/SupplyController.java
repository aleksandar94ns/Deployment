package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Manager;
import com.ftn.model.Restaurant;
import com.ftn.model.Supply;
import com.ftn.model.User;
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

/**
 * Created by Alex on 2/25/17.
 */
@RestController
@RequestMapping("/api/supplies")
public class SupplyController {
    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final SupplyDao supplyDao;

    @Autowired
    public SupplyController(UserDao userDao, RestaurantDao restaurantDao, SupplyDao supplyDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.supplyDao = supplyDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            final Manager manager = userDao.findByEmail(authentication.getName());
            final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
            return new ResponseEntity<>(supplyDao.findByRestaurantId(restaurant.getId()), HttpStatus.OK);
        }
        catch (Exception e) {
            if (userDao.findByEmail(authentication.getName()).getRole().equals(User.Role.SELLER)) {
                return new ResponseEntity<>(supplyDao.findAll(), HttpStatus.OK);
            }
            else {
                throw new BadRequestException();
            }
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
