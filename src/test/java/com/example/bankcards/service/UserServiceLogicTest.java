package com.example.bankcards.service;

import com.example.bankcards.entity.enums.RoleName;
import com.example.bankcards.entity.model.Role;
import com.example.bankcards.entity.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceLogicTest {
    private User user;
    private Role userRole;
    private Role adminRole;

    @BeforeEach
    void setUp() {
        userRole = new Role();
        userRole.setId(1L);
        userRole.setName(RoleName.ROLE_USER);

        adminRole = new Role();
        adminRole.setId(2L);
        adminRole.setName(RoleName.ROLE_ADMIN);

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@bank.com");
        user.setRoles(new ArrayList<>(List.of(userRole)));
    }

    @Test
    void hasRole_ShouldReturnTrue_WhenUserHasRole() {
        boolean hasUserRole = user.getRoles().stream().anyMatch(r -> r.getName() == RoleName.ROLE_USER);
        assertTrue(hasUserRole);
    }

    @Test
    void hasRole_ShouldReturnFalse_WhenUserDoesNotHaveRole() {
        boolean hasAdminRole = user.getRoles().stream().anyMatch(r -> r.getName() == RoleName.ROLE_ADMIN);
        assertFalse(hasAdminRole);
    }

    @Test
    void addRole_ShouldAddRole_WhenNotPresent() {
        assertFalse(user.getRoles().contains(adminRole));
        user.getRoles().add(adminRole);
        assertTrue(user.getRoles().contains(adminRole));
        assertEquals(2, user.getRoles().size());
    }

    @Test
    void removeRole_ShouldRemoveRole_WhenPresent() {
        assertEquals(1, user.getRoles().size());
        user.getRoles().removeIf(r -> r.getName() == RoleName.ROLE_USER);
        assertEquals(0, user.getRoles().size());
    }
}