package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.ChefDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.UserDao;
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

import javax.transaction.Transactional;

/**
 * Created by Alek on 2/23/2017.
 */
@RestController
@RequestMapping("/api/users/chefs")
public class ChefController {

    private final UserDao userDao;

    private final BCryptPasswordEncoder encoder;

    private final RestaurantDao restaurantDao;

    private final ChefDao chefDao;

    @Autowired
    public ChefController(UserDao userDao, BCryptPasswordEncoder encoder, RestaurantDao restaurantDao, ChefDao chefDao) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.restaurantDao = restaurantDao;
        this.chefDao = chefDao;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(chefDao.findByRestaurantIdAndRole(restaurant.getId(), User.Role.CHEF), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Chef chef) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        chef.setRestaurant(manager.getRestaurant());
        if (chef.getRestaurant() == null) {
            throw new BadRequestException();
        }
        restaurantDao.findById(chef.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        chef.setRole(User.Role.CHEF);
        chef.setPassword(encoder.encode(chef.getPassword()));
        chef.setEnabled(true);
        userDao.save(chef);
        return new ResponseEntity<>(chef, HttpStatus.CREATED);
    }
}
