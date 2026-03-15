package com.example.bankcards.service;

import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.enums.RoleName;
import com.example.bankcards.entity.model.User;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.exception.UnauthorizedException;
import com.example.bankcards.mappers.UserMapper;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final SecurityUtils securityUtils;

    public UserResponse makeAdmin(Long userId) {
        checkAdmin();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> ResourceNotFoundException.forUser(userId));

        var adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        if (!user.getRoles().contains(adminRole)) {
            user.getRoles().add(adminRole);
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deactivateUser(Long userId) {
        checkAdmin();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> ResourceNotFoundException.forUser(userId));

        user.setRoles(List.of());
        userRepository.save(user);
    }

    public long getTotalUsers() {
        checkAdmin();
        return userRepository.count();
    }

    private void checkAdmin() {
        if (!securityUtils.isAdmin()) {
            throw new UnauthorizedException("Admin access required");
        }
    }
}