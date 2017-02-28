package com.ftn.integration;

import com.ftn.dto.ChangePasswordDTO;
import com.ftn.dto.UserPatchDTO;
import com.ftn.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.hamcrest.Matchers.is;

/**
 * Created by Alex on 2/27/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
public class UserControllerIntegrationTest {

    private static final String BASE_URL = "/api/users";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void getMeInfo() throws Exception {
        final String GET_USER_INFO = BASE_URL + "/me";
        mockMvc.perform(get(GET_USER_INFO)
                .with(user("admin@gmail.com")))
                .andExpect(status().isOk());

    }

    @Test
    @Transactional
    public void getMeInfoNoAuth() throws Exception {
        final String GET_USER_INFO = BASE_URL + "/me";
        mockMvc.perform(get(GET_USER_INFO))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void changePasswordWithCorrectOldPassword() throws Exception {
        final String CHANGE_PASSWORD = BASE_URL + "/me/changePassword";
        final ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("123", "234");
        mockMvc.perform(patch(CHANGE_PASSWORD)
                .with(user("admin@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(changePasswordDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void changePasswordWithIncorrectOldPassword() throws Exception {
        final String CHANGE_PASSWORD = BASE_URL + "/me/changePassword";
        final ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("111", "234");
        mockMvc.perform(patch(CHANGE_PASSWORD)
                .with(user("admin@gmail.com"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(changePasswordDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void changeUserLastName() throws Exception {
        final String UPDATE_INFO = BASE_URL + "/me";
        final UserPatchDTO userPatchDTO = new UserPatchDTO("Alex", "Kahriman", 0, 0);
        mockMvc.perform(patch(UPDATE_INFO)
                .with(user("akahriman@execom.eu"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.json(userPatchDTO)))
                .andExpect(jsonPath("$.firstName", is("Alex")))
                .andExpect(status().isOk());
    }


}
