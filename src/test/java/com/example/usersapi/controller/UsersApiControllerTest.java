package com.example.usersapi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.service.UserService;
import com.example.usersapi.service.UserServiceImpl;
import com.example.usersapi.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
public class UsersApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @MockBean
    JwtUtil jwtUtil;

    @InjectMocks
    private User user;

    @InjectMocks
    private JwtResponse jwtResponse;

    @Before
    public void init() {
        jwtResponse.setToken("12345");
        jwtResponse.setId(1);
        jwtResponse.setUsername("testUser");

        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("user@testmail.com");
    }

    @Test
    public void signup_Returns200_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" + "\"username\":\"testUser\"," + "\"email\":\"user@testmail.com\"," + "\"password\":\"testPass\"," + "\"userRole\":{" + "\"name\": \"ROLE_ADMIN\"}" + "}");

        when(userService.signUpUser(any())).thenReturn(jwtResponse);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"12345\",\"username\":\"testUser\",\"id\":1}"))
                .andReturn();
        System.out.println(">>>>>>>>>>>>>" + result.getResponse().getContentAsString());
    }

    @Test
    public void dummy_Test() throws Exception {
        assertEquals(2,2);
    }
}
