package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.GuestReservationDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.ShiftDao;
import com.ftn.repository.UserDao;
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
import java.util.Date;

/**
 * Created by Alek on 2/27/2017.
 */
@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final ShiftDao shiftDao;

    @Autowired
    public ShiftController(UserDao userDao, RestaurantDao restaurantDao, ShiftDao shiftDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.shiftDao = shiftDao;
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(shiftDao.findByRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Shift shift) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        shift.setDay(new Date());
        shift.setRestaurant(manager.getRestaurant());
        shiftDao.save(shift);
        return new ResponseEntity<>(shift, HttpStatus.CREATED);
    }
}
