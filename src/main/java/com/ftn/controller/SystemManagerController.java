package com.ftn.controller;

/**
 * Created by Alek on 2/23/2017.
 */

import com.ftn.model.SystemManager;
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

@RestController
@RequestMapping("/api/users/systemManagers")
public class SystemManagerController {

    private final UserDao userDao;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public SystemManagerController(UserDao userDao, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read(){
        return new ResponseEntity<>(userDao.findByRole(User.Role.SYSTEM_MANAGER), HttpStatus.OK);
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody SystemManager systemManager) {
        systemManager.setRole(User.Role.SYSTEM_MANAGER);
        systemManager.setPassword(encoder.encode(systemManager.getPassword()));
        systemManager.setEnabled(true);
        userDao.save(systemManager);
        return new ResponseEntity<>(systemManager, HttpStatus.CREATED);
    }
}
