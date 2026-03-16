# Обработка исключений

**Глобальный обработчик ошибок и пользовательские исключения.**

## Классы исключений
### ResourceNotFoundException

Исключение выбрасывается при попытке доступа к несуществующему ресурсу (пользователь, карта).

Статус ответа: `404 Not Found`

Статические фабричные методы:

`forCard(Long id) `- карта не найдена по ID

`forUser(Long id)` - пользователь не найден по ID

`forCardNumber(String cardNumber)` - карта не найдена по номеру

**Пример ответа:**


```json
{
"status": 404,
"message": "Card not found with id: 999",
"timestamp": "2024-01-01T10:00:00"
}
```

### UnauthorizedException

Исключение выбрасывается при попытке доступа к ресурсу без необходимых прав.

Статус ответа: `403 Forbidden`

Статические фабричные методы:

`notYourCard()` - попытка доступа к чужой карте

`adminOnly()` - попытка выполнения операции, требующей прав администратора

**Пример ответа:**


```json
{
"status": 403,
"message": "You don't have permission to access this card",
"timestamp": "2024-01-01T10:00:00"
}
```

### CardBlockedException

Исключение выбрасывается при попытке операции с заблокированной картой.

Статус ответа: `400 Bad Request`

Статические фабричные методы:

`forTransfer(Long cardId)` - попытка перевода с заблокированной карты

**Пример ответа:**


```json
{
"status": 400,
"message": "Card 1 is blocked. Cannot perform transfer",
"timestamp": "2024-01-01T10:00:00"
}
```

### InsufficientFundsException

Исключение выбрасывается при недостаточном балансе для выполнения перевода.

Статус ответа: `400 Bad Request`

Статические фабричные методы:

`forTransfer(BigDecimal balance, BigDecimal amount)` - недостаточно средств для перевода

**Пример ответа:**


```json
{
"status": 400,
"message": "Insufficient funds. Balance: 1000.00, Requested: 2000.00",
"timestamp": "2024-01-01T10:00:00"
}
```

## Глобальный обработчик
### GlobalExceptionHandler

Централизованный обработчик исключений для всего приложения.

Основные компоненты:

`handleNotFound` - обработка ResourceNotFoundException (404)

`handleValidationExceptions` - обработка ошибок валидации (400)

`handleBadCredentials`- обработка неверных учетных данных (401)

`handleUnauthorized` - обработка UnauthorizedException (403)

`handleCardBlocked` - обработка CardBlockedException (400)

`handleInsufficientFunds` - обработка InsufficientFundsException (400)

`handleGeneric` - обработка всех остальных исключений (500)

Структура ответа **ErrorResponse**:

`status (int)` - HTTP статус код

`message (String)` - сообщение об ошибке

`timestamp (LocalDateTime)` - время возникновения ошибки

**Пример ответа при ошибке валидации:**


```json
{
"username": "Username is already in use",
"email": "Invalid email format"
}
```

**Пример ответа при внутренней ошибке сервера:**


```json
{
"status": 500,
"message": "An unexpected error occurred",
"timestamp": "2024-01-01T10:00:00"
}
```