package com.ftn.integration;

import com.ftn.model.Manager;
import com.ftn.model.Shift;
import com.ftn.repository.ShiftDao;
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
public class ShiftControllerIntegrationTest {

    private static final String BASE_URL = "/api/shifts";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShiftDao shiftDao;

    @Test
    @Transactional
    @WithMockUser(roles = "MANAGER")
    public void getAllShiftsForbidden() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("res@gmail.com")
                        .authorities(new SimpleGrantedAuthority("MANAGER"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(roles = "SYSTEM_MANAGER")
    public void getAllShifts() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("sys@gmail.com")
                        .authorities(new SimpleGrantedAuthority("SYSTEM_MANAGER"))))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createShiftForbidden() throws Exception {
        final Shift shift = shiftDao.findByName("First shift");
        mockMvc.perform(post(BASE_URL)
                .with(user("sys@gmail.com")
                        .authorities(new SimpleGrantedAuthority("SYSTEM_MANAGER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(shift)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void createShift() throws Exception {
        final Shift shift = shiftDao.findByName("Late night shift");
        mockMvc.perform(post(BASE_URL)
                .with(user("res@gmail.com")
                        .authorities(new SimpleGrantedAuthority("MANAGER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(shift)))
                .andExpect(status().isCreated());
    }
}
