package com.example.bankcards.service;

import com.example.bankcards.dto.request.RegisterRequest;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.enums.RoleName;
import com.example.bankcards.entity.model.Role;
import com.example.bankcards.entity.model.User;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.exception.UnauthorizedException;
import com.example.bankcards.mappers.UserMapper;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;

    @Transactional
    public UserResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }
        User user = userMapper.toUserEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(List.of(userRole));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getCurrUser() {
        User user = findById(securityUtils.getCurrentUserId());
        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserById(Long id) {
        User user = findById(id);
        return userMapper.toUserResponse(user);
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        if (!securityUtils.isAdmin()) {
            throw new UnauthorizedException("Admin Only");
        }
        return userRepository.findAll(pageable)
                .map(userMapper::toUserResponse);
    }

    @Transactional
    public UserResponse updateUserRole(Long userId, Set<RoleName> roles) {
        if (!securityUtils.isAdmin()) {
            throw new UnauthorizedException("Admin only");
        }

        User user = findById(userId);
        List<Role> newRoles = roles.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .toList();

        user.setRoles(newRoles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getUserByEmail(String email) {
        if (!securityUtils.isAdmin()) {
            throw new UnauthorizedException("Admin only");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> ResourceNotFoundException.forCardNumber("User not found with email: " + email));

        return userMapper.toUserResponse(user);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.forUser(id));
    }
}