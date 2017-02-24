package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Guest;
import com.ftn.model.Manager;
import com.ftn.model.User;
import com.ftn.model.Waiter;
import com.ftn.model.Restaurant;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.UserDao;
import com.ftn.repository.WaiterDao;
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

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Alek on 2/20/2017.
 */
@RestController
@RequestMapping("/api/users/waiters")
public class WaitersController {

    @Autowired
    UserDao userDao;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    WaiterDao waiterDao;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(waiterDao.findByRestaurantIdAndRole(restaurant.getId(), User.Role.WAITER), HttpStatus.OK);
        //return new ResponseEntity<>(waiterDao.findByRestaurant(restaurant.getId()), HttpStatus.OK);
        //return new ResponseEntity<>(userDao.findByRole(User.Role.WAITER).stream().filter(waiter -> restaurant.equals(waiterDao.findByRestaurant(restaurant.getClass().getName()))).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Waiter waiter) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        waiter.setRestaurant(manager.getRestaurant());
        if (waiter.getRestaurant() == null) {
            throw new BadRequestException();
        }
        restaurantDao.findById(waiter.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        waiter.setRole(User.Role.WAITER);
        waiter.setPassword(encoder.encode(waiter.getPassword()));
        waiter.setEnabled(true);
        //manager.setConfirmationCode(UUID.randomUUID().toString());
        userDao.save(waiter);
        //mailService.sendVerificationMail(request, manager.getEmail(), manager.getConfirmationCode());
        return new ResponseEntity<>(waiter, HttpStatus.CREATED);
    }
}
