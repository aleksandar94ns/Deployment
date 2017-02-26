package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Guest;
import com.ftn.model.GuestReservation;
import com.ftn.model.Order;
import com.ftn.model.OrderItem;
import com.ftn.repository.GuestReservationDao;
import com.ftn.repository.OrderDao;
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

/**
 * Created by Boki on 2/22/2017.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final UserDao userDao;

    private final OrderDao orderDao;

    private final GuestReservationDao guestReservationDao;

    @Autowired
    public OrderController(UserDao userDao, OrderDao orderDao, GuestReservationDao guestReservationDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.guestReservationDao = guestReservationDao;
    }

    @Transactional
    @PreAuthorize("hasAuthority('GUEST')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Order order) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest guest = userDao.findByEmail(authentication.getName());
        order.setStatus("ORDERED");
        final GuestReservation guestReservation = guestReservationDao.findByGuestIdAndReservationId(guest.getId(),
                order.getReservation().getId()).orElseThrow(BadRequestException::new);
        order.getOrderItems().forEach(orderItem -> {
            orderItem.setStatus(OrderItem.Status.ORDERED);
            orderItem.setGuestReservation(guestReservation);
        });
        orderDao.save(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
