package com.ftn.controller;

import com.ftn.dto.CreateReservationDTO;
import com.ftn.exception.BadRequestException;
import com.ftn.exception.ForbiddenException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.GuestReservationDao;
import com.ftn.repository.ReservationDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.UserDao;
import com.ftn.service.MailService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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

    private final MailService mailService;

    @Autowired
    public ReservationController(ReservationDao reservationDao, UserDao userDao, GuestReservationDao guestReservationDao, RestaurantDao restaurantDao, MailService mailService) {
        this.reservationDao = reservationDao;
        this.userDao = userDao;
        this.guestReservationDao = guestReservationDao;
        this.restaurantDao = restaurantDao;
        this.mailService = mailService;
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity readManager() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(reservationDao.findByRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('GUEST')")
    @RequestMapping(method = RequestMethod.GET, value = "/me")
    public ResponseEntity read() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest guest = userDao.findByEmail(authentication.getName());
        return new ResponseEntity<>(guestReservationDao.findByGuestId(guest.getId()), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public synchronized ResponseEntity create(@RequestBody CreateReservationDTO createReservationDTO, HttpServletRequest request) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest reservationOwner = userDao.findByEmail(authentication.getName());
        final List<Guest> guests = new ArrayList<>();
        final Reservation reservation = createReservationDTO.getReservation();
        final List<Reservation> existingReservationsAtTime = reservationDao.findByArrivalDateLessThanEqualAndDepartureDateGreaterThanEqual(reservation.getDepartureDate(), reservation.getArrivalDate());
        existingReservationsAtTime.forEach(existingReservationAtTime ->
                existingReservationAtTime.getRestaurantTables().forEach(restaurantTable -> {
                    if (reservation.getRestaurantTables().contains(restaurantTable)) {
                        throw new ForbiddenException();
                    }
                }));
        createReservationDTO.getGuests().forEach(guest -> {
            guests.add(userDao.findById(guest.getId()));
            mailService.sendReservationInvitationMail(request, guest.getEmail());
        });
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
