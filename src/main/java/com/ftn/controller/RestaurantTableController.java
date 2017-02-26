package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Manager;
import com.ftn.model.Restaurant;
import com.ftn.model.RestaurantTable;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.RestaurantTableDao;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Alek on 2/26/2017.
 */
@RestController
@RequestMapping("/api/restaurantTables")
public class RestaurantTableController {

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final RestaurantTableDao restaurantTableDao;

    @Autowired
    public RestaurantTableController(UserDao userDao, RestaurantDao restaurantDao, RestaurantTableDao restaurantTableDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.restaurantTableDao = restaurantTableDao;
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody RestaurantTable restaurantTable) {
        restaurantTableDao.save(restaurantTable);
        return new ResponseEntity<>(restaurantTable, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.PUT, value = "/{areaId}")
    @Transactional
    public ResponseEntity create(@PathVariable long areaId, @RequestBody List<RestaurantTable> restaurantTables) {
        final List<RestaurantTable> allTables = restaurantTableDao.findByAreaId(areaId);
        allTables.forEach(restaurantTable -> restaurantTable.setActive(false));
        restaurantTableDao.save(restaurantTables);
        return new ResponseEntity<>(restaurantTables, HttpStatus.OK);
    }
}
