package com.ftn.integration;

import com.ftn.model.Shift;
import com.ftn.model.Supply;
import com.ftn.repository.ShiftDao;
import com.ftn.repository.SupplyDao;
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
public class SupplyControllerIntegrationTest {

    private static final String BASE_URL = "/api/supplies";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SupplyDao supplyDao;

    @Test
    @Transactional
    @WithMockUser(roles = "MANAGER")
    public void getAllSuppliesManager() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("res@gmail.com")
                        .authorities(new SimpleGrantedAuthority("MANAGER"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(roles = "SELLER")
    public void getAllSuppliesSeller() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("sel1@gmail.com")
                        .authorities(new SimpleGrantedAuthority("SELLER"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void createSupplyForbidden() throws Exception {
        final Supply supply = supplyDao.findByName("Salt");
        mockMvc.perform(post(BASE_URL)
                .with(user("sel@gmail.com")
                        .authorities(new SimpleGrantedAuthority("SELLER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(supply)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createSupply() throws Exception {
        final Supply supply = supplyDao.findByName("Salt");
        mockMvc.perform(post(BASE_URL)
                .with(user("res@gmail.com")
                        .authorities(new SimpleGrantedAuthority("MANAGER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(supply)))
                .andExpect(status().isCreated());
    }
}
