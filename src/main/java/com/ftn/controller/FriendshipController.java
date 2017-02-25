package com.ftn.controller;

import com.ftn.exception.BadRequestException;
import com.ftn.exception.NotFoundException;
import com.ftn.model.Friendship;
import com.ftn.model.Guest;
import com.ftn.model.User;
import com.ftn.repository.FriendshipDao;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Alex on 12/3/16.
 */
@RestController
@RequestMapping("/api/friends")
public class FriendshipController {

    private final UserDao userDao;

    private final FriendshipDao friendshipDao;

    @Autowired
    public FriendshipController(UserDao userDao, FriendshipDao friendshipDao) {
        this.userDao = userDao;
        this.friendshipDao = friendshipDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity read() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userDao.findByEmail(authentication.getName());
        final List<Friendship> friendships = friendshipDao.findByOriginatorIdOrRecipientId(user.getId(), user.getId()).stream().filter(friendship ->
                friendship.getStatus().equals(Friendship.FriendshipStatus.PENDING) || friendship.getStatus().equals(Friendship.FriendshipStatus.ACCEPTED))
                .collect(Collectors.toList());
        return new ResponseEntity<>(friendships, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET, value = "/potential")
    public ResponseEntity readPotential() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = userDao.findByEmail(authentication.getName());
        final List<Guest> guests = userDao.findByRole(User.Role.GUEST);
        guests.removeIf(guest -> guest.getId() == user.getId());
        final List<Friendship> friendships = friendshipDao.findByOriginatorIdOrRecipientId(user.getId(), user.getId());
        friendships.forEach(friendship -> {
            if (friendship.getStatus() == Friendship.FriendshipStatus.DECLINED || friendship.getRecipient().getId() == user.getId()) {
                if (friendship.getOriginator().getId() == user.getId()) {
                    guests.removeIf(guest -> guest.getId() == friendship.getRecipient().getId());
                } else {
                    guests.removeIf(guest -> guest.getId() == friendship.getOriginator().getId());
                }
            }
        });
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Guest guest) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest originator = userDao.findByEmail(authentication.getName());
        final Guest recipient = userDao.findById(guest.getId());
        if (recipient == null) {
            throw new NotFoundException();
        }
        final Friendship friendship = new Friendship(originator, recipient);
        friendshipDao.save(friendship);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity edit(@RequestBody Friendship updatedFriendship) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest guest = userDao.findByEmail(authentication.getName());
        final Friendship friendship = friendshipDao.findById(updatedFriendship.getId());
        if (friendship == null || friendship.getRecipient().getId() != guest.getId()) {
            throw new NotFoundException();
        }
        if (updatedFriendship.getStatus() == null) {
            throw new BadRequestException();
        }
        friendship.setStatus(updatedFriendship.getStatus());
        friendshipDao.save(friendship);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Guest originator = userDao.findByEmail(authentication.getName());
        final Friendship friendship = friendshipDao.findById(id);
        if (originator.getId() != friendship.getOriginator().getId()) {
            throw new NotFoundException();
        }
        friendshipDao.delete(friendship);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
