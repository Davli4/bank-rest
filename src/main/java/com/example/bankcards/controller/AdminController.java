package com.example.bankcards.controller;

import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.MessageResponse;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.service.AdminService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final CardService cardService;
    private final UserService userService;
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponse>> getUsers(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<UserResponse> makeAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.makeAdmin(id));
    }

    @DeleteMapping("/users/{id}/deactivate")
    public ResponseEntity<MessageResponse> deactivate(@PathVariable Long id) {
        adminService.deactivateUser(id);
        return  ResponseEntity.ok(new MessageResponse("Deactivated"));
    }

    @GetMapping("/cards")
    public ResponseEntity<Page<CardResponse>> getAllCards(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) CardStatus status,
            @RequestParam(required = false) String owner,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(cardService.getAllCardsA(userId, status, owner, pageable));
    }

    @PostMapping("/cards")
    public ResponseEntity<CardResponse> createCard(
            @Valid @RequestBody CardRequest request,
            @RequestParam Long userId) {
        return ResponseEntity.ok(cardService.createCard(request, userId));
    }

    @PutMapping("/cards/{id}/block")
    public ResponseEntity<CardResponse> blockCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.blockCard(id));
    }

    @PutMapping("/cards/{id}/activate")
    public ResponseEntity<CardResponse> activateCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.activateCard(id));
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<MessageResponse> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok(new MessageResponse("Deleted card successfully"));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        long totalUsers = adminService.getTotalUsers();
        return ResponseEntity.ok(Map.of(
                "totalUsers", totalUsers
        ));
    }
}
