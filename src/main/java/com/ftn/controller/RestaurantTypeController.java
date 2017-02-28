package com.ftn.controller;

import com.ftn.model.RestaurantType;
import com.ftn.repository.RestaurantTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/restaurantTypes")
public class RestaurantTypeController {

    private final RestaurantTypeDao restaurantTypeDao;

    @Autowired
    public RestaurantTypeController(RestaurantTypeDao restaurantTypeDao) {
        this.restaurantTypeDao = restaurantTypeDao;
    }

    @Transactional
    @PreAuthorize("hasAuthority('SYSTEM_MANAGER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read() {
        return new ResponseEntity<>(restaurantTypeDao.findAll(), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('SYSTEM_MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody RestaurantType restaurantType) {
        restaurantTypeDao.save(restaurantType);
        return new ResponseEntity<>(restaurantType, HttpStatus.CREATED);
    }
}
