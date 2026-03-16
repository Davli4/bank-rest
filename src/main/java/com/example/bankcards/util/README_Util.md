# Утилиты

Вспомогательные классы: шифрование, маскирование и прочее.

## Классы

### CardNumberMasker

Утилита для маскировки и валидации номеров банковских карт.

**Основные методы:**

1. `mask(String cardNumber)` - маскирует номер карты, оставляя только последние 4 цифры

2. `isValid(String cardNumber)` - проверяет, содержит ли строка 16 цифр

**Формат маскировки:**

Вход: "1234567890123456"

Выход: "**** **** **** 3456"

**Примеры использования:**


```java
CardNumberMasker masker = new CardNumberMasker();
String masked = masker.mask("1234567890123456"); // "**** **** **** 3456"
boolean valid = masker.isValid("1234567890123456"); // совпадает(true)
```

### SecurityUtils

Утилита для работы с контекстом безопасности и текущим пользователем.

**Основные методы:**

1. `getCurrentUser()` - получение текущего аутентифицированного пользователя

2. `getCurrentUserId()` - получение ID текущего пользователя

3. `isAdmin()` - проверка, является ли текущий пользователь администратором

4. `checkCardOwnership(Long cardOwnerId)` - проверка прав доступа к карте

**Исключения:**

1. `RuntimeException("User not authenticated")` - если пользователь не аутентифицирован

2. `RuntimeException("Invalid user principal")` - если принципал имеет неверный тип

3. `RuntimeException("You don't have permission to access this card")` - если нет прав на карту

**Примеры использования:**


```java
Long userId = securityUtils.getCurrentUserId();
boolean isAdmin = securityUtils.isAdmin();
securityUtils.checkCardOwnership(card.getUser().getId());
```

### ValidationUtils

Утилита для валидации входных данных.

**Основные методы:**

1. `validateTransferAmount(BigDecimal amount)` - проверка корректности суммы перевода

**Проверки суммы перевода:**

1. Сумма не должна быть `null`

2. Сумма должна быть положительной (> 0)

3. Сумма должна иметь не более 2 знаков после запятой

**Исключения:**

1. `ValidationException("Amount is null")` - если сумма `null`

2. `ValidationException("Amount is negative")` - если сумма <= 0

3. `ValidationException("Amount scale greater than 2")` - если больше 2 знаков после запятой

**Примеры использования:**


```java
validationUtils.validateTransferAmount(new BigDecimal("100.50")); // OK
validationUtils.validateTransferAmount(new BigDecimal("-10.00")); // пробросит исключенеие
validationUtils.validateTransferAmount(new BigDecimal("100.555")); // пробросит исключенеие
```