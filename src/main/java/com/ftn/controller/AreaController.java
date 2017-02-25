package com.ftn.controller;

import com.ftn.exception.NotFoundException;
import com.ftn.repository.AreaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Alex on 2/24/17.
 */
@RestController
@RequestMapping("/api/areas")
public class AreaController {

    @Autowired
    AreaDao areaDao;

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
}
