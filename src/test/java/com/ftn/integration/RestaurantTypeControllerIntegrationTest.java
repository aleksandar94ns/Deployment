package com.ftn.integration;

import com.ftn.model.Guest;
import com.ftn.model.RestaurantType;
import com.ftn.repository.FriendshipDao;
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
public class RestaurantTypeControllerIntegrationTest {

    private static final String BASE_URL = "/api/restaurantTypes";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestaurantTypeDao restaurantTypeDao;

    @Test
    @Transactional
    @WithMockUser(roles = "GUEST")
    public void getAllFriendships() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST"))))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(roles = "SYSTEM_MANAGER")
    public void getAllRestaurantTypes() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("sys@gmail.eu")
                        .authorities(new SimpleGrantedAuthority("SYSTEM_MANAGER"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void createRestaurantTypeForbidden() throws Exception {
        final RestaurantType restaurantType = restaurantTypeDao.findByName("Fastfood");
        mockMvc.perform(post(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(restaurantType)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createRestaurantType() throws Exception {
        final RestaurantType restaurantType = restaurantTypeDao.findByName("Fastfood");
        mockMvc.perform(post(BASE_URL)
                .with(user("sys@gmail.com")
                        .authorities(new SimpleGrantedAuthority("SYSTEM_MANAGER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(restaurantType)))
                .andExpect(status().isCreated());
    }
}
