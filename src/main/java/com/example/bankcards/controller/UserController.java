package com.example.bankcards.controller;

import com.example.bankcards.dto.request.TransferRequest;
import com.example.bankcards.dto.response.BalanceResponse;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.dto.response.MessageResponse;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.TransferService;
import com.example.bankcards.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TransferService transferService;
    private final CardService cardService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrUser());
    }

    @GetMapping("/cards")
    public ResponseEntity<Page<CardResponse>> getMyCards(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(cardService.getCards(pageable));
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<CardResponse> getCardById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @GetMapping("/cards/{id}/balance")
    public ResponseEntity<BalanceResponse> getCardBalance(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.getCardBalance(id));
    }

    @GetMapping("/cards/status/{status}")
    public ResponseEntity<?> getCardsByStatus(@PathVariable CardStatus status) {
        return ResponseEntity.ok(cardService.getCardsByStatus(status));
    }

    @PostMapping("/cards/{id}/block")
    public ResponseEntity<CardResponse> blockCard(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.blockCard(id));
    }

    @PostMapping("/transfer")
    public ResponseEntity<MessageResponse> transfer(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.ok(transferService.transfer(request));
    }
}
