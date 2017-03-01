package com.ftn.controller;

import com.ftn.exception.ForbiddenException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.*;
import com.ftn.repository.GuestReservationDao;
import com.ftn.repository.UserDao;
import com.ftn.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alek on 2/27/2017.
 */
@RestController
@RequestMapping("/api/guestReservations")
public class GuestReservatoinsController {

    private final GuestReservationDao guestReservationDao;

    private final UserDao userDao;

    @Autowired
    public GuestReservatoinsController(GuestReservationDao guestReservationDao, UserDao userDao) {
        this.guestReservationDao = guestReservationDao;
        this.userDao = userDao;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity read(@RequestBody Reservation reservation) {
        final List<GuestReservation> guestReservations = guestReservationDao.findByReservationId(reservation.getId());
        return new ResponseEntity<>(guestReservations, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('GUEST')")
    @DeleteMapping(value = "/{reservationId}")
    public ResponseEntity delete(@PathVariable long reservationId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest guest = userDao.findByEmail(authentication.getName());
        final GuestReservation guestReservation = guestReservationDao.findByGuestIdAndReservationId(guest.getId(), reservationId).orElseThrow(NotFoundException::new);
        final long timeDifference = DateUtils.getDateDiff(new Date(), guestReservation.getReservation().getArrivalDate(), TimeUnit.MINUTES);
        if (timeDifference < 30) {
            throw new ForbiddenException();
        }
        guestReservation.setStatus(GuestReservation.Status.CANCELED);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
