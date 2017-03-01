package com.ftn.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Alex on 3/1/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class ReservationControllerIntegrationTest {

    private static final String BASE_URL = "/api/reservations";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void getForRestaurantByManager() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("resmen1@gmail.com")
                        .authorities(new SimpleGrantedAuthority("MANAGER"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getForRestaurantByGuest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST"))))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void getMineByGuest() throws Exception {
        final String ME = BASE_URL + "/me";
        mockMvc.perform(get(ME)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getMineByAdmin() throws Exception {
        final String ME = BASE_URL + "/me";
        mockMvc.perform(get(ME)
                .with(user("admin@gmail.com")
                        .authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isForbidden());
    }
}
