package com.example.bankcards.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public static UnauthorizedException notYourCard() {
        return new UnauthorizedException("You don't have permission to access this card");
    }

    public static UnauthorizedException adminOnly() {
        return new UnauthorizedException("This operation requires ADMIN role");
    }
}