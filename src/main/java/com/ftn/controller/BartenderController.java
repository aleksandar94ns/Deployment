package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.BartenderDao;
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
 * Created by Alek on 2/21/2017.
 */
@RestController
@RequestMapping("/api/users/bartenders")
public class BartenderController {

    private final UserDao userDao;

    private final BCryptPasswordEncoder encoder;

    private final RestaurantDao restaurantDao;

    private final BartenderDao bartenderDao;

    @Autowired
    public BartenderController(BartenderDao bartenderDao, UserDao userDao, BCryptPasswordEncoder encoder, RestaurantDao restaurantDao) {
        this.bartenderDao = bartenderDao;
        this.userDao = userDao;
        this.encoder = encoder;
        this.restaurantDao = restaurantDao;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(bartenderDao.findByRestaurantIdAndRole(restaurant.getId(), User.Role.BARTENDER), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Bartender bartender) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        bartender.setRestaurant(manager.getRestaurant());
        if (bartender.getRestaurant() == null) {
            throw new BadRequestException();
        }
        restaurantDao.findById(bartender.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        bartender.setRole(User.Role.BARTENDER);
        bartender.setPassword(encoder.encode(bartender.getPassword()));
        bartender.setEnabled(true);
        userDao.save(bartender);
        return new ResponseEntity<>(bartender, HttpStatus.CREATED);
    }
}
