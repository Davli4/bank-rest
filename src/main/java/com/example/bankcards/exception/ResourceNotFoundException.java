package com.example.bankcards.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException forCard(Long id) {
        return new ResourceNotFoundException("Card not found with id: " + id);
    }

    public static ResourceNotFoundException forUser(Long id) {
        return new ResourceNotFoundException("User not found with id: " + id);
    }

    public static ResourceNotFoundException forCardNumber(String cardNumber) {
        return new ResourceNotFoundException("Card not found with number: " + cardNumber);
    }
}