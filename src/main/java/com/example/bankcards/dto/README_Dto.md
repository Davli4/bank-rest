# DTO

Классы передачи данных между слоями приложения.

## Пакет request

### LoginRequest

Запрос на аутентификацию пользователя.

**Основные компоненты:**

1. username - имя пользователя

2. password - пароль

**Валидация:**

1. @NotBlank - поля не могут быть пустыми

**Пример:**

```json
{
"username": "admin",
"password": "admin123"
}
```

### RegisterRequest

Запрос на регистрацию нового пользователя.

**Основные компоненты:**

1. username - имя пользователя (3-50 символов)
 
2. password - пароль (6-255 символов)

3. email - электронная почта

4. firstName - имя (опционально)

5. lastName - фамилия (опционально)

**Валидация:**

1. @NotBlank - обязательные поля не могут быть пустыми

2. @Size(min=3, max=50) - ограничение длины username

3. @Size(min=6, max=255) - ограничение длины password

4. @Email - проверка формата email

**Пример:**

```json
{
"username": "ivanpetrov",
"password": "secure123",
"email": "ivan@example.com",
"firstName": "Иван",
"lastName": "Петров"
}
```

### CardRequest

Запрос на создание новой карты (только для администратора).

**Основные компоненты:**

1. cardNumber - номер карты (16 цифр)

2. owner - имя владельца

3. expiryDate - срок действия

4. initialBalance - начальный баланс (опционально)

**Валидация:**

1. @Pattern(regexp = "\\d{16}") - номер карты должен содержать 16 цифр

2. @Positive - начальный баланс должен быть положительным

**Пример:**

```json
{
"cardNumber": "1234567890123456",
"owner": "Ivan Petrov",
"expiryDate": "2025-12-31",
"initialBalance": 10000.00
}
```

### TransferRequest
Запрос на перевод средств между картами пользователя.

**Основные компоненты:**

1. fromCardId - ID карты отправителя

2. toCardId - ID карты получателя

3. amount - сумма перевода

**Валидация:**

1. @NotNull - поля не могут быть null

2. @Positive - сумма должна быть положительной

**Пример:**

```json
{
"fromCardId": 1,
"toCardId": 2,
"amount": 5000.00
}
```

## Пакет response

### JwtResponse

Ответ с JWT токеном после успешной аутентификации.

**Основные компоненты:**

1. token - JWT токен

2. type - тип токена (Bearer)

3. id - ID пользователя

4. username - имя пользователя

5. email - email пользователя

6. roles - список ролей пользователя

**Пример:**

```json
{
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc3MzUwMzk4OSwiZXhwIjoxNzczNTkwMzg5fQ.a_dMm0FSwVt5fcbaew4tkMvL5xvvTEwx7CaUvt-Bv2k",
"type": "Bearer",
"id": 1,
"username": "admin",
"email": "admin@bank.com",
"roles": ["ROLE_ADMIN", "ROLE_USER"]
}
```

### UserResponse

Ответ с данными пользователя.

**Основные компоненты:**

1. id - ID пользователя

2. username - имя пользователя

3. email - электронная почта

4. firstName - имя

5. lastName - фамилия

6. roles - список ролей

7. createdAt - дата регистрации

**Пример:**

```json
{
"id": 1,
"username": "admin",
"email": "admin@bank.com",
"firstName": "Admin",
"lastName": "User",
"roles": ["ROLE_ADMIN", "ROLE_USER"],
"createdAt": "2024-01-01T10:00:00"
}
```

### CardResponse

Ответ с данными карты.

**Основные компоненты:**

1. id - ID карты

2. cardNumber - полный номер карты (только для администратора)

3. maskedCardNumber - замаскированный номер

4. owner - имя владельца

5. expiryDate - срок действия

6. status - статус карты

7. balance - текущий баланс

**Пример:**

```json
{
"id": 1,
"cardNumber": "1234567890123456",
"maskedCardNumber": "**** **** **** 3456",
"owner": "Ivan Petrov",
"expiryDate": "2025-12-31",
"status": "ACTIVE",
"balance": 15000.50
}
```

### BalanceResponse
Ответ с балансом карты.

**Основные компоненты:**

1. cardId - ID карты

2. maskedCardNumber - замаскированный номер карты

3. balance - текущий баланс

**Пример:**

```json
{
"cardId": 1,
"maskedCardNumber": "**** **** **** 3456",
"balance": 15000.50
}
```

### MessageResponse
Универсальный ответ с текстовым сообщением.

**Основные компоненты:**

1. message - текст сообщения

**Пример:**

```json
{
"message": "Transfer successful"
}
```