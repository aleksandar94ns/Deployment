package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.*;
import com.ftn.repository.DrinkCardDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Alek on 2/23/2017.
 */
@RestController
@RequestMapping("/api/drinkCards")
public class DrinkCardController {

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final DrinkCardDao drinkCardDao;

    @Autowired
    public DrinkCardController(UserDao userDao, RestaurantDao restaurantDao, DrinkCardDao drinkCardDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.drinkCardDao = drinkCardDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(drinkCardDao.findByRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/restaurant/{restaurantId}")
    public ResponseEntity read(@PathVariable Long restaurantId) {
        restaurantDao.findById(restaurantId).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(drinkCardDao.findByRestaurantId(restaurantId), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody DrinkCard drinkCard) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        drinkCard.setRestaurant(manager.getRestaurant());
        if (drinkCard.getRestaurant() == null) {
            throw new BadRequestException();
        }
        restaurantDao.findById(drinkCard.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        drinkCardDao.save(drinkCard);
        return new ResponseEntity<>(drinkCard, HttpStatus.CREATED);
    }
}
