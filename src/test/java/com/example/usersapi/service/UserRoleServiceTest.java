package com.example.usersapi.service;

import com.example.usersapi.exception.UserRoleExistsException;
import com.example.usersapi.model.UserRole;
import com.example.usersapi.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.MissingFormatArgumentException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleServiceTest {
    private UserRole tempRole;

    @InjectMocks
    private UserRoleServiceImpl userRoleService;

    @InjectMocks
    private UserRole userRole;

    @Mock
    UserRoleRepository userRoleRepository;

    @Before
    public void initUserRole() {
        tempRole = new UserRole("ROLE_TESTER");
    }

    @Test
    public void createRole_UserRole_Success() throws UserRoleExistsException {
        when(userRoleRepository.save(any())).thenReturn(tempRole);

        UserRole createdRole = userRoleService.createRole(tempRole);

        assertNotNull(createdRole);
        assertEquals(tempRole.getName(), createdRole.getName());
    }

    @Test(expected = MissingFormatArgumentException.class)
    public void createRole_InvalidRoleName_Failure() throws UserRoleExistsException {
        tempRole.setName("");
        UserRole createdRole = userRoleService.createRole(tempRole);
    }

    @Test
    public void getRole_UserRole_Success() {
        when(userRoleRepository.findByName(any())).thenReturn(tempRole);

        UserRole foundRole = userRoleService.getRole(tempRole.getName());

        assertNotNull(foundRole);
        assertEquals(tempRole, foundRole);
    }

    @Test
    public void getRole_UserRole_Failure() {
        when(userRoleRepository.findByName(any())).thenReturn(null);

        UserRole foundRole = userRoleService.getRole(tempRole.getName());

        assertNull(foundRole);
        assertEquals(null, foundRole);
    }
}
