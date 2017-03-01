package com.ftn.controller;

import com.ftn.model.Area;
import com.ftn.model.Reservation;
import com.ftn.repository.AreaDao;
import com.ftn.repository.ReservationDao;
import com.ftn.exception.BadRequestException;
import com.ftn.model.Manager;
import com.ftn.model.Restaurant;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;

/**
 * Created by Alex on 2/24/17.
 */
@RestController
@RequestMapping("/api/areas")
public class AreaController {

    private final AreaDao areaDao;

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final ReservationDao reservationDao;

    @Autowired
    public AreaController(AreaDao areaDao, UserDao userDao, RestaurantDao restaurantDao, ReservationDao reservationDao) {
        this.areaDao = areaDao;
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.reservationDao = reservationDao;
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(areaDao.findByRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/restaurant/{id}")
    public ResponseEntity read(@PathVariable long id){
        return new ResponseEntity<>(areaDao.findByRestaurantId(id), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/available")
    public ResponseEntity read(@RequestParam("restaurant") long restaurantId, @RequestParam("arrivalDate") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.S") Date arrivalDate, @RequestParam("departureDate") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.S") Date departureDate) {
        final List<Area> areas = areaDao.findByRestaurantId(restaurantId);
        final List<Reservation> reservations = reservationDao.findByArrivalDateLessThanEqualAndDepartureDateGreaterThanEqual(departureDate, arrivalDate);
        areas.forEach(area -> area.getRestaurantTables().forEach(restaurantTable -> restaurantTable.setReserved(reservations.stream().anyMatch(reservation -> reservation.getRestaurantTables().contains(restaurantTable)))));
        return new ResponseEntity<>(areas, HttpStatus.OK);

    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Area area) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        area.setRestaurant(manager.getRestaurant());
        areaDao.save(area);
        return new ResponseEntity<>(area, HttpStatus.CREATED);
    }
}
