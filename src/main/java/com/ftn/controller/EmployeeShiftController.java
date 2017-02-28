package com.ftn.controller;

/**
 * Created by Alek on 2/28/2017.
 */

import com.ftn.exception.BadRequestException;
import com.ftn.model.EmployeeShift;
import com.ftn.model.Manager;
import com.ftn.model.Restaurant;
import com.ftn.repository.EmployeeShiftDao;
import com.ftn.repository.GradeDao;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/employeeShifts")
public class EmployeeShiftController {

    @Autowired
    EmployeeShiftDao employeeShiftDao;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity read(){
        List<EmployeeShift> employeeShifts = employeeShiftDao.findAll();
        return new ResponseEntity<>(employeeShifts, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.PUT, value = "/{date}")
    public ResponseEntity search(@PathVariable Date date) {
        List<EmployeeShift> employeeShifts = employeeShiftDao.findAll();
        //List<EmployeeShift> employeeShifts = employeeShiftDao.findByDateBetween(new Date(), date);
        return new ResponseEntity<>(employeeShifts, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody EmployeeShift employeeShift) {
        employeeShiftDao.save(employeeShift);
        return new ResponseEntity<>(employeeShift, HttpStatus.CREATED);
    }
}
