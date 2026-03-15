package com.example.bankcards.util;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidationUtils {
    public void validateTransferAmount(BigDecimal amount){
        if(amount == null){
            throw new ValidationException("Amount is null");
        }
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValidationException("Amount is negative");
        }
        if(amount.scale() > 2) {
            throw new ValidationException("Amount scale greater than 2");
        }
    }
}
