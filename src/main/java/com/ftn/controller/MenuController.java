package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.model.Manager;
import com.ftn.model.Menu;
import com.ftn.model.Restaurant;
import com.ftn.repository.MenuDao;
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

/**
 * Created by Alek on 2/23/2017.
 */
@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final UserDao userDao;

    private final RestaurantDao restaurantDao;

    private final MenuDao menuDao;

    @Autowired
    public MenuController(UserDao userDao, RestaurantDao restaurantDao, MenuDao menuDao) {
        this.userDao = userDao;
        this.restaurantDao = restaurantDao;
        this.menuDao = menuDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        final Restaurant restaurant = restaurantDao.findById(manager.getRestaurant().getId()).orElseThrow(BadRequestException::new);
        return new ResponseEntity<>(menuDao.findByRestaurantId(restaurant.getId()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Menu menu) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Manager manager = userDao.findByEmail(authentication.getName());
        menu.setRestaurant(manager.getRestaurant());
        if (menu.getRestaurant() == null) {
            throw new BadRequestException();
        }
        menuDao.save(menu);
        return new ResponseEntity<>(menu, HttpStatus.CREATED);
    }
}
