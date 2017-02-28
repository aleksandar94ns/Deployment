package com.ftn.integration;

import com.ftn.model.Friendship;
import com.ftn.model.Guest;
import com.ftn.repository.FriendshipDao;
import com.ftn.repository.UserDao;
import com.ftn.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

/**
 * Created by Alex on 2/27/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class FriendshipControllerIntegrationTest {

    private static final String BASE_URL = "/api/friends";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FriendshipDao friendshipDao;

    @Test
    @Transactional
    @WithMockUser(roles = "GUEST")
    public void getAllFriendships() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getAllFriendshipsByNonGuest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("res@gmail.com")
                        .authorities(new SimpleGrantedAuthority("MANAGER"))))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createFriendshipWithFriend() throws Exception {
        final Guest guest = userDao.findByEmail("aleksandar.kahriman@gmail.com");
        mockMvc.perform(post(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(guest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void createFriendship() throws Exception {
        final Guest guest = userDao.findByEmail("alek@gmail.com");
        mockMvc.perform(post(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(guest)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void acceptFriendshipRequest() throws Exception {
        final long friendshipId = 1;
        final Friendship friendship = friendshipDao.findById(friendshipId).orElseThrow(Exception::new);
        friendship.setStatus(Friendship.FriendshipStatus.ACCEPTED);
        mockMvc.perform(patch(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(friendship)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void acceptFriendshipRequestWhereNotAnInvolvedParty() throws Exception {
        final long friendshipId = 1;
        final Friendship friendship = friendshipDao.findById(friendshipId).orElseThrow(Exception::new);
        friendship.setStatus(Friendship.FriendshipStatus.ACCEPTED);
        mockMvc.perform(patch(BASE_URL)
                .with(user("alek@gmail.com")
                        .authorities(new SimpleGrantedAuthority("GUEST")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(friendship)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteFriendship() throws Exception {
        final long friendshipId = 1;
        final String DELETE_URL = BASE_URL + "/" + friendshipId;
        mockMvc.perform(delete(DELETE_URL)
                .with(user("aleksandar.kahriman@gmail.com")
                        .authorities(new SimpleGrantedAuthority("GUEST"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void deleteFriendshipWhereNotOriginator() throws Exception {
        final long friendshipId = 1;
        final String DELETE_URL = BASE_URL + "/" + friendshipId;
        mockMvc.perform(delete(DELETE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST"))))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteNonExistingFriendship() throws Exception {
        final long friendshipId = -1;
        final String DELETE_URL = BASE_URL + "/" + friendshipId;
        mockMvc.perform(delete(DELETE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST"))))
                .andExpect(status().isNotFound());
    }
}
