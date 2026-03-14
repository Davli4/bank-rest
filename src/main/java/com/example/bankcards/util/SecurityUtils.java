package com.example.bankcards.util;

import com.example.bankcards.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public UserDetailsImpl getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetailsImpl)) {
            throw new RuntimeException("Invalid user principal");
        }

        return (UserDetailsImpl) principal;
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public boolean isAdmin() {
        return getCurrentUser().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public void checkCardOwnership(Long cardOwnerId) {
        Long currentUserId = getCurrentUserId();

        if (!currentUserId.equals(cardOwnerId) && !isAdmin()) {
            throw new RuntimeException("You don't have permission to access this card");
        }
    }
}