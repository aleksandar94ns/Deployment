package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Manager;
import com.ftn.model.Menu;
import com.ftn.model.RestaurantType;
import com.ftn.repository.RestaurantTypeDao;
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

@RestController
@RequestMapping("/api/restaurantTypes")
public class RestaurantTypeController {

    private final RestaurantTypeDao restaurantTypeDao;

    @Autowired
    public RestaurantTypeController(RestaurantTypeDao restaurantTypeDao) {
        this.restaurantTypeDao = restaurantTypeDao;
    }

    @PreAuthorize("hasAuthority('SYSTEM_MANAGER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read() {
        return new ResponseEntity<>(restaurantTypeDao.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SYSTEM_MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody RestaurantType restaurantType) {
        restaurantTypeDao.save(restaurantType);
        return new ResponseEntity<>(restaurantType, HttpStatus.CREATED);
    }
}
