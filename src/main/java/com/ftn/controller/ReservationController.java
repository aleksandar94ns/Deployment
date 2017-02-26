package com.ftn.controller;

import com.ftn.dto.CreateReservationDTO;
import com.ftn.model.Guest;
import com.ftn.model.GuestReservation;
import com.ftn.model.Reservation;
import com.ftn.repository.GuestReservationDao;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2/26/17.
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    private final UserDao userDao;

    private final GuestReservationDao guestReservationDao;

    @Autowired
    public ReservationController(ReservationDao reservationDao, UserDao userDao, GuestReservationDao guestReservationDao) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
        this.guestReservationDao = guestReservationDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody CreateReservationDTO createReservationDTO) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest reservationOwner = userDao.findByEmail(authentication.getName());
        final List<Guest> guests = new ArrayList<>();
        final Reservation reservation = createReservationDTO.getReservation();
        createReservationDTO.getGuests().forEach(guest -> guests.add(userDao.findById(guest.getId())));
        reservationDao.save(reservation);
        final GuestReservation ownerReservation = new GuestReservation(reservation, reservationOwner);
        ownerReservation.setStatus(GuestReservation.Status.ACCEPTED);
        guestReservationDao.save(ownerReservation);
        guests.forEach(guest -> guestReservationDao.save(new GuestReservation(reservation, guest)));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
