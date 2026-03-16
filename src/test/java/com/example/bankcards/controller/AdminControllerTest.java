package com.example.bankcards.controller;

import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.service.AdminService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @MockBean
    private UserService userService;

    @MockBean
    private AdminService adminService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUsersShouldReturnPageWhenAdmin() throws Exception {
        Page<UserResponse> page = new PageImpl<>(List.of(new UserResponse()));
        when(userService.getAllUsers(any())).thenReturn(page);

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void makeAdminShouldReturnUserWhenAdmin() throws Exception {
        UserResponse response = new UserResponse();
        response.setId(1L);

        when(adminService.makeAdmin(1L)).thenReturn(response);

        mockMvc.perform(put("/api/admin/users/1/role"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deactivateShouldReturnMessageWhenAdmin() throws Exception {
        mockMvc.perform(delete("/api/admin/users/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Deactivated"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllCardsShouldReturnPageWhenAdmin() throws Exception {
        Page<CardResponse> page = new PageImpl<>(List.of(new CardResponse()));
        when(cardService.getAllCardsA(any(), any(), any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/admin/cards"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void blockCardShouldReturnCardWhenAdmin() throws Exception {
        CardResponse response = new CardResponse();
        response.setId(1L);

        when(cardService.blockCard(1L)).thenReturn(response);

        mockMvc.perform(put("/api/admin/cards/1/block"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCardShouldReturnMessageWhenAdmin() throws Exception {
        mockMvc.perform(delete("/api/admin/cards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Deleted card successfully"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getStatsShouldReturnTotalUsersWhenAdmin() throws Exception {
        when(adminService.getTotalUsers()).thenReturn(5L);

        mockMvc.perform(get("/api/admin/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers").value(5L));
    }
}