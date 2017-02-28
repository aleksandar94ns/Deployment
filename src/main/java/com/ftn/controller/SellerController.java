package com.ftn.controller;

import com.ftn.model.Seller;
import com.ftn.model.User;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * Created by Alek on 2/22/2017.
 */
@RestController
@RequestMapping("/api/users/sellers")
public class SellerController {

    private final UserDao userDao;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public SellerController(UserDao userDao, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        return new ResponseEntity<>(userDao.findByRole(User.Role.SELLER), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('MANAGER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Seller seller) {
        seller.setRole(User.Role.SELLER);
        seller.setPassword(encoder.encode(seller.getPassword()));
        seller.setEnabled(true);
        userDao.save(seller);
        return new ResponseEntity<>(seller, HttpStatus.CREATED);
    }
}
