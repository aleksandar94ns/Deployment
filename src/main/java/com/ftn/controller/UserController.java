package com.ftn.controller;

import com.ftn.model.Guest;
import com.ftn.model.User;
import com.ftn.repository.UserDao;
import com.ftn.service.MailService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Alex on 10/28/16.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    MailService mailService;

    @RequestMapping(method = RequestMethod.POST, value = "/guests")
    public ResponseEntity create(HttpServletRequest request, @RequestBody Guest guest) {
        guest.setRole(User.Role.GUEST);
        guest.setPassword(encoder.encode(guest.getPassword()));
        guest.setEnabled(false);
        guest.setConfirmationCode(UUID.randomUUID().toString());
        userDao.save(guest);
        mailService.sendVerificationMail(request, guest.getEmail(), guest.getConfirmationCode());
        return new ResponseEntity<>(guest, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/guests/{confirmationCode}")
    public ResponseEntity verify(@PathVariable String confirmationCode) {
        final Guest guest = userDao.findByConfirmationCode(confirmationCode);
        if (guest == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        guest.setEnabled(true);
        userDao.save(guest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/me")
    public ResponseEntity getProfile() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userDao.findByEmail(authentication.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
