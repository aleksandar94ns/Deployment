package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Manager;
import com.ftn.model.User;
import com.ftn.model.Waiter;
import com.ftn.model.Restaurant;
import com.ftn.repository.AreaDao;
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

/**
 * Created by Alek on 2/20/2017.
 */
@RestController
@RequestMapping("/api/users/waiters")
public class WaitersController {

    private final UserDao userDao;

    private final BCryptPasswordEncoder encoder;

    private final RestaurantDao restaurantDao;

    private final WaiterDao waiterDao;

    private final AreaDao areaDao;

    @Autowired
    public WaitersController(UserDao userDao, BCryptPasswordEncoder encoder, RestaurantDao restaurantDao, WaiterDao waiterDao, AreaDao areaDao) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.restaurantDao = restaurantDao;
        this.waiterDao = waiterDao;
        this.areaDao = areaDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(waiterDao.findByRestaurantIdAndRole(restaurant.getId(), User.Role.WAITER), HttpStatus.OK);
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
        if (waiter.getArea() == null) {
            throw new BadRequestException();
        }
        areaDao.findById(waiter.getArea().getId()).orElseThrow(BadRequestException::new);
        waiter.setRole(User.Role.WAITER);
        waiter.setPassword(encoder.encode(waiter.getPassword()));
        waiter.setEnabled(true);
        userDao.save(waiter);
        return new ResponseEntity<>(waiter, HttpStatus.CREATED);
    }
}
