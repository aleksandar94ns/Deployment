package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.*;
import com.ftn.repository.BartenderDao;
import com.ftn.repository.DrinkItemDao;
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
    DrinkItemDao drinkItemDao;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        //return new ResponseEntity<>(drinkItemDao.findByDrinkCardId(restaurant.getId()), HttpStatus.OK);
        return null;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody DrinkItem drinkItem) {
        /*bartender.setRestaurant(manager.getRestaurant());
        if (bartender.getRestaurant() == null) {
            throw new BadRequestException();
        }
        restaurantDao.findById(bartender.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        bartender.setRole(User.Role.BARTENDER);
        bartender.setPassword(encoder.encode(bartender.getPassword()));
        bartender.setEnabled(true);
        //manager.setConfirmationCode(UUID.randomUUID().toString());
        userDao.save(bartender);
        //mailService.sendVerificationMail(request, manager.getEmail(), manager.getConfirmationCode());
        return new ResponseEntity<>(drinkItem, HttpStatus.CREATED);*/
        return null;
    }
}
