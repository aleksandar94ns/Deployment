package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.*;
import com.ftn.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Alek on 2/23/2017.
 */
@RestController
@RequestMapping("/api/drinkItems")
public class DrinkItemController {

    @Autowired
    UserDao userDao;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    DrinkCardDao drinkCardDao;

    @Autowired
    DrinkItemDao drinkItemDao;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        DrinkItem drinkItemList = drinkItemDao.findById((long)1);
        return new ResponseEntity<>(drinkItemDao.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody DrinkItem drinkItem) {
        drinkItemDao.save(drinkItem);
        return new ResponseEntity<>(drinkItem, HttpStatus.CREATED);
    }
}
