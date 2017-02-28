package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Manager;
import com.ftn.model.MenuItem;
import com.ftn.model.Restaurant;
import com.ftn.repository.*;
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

import javax.transaction.Transactional;

/**
 * Created by Alek on 2/23/2017.
 */
@RestController
@RequestMapping("/api/menuItems")
public class MenuItemController {

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final MenuItemDao menuItemDao;

    @Autowired
    public MenuItemController(UserDao userDao, RestaurantDao restaurantDao, MenuItemDao menuItemDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.menuItemDao = menuItemDao;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(menuItemDao.findByMenuRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody MenuItem menuItem) {
        menuItemDao.save(menuItem);
        return new ResponseEntity<>(menuItem, HttpStatus.CREATED);
    }
}
