package com.ftn.controller;

import com.ftn.model.Guest;
import com.ftn.model.Reservation;
import com.ftn.repository.ReservationDao;
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

import java.util.Collections;

/**
 * Created by Alex on 2/26/17.
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    private final UserDao userDao;

    @Autowired
    public ReservationController(ReservationDao reservationDao, UserDao userDao) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Reservation reservation) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest guest = userDao.findByEmail(authentication.getName());
        reservation.setGuests(Collections.singletonList(guest));
        reservationDao.save(reservation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
