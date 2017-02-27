package com.ftn.controller;

import com.ftn.model.*;
import com.ftn.repository.GuestReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Alek on 2/27/2017.
 */
@RestController
@RequestMapping("/api/guestReservations")
public class GuestReservatoinsController {

    @Autowired
    GuestReservationDao guestReservationDao;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity read(@RequestBody Reservation reservation){
        final List<GuestReservation> guestReservations = guestReservationDao.findByReservationId(reservation.getId());
        return new ResponseEntity<>(guestReservations, HttpStatus.OK);
    }
}
