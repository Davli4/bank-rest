package com.example.bankcards.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50 , message = "should be between 3 and 50 chars")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6 , max = 255, message = "should be between 6 and 255 chars")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "invalid email format")
    private String email;

    private String firstName;
    private String lastName;
}
