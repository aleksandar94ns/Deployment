package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Guest;
import com.ftn.model.GuestReservation;
import com.ftn.model.Reservation;
import com.ftn.model.Restaurant;
import com.ftn.repository.GuestReservationDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.RestaurantTypeDao;
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
import java.util.stream.Collectors;

/**
 * Created by Alex on 12/9/16.
 */
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final RestaurantTypeDao restaurantTypeDao;

    private final GuestReservationDao guestReservationDao;

    @Autowired
    public RestaurantController(UserDao userDao, RestaurantDao restaurantDao, RestaurantTypeDao restaurantTypeDao, GuestReservationDao guestReservationDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.restaurantTypeDao = restaurantTypeDao;
        this.guestReservationDao = guestReservationDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read() {
        return new ResponseEntity<>(restaurantDao.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/me")
    public ResponseEntity getVisitedRestaurants() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest user = userDao.findByEmail(authentication.getName());
        if (user == null) {
            throw new NotFoundException();
        }
        final List<GuestReservation> guestReservations = new ArrayList<>(guestReservationDao.findByGuestId(user.getId()));
        return new ResponseEntity<>(guestReservations.stream().map(GuestReservation::getReservation).map(Reservation::getRestaurant).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SYSTEM_MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody  Restaurant restaurant) {
        if (restaurant.getRestaurantType() == null) {
            throw new BadRequestException();
        }
        restaurantTypeDao.findByIdAndNameAndDescription(restaurant.getRestaurantType().getId(),
                restaurant.getRestaurantType().getName(), restaurant.getRestaurantType().getDescription()).orElseThrow(BadRequestException::new);
        restaurantDao.save(restaurant);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
