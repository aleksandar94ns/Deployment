package com.ftn.controller;

import com.ftn.model.Area;
import com.ftn.model.Reservation;
import com.ftn.repository.AreaDao;
import com.ftn.repository.ReservationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * Created by Alex on 2/24/17.
 */
@RestController
@RequestMapping("/api/areas")
public class AreaController {

    private final AreaDao areaDao;

    private final ReservationDao reservationDao;

    @Autowired
    public AreaController(AreaDao areaDao, ReservationDao reservationDao) {
        this.areaDao = areaDao;
        this.reservationDao = reservationDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        return new ResponseEntity<>(areaDao.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/restaurant/{id}")
    public ResponseEntity read(@PathVariable long id){
        return new ResponseEntity<>(areaDao.findByRestaurantId(id), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/available")
    public ResponseEntity read(@RequestParam("restaurant") long restaurantId, @RequestParam("arrivalDate") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.S") Date arrivalDate, @RequestParam("departureDate") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.S") Date departureDate) {
        final List<Area> areas = areaDao.findByRestaurantId(restaurantId);
        final List<Reservation> reservations = reservationDao.findByArrivalDateLessThanEqualAndDepartureDateGreaterThanEqual(departureDate, arrivalDate);
        return new ResponseEntity<>(areas, HttpStatus.OK);
    }
}
