package com.ftn.controller;

import com.ftn.dto.ChangePasswordDTO;
import com.ftn.dto.UserPatchDTO;
import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Guest;
import com.ftn.model.User;
import com.ftn.protocol.HasUniform;
import com.ftn.repository.UserDao;
import com.ftn.service.MailService;
import com.google.common.base.Strings;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Created by Alex on 10/28/16.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserDao userDao;

    private final BCryptPasswordEncoder encoder;

    private final MailService mailService;

    @Autowired
    public UserController(UserDao userDao, BCryptPasswordEncoder encoder, MailService mailService) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.mailService = mailService;
    }

    @Transactional
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

    @Transactional
    @RequestMapping(method = RequestMethod.GET, value = "/guests/{confirmationCode}")
    public ResponseEntity verify(@PathVariable String confirmationCode) {
        final Guest guest = userDao.findByConfirmationCode(confirmationCode);
        if (guest == null) {
            throw new NotFoundException();
        }
        guest.setEnabled(true);
        userDao.save(guest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/me")
    public ResponseEntity getProfile() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userDao.findByEmail(authentication.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PATCH, value = "/me/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userDao.findByEmail(authentication.getName());
        if (Strings.isNullOrEmpty(changePasswordDTO.getOldPassword()) || Strings.isNullOrEmpty(changePasswordDTO.getPassword())) {
            throw new BadRequestException();
        }
        if (!encoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new com.ftn.exception.AuthenticationException();
        }
        user.setPassword(encoder.encode(changePasswordDTO.getPassword()));
        userDao.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PATCH, value = "/me")
    public ResponseEntity update(@RequestBody UserPatchDTO userPatchDTO) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userDao.findByEmail(authentication.getName());
        user.setFirstName(userPatchDTO.getFirstName());
        user.setLastName(userPatchDTO.getLastName());
        if (user instanceof HasUniform) {
            ((HasUniform) user).setDressSize(userPatchDTO.getDressSize());
            ((HasUniform) user).setFootwearSize(userPatchDTO.getFootwearSize());
        }
        userDao.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
