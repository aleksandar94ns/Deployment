package com.ftn.integration;

import com.ftn.model.Area;
import com.ftn.model.Menu;
import com.ftn.repository.AreaDao;
import com.ftn.repository.MenuDao;
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
public class MenuControllerIntegrationTest {

    private static final String BASE_URL = "/api/menus";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuDao menuDao;

    @Test
    @Transactional
    @WithMockUser(roles = "GUEST")
    public void getAllMenusForbidden() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST"))))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(roles = "MANAGER")
    public void getAllMenus() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .with(user("res@gmail.com")
                        .authorities(new SimpleGrantedAuthority("MANAGER"))))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void createMenu() throws Exception {
        final Menu menu = menuDao.findByName("Soups");
        mockMvc.perform(post(BASE_URL)
                .with(user("res@gmail.com")
                        .authorities(new SimpleGrantedAuthority("MANAGER")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(menu)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void createMenuForbidden() throws Exception {
        final Menu menu = menuDao.findByName("Soups");
        mockMvc.perform(post(BASE_URL)
                .with(user("akahriman@execom.eu")
                        .authorities(new SimpleGrantedAuthority("GUEST")))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(menu)))
                .andExpect(status().isForbidden());
    }
}
