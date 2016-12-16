package com.ftn.controller;

import com.ftn.exception.NotFoundException;
import com.ftn.model.Guest;
import com.ftn.model.Reservation;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 12/9/16.
 */
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    UserDao userDao;

    @Autowired
    RestaurantDao restaurantDao;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read() {
        return new ResponseEntity<>(restaurantDao.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/me")
    public ResponseEntity getVisitedRestaurants() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest user = userDao.findByEmail(authentication.getName());
        if (user == null) {
            throw new NotFoundException();
        }
        final List<Reservation> reservations = new ArrayList<>(user.getReservations());
        return new ResponseEntity<>(reservations.stream().map(Reservation::getRestaurant).collect(Collectors.toList()), HttpStatus.OK);
    }
}
