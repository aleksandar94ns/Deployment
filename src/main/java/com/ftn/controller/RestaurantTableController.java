package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Manager;
import com.ftn.model.Restaurant;
import com.ftn.model.RestaurantTable;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.RestaurantTableDao;
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
 * Created by Alek on 2/26/2017.
 */
@RestController
@RequestMapping("/api/restaurantTables")
public class RestaurantTableController {

    @Autowired
    UserDao userDao;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    RestaurantTableDao restaurantTableDao;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(restaurantTableDao.findByAreaRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody RestaurantTable restaurantTable) {
        restaurantTableDao.save(restaurantTable);
        return new ResponseEntity<>(restaurantTable, HttpStatus.CREATED);
    }
}
