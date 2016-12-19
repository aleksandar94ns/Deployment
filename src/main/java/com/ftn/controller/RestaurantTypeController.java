package com.ftn.controller;

import com.ftn.repository.RestaurantTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurantTypes")
public class RestaurantTypeController {

    @Autowired
    RestaurantTypeDao restaurantTypeDao;

    @PreAuthorize("hasAuthority('SYSTEM_MANAGER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read() {
        return new ResponseEntity<>(restaurantTypeDao.findAll(), HttpStatus.OK);
    }
}
