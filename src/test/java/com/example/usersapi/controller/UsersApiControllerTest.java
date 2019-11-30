package com.example.usersapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import com.example.usersapi.model.User;
import com.example.usersapi.service.UserService;
import com.example.usersapi.util.JwtUtil;
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
    private UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    @InjectMocks
    private User user;

    @Test
    public void signup_Returns200_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_JSON).content(
                        "{" + "\"username\":\"user1\"," + "\"password\":\"pwd1\"," + "\"userRole\":{"
                                + "\"name\": \"ROLE_ADMIN\"}" + "}");

//        when(userService.signUpUser(any())).thenReturn("123456");

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
//                .andExpect(content().json("{\"token\":\"123456\"}"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

}
