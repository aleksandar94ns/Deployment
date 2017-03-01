package com.ftn.integration;

import com.ftn.model.Restaurant;
import com.ftn.model.RestaurantType;
import com.ftn.repository.RestaurantDao;
import com.ftn.repository.RestaurantTypeDao;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Alek on 3/1/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class RestaurantControllerIntegrationTest {

    private static final String BASE_URL = "/api/restaurants";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestaurantDao restaurantDao;

    @Test
    @Transactional
    @WithMockUser(roles = "GUEST")
    public void getAllRestaurants() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void createRestaurantForbidden() throws Exception {
        final Restaurant restaurant = restaurantDao.findByName("KFC");
        mockMvc.perform(post(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(restaurant)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createRestaurantManagerForbidden() throws Exception {
        final Restaurant restaurant = restaurantDao.findByName("KFC");
        mockMvc.perform(post(BASE_URL)
                .with(user("res@gmail.com")
                        .authorities(new SimpleGrantedAuthority("MANAGER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(restaurant)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createRestaurant() throws Exception {
        final Restaurant restaurant = restaurantDao.findByName("KFC");
        mockMvc.perform(post(BASE_URL)
                .with(user("sys@gmail.com")
                        .authorities(new SimpleGrantedAuthority("SYSTEM_MANAGER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(restaurant)))
                .andExpect(status().isCreated());
    }
}
