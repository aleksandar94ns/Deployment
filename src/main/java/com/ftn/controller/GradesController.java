package com.ftn.controller;

import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.GradeDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alek on 2/27/2017.
 */
@RestController
@RequestMapping("/api/grades")
public class GradesController {

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final GradeDao gradeDao;

    @Autowired
    public GradesController(UserDao userDao, RestaurantDao restaurantDao, GradeDao gradeDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.gradeDao = gradeDao;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(NotFoundException::new);
        final List<Grade> grades = gradeDao.findByReservationRestaurantId(restaurant.getId());
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }
}
