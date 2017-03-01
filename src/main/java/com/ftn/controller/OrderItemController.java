package com.ftn.controller;

import com.ftn.exception.NotFoundException;
import com.ftn.model.Manager;
import com.ftn.model.OrderItem;
import com.ftn.model.Restaurant;
import com.ftn.repository.OrderItemDao;
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
 * Created by Alek on 3/1/2017.
 */
@RestController
@RequestMapping("/api/orderItems")
public class OrderItemController {

    @Autowired
    UserDao userDao;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(NotFoundException::new);
        final List<OrderItem> orderItemList = orderItemDao.findByGuestReservationReservationRestaurantId(restaurant.getId());
        return new ResponseEntity<>(orderItemList, HttpStatus.OK);
    }
}
