package com.ftn.controller;

import com.ftn.dto.CreateReservationDTO;
import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.GuestReservationDao;
import com.ftn.repository.ReservationDao;
import com.ftn.repository.RestaurantDao;
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

    private final RestaurantDao restaurantDao;

    @Autowired
    public ReservationController(ReservationDao reservationDao, UserDao userDao, GuestReservationDao guestReservationDao, RestaurantDao restaurantDao) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
        this.guestReservationDao = guestReservationDao;
        this.restaurantDao = restaurantDao;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity readManager() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(reservationDao.findByRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/me")
    public ResponseEntity read() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest guest = userDao.findByEmail(authentication.getName());
        return new ResponseEntity<>(guestReservationDao.findByGuestId(guest.getId()), HttpStatus.OK);
    }

    @Transactional
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

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity update(@RequestBody GuestReservation guestReservation) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest guest = userDao.findByEmail(authentication.getName());
        if (guestReservation.getGuest() == null || guestReservation.getGuest().getId() != guest.getId()) {
            throw new BadRequestException();
        }
        guestReservationDao.save(guestReservation);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
