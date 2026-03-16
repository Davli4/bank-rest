# Репозитории
Интерфейсы Spring Data JPA для доступа к базе данных.

## Классы

### CardRepository

Репозиторий для работы с сущностью Card.

**Основные методы:**

1. `findByUserId(Long userId, Pageable pageable)` - получение всех карт пользователя с пагинацией

2. `findByIdAndUserId(Long cardId, Long userId)` - поиск карты по ID с проверкой принадлежности пользователю

3. `findByUserIdAndStatus(Long userId, CardStatus status)` - получение карт пользователя по статусу

4. `existsByCardNumber(String cardName)` - проверка существования карты по номеру

5. `findWithFilters(Long userId, CardStatus status, String owner, Pageable pageable)` - поиск карт с фильтрацией (для администратора)

**Параметры фильтрации:**

1. `userId` - ID пользователя (опционально)

2. `status` - статус карты (ACTIVE, BLOCKED, EXPIRED)

3. `owner` - имя владельца (частичное совпадение, регистронезависимое)

### UserRepository

Репозиторий для работы с сущностью **User**.

**Основные методы:**

1. `findByUsername(String username)` - поиск пользователя по имени (для аутентификации)

2. `findByEmail(String email)` - поиск пользователя по email

3. `existsByUsername(String username)` - проверка существования имени пользователя

4. `existsByEmail(String email)` - проверка существования email

### RoleRepository

Репозиторий для работы с сущностью **Role**.

**Основные методы:**

1. `findByName(RoleName role)` - поиск роли по имени