package com.example.usersapi.util;

import com.example.usersapi.model.User;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtUtilTest {
    private User testUser;

    @InjectMocks
    private JwtUtil jwtUtil;

    @InjectMocks
    private User user;

    @MockBean
    private JwtUtil jwtUtilHelper;

    @Before
    public void init() {
        testUser = new User();
        testUser.setUsername("batman");
    }

    @Test
    public void generateToken_String_Success() {
        String token = jwtUtil.generateToken(user);

        when(jwtUtilHelper.doGenerateToken(any(), anyString(), anyInt())).thenReturn("fake-token-123");

        assertEquals("fake-token-123", token );
    }

    @Test
    public void doGenerateToken_String_Success() {

    }
}
