package com.example.usersapi.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import com.example.usersapi.model.JwtResponse;
import com.example.usersapi.model.User;
import com.example.usersapi.model.UserProfile;
import com.example.usersapi.model.UserRole;
import com.example.usersapi.service.UserProfileServiceImpl;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

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
    private UserProfileServiceImpl userProfileService;

    @MockBean
    private JwtUtil jwtUtil;

    @InjectMocks
    private User user;

    @InjectMocks
    private JwtResponse jwtResponse;

    @InjectMocks
    private UserRole userRole;

    @InjectMocks
    private UserProfile userProfile;

    private List<User> userList;

    @Before
    public void init() {
        jwtResponse.setToken("12345");
        jwtResponse.setId(1);
        jwtResponse.setUsername("testUser");

        userRole.setId(3);
        userRole.setName("ROLE_USER");

        userProfile.setId(1);
        userProfile.setUserId(1);
        userProfile.setAdditionalEmail("additionalEmail@email.com");
        userProfile.setAddress("123 Test St.");
        userProfile.setMobile("1-800-TEST-NUM");

        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("user@testmail.com");
        user.setUserRole(userRole);
        user.setUserProfile(userProfile);

        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    public void signup_ReturnsJwtResponse_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"email\":\"user@testmail.com\",\"password\":\"testPass\",\"userRole\":{\"name\": \"ROLE_USER\"}}");

        when(userService.signUpUser(any())).thenReturn(jwtResponse);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"12345\",\"username\":\"testUser\",\"id\":1}"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void login_ReturnsJwtResponse_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user@testmail.com\",\"password\":\"testPass\"}");

        when(userService.loginUser(any())).thenReturn(jwtResponse);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"12345\",\"username\":\"testUser\",\"id\":1}"))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "admin", password = "testPass", roles = {"USER"})
    public void listAll_UserList_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/list")
                .header("Authorization", "");

        when(userService.listAll()).thenReturn(userList);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"username\":\"testUser\",\"password\":\"testPass\",\"email\":\"user@testmail.com\",\"userProfile\":{\"id\":1,\"additionalEmail\":\"additionalEmail@email.com\",\"mobile\":\"1-800-TEST-NUM\",\"address\":\"123 Test St.\",\"userId\":1},\"userRole\":{\"id\":3,\"name\":\"ROLE_USER\"}}]"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void listPostsByUser_ReturnsPostList_Success() throws Exception {}

    @Test
    public void listCommentsByUser_ReturnsCommentList_Success() throws Exception {}

    @Test
    @WithMockUser(username = "testUser", password = "testPass", roles = {"ADMIN"})
    public void getUserProfile_ReturnsUserProfile_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/profile")
                .header("Authorization", "")
                .header("userId", "1");

        when(userProfileService.getUserProfile(anyInt())).thenReturn(userProfile);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"additionalEmail\":\"additionalEmail@email.com\",\"mobile\":\"1-800-TEST-NUM\",\"address\":\"123 Test St.\",\"userId\":1}"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "testUser", password = "testPass", roles = {"ADMIN"})
    public void createUserProfile_ReturnsUserProfile_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/profile")
                .header("Authorization", "")
                .header("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"additionalEmail\":\"additionalEmail@email.com\",\"address\":\"123 Test St.\",\"mobile\":\"1-800-TEST-NUM\"}");

        when(userProfileService.createProfile(any(), anyInt())).thenReturn(userProfile);

        MvcResult result = mockMvc
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"additionalEmail\":\"additionalEmail@email.com\",\"mobile\":\"1-800-TEST-NUM\",\"address\":\"123 Test St.\",\"userId\":1}\n"))
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void findUserById_ReturnsUser_Success() throws Exception {}

    @Test
    public void dummy_Test() throws Exception {
        assertEquals(2,2);
    }
}
