# Сущности
JPA-сущности: Card, User, Role и другие.

## Пакет enums

**CardStatus**

Статус банковской карты.

**Значения:**

`ACTIVE` - карта активна, доступна для использования

`BLOCKED` - карта заблокирована (пользователем или администратором)

`EXPIRED` - срок действия карты истек

**RoleName**

Названия ролей пользователей.

**Значения:**

`ROLE_ADMIN` - роль администратора (полный доступ)

`ROLE_USER` - роль обычного пользователя (ограниченный доступ)

## Пакет model

**User**

Сущность пользователя системы.

**Основные компоненты:**

1. id - уникальный идентификатор пользователя

2. username - имя пользователя (уникальное)

3. password - зашифрованный пароль

4. email - электронная почта (уникальная)

5. firstName - имя

6. lastName - фамилия

7. createdAt - дата и время создания

8. updatedAt - дата и время последнего обновления

9. roles - список ролей пользователя

10. cards - список карт пользователя

**Связи:**

`@ManyToMany` с **Role** через таблицу `user_roles`

`@OneToMany` с **Card** (один пользователь может иметь много карт)

**Индексы:**

`username` - уникальный индекс

`email` - уникальный индекс

**Пример:**


```sql
INSERT INTO users (username, password, email, first_name, last_name)
VALUES ('admin', '$2a$10$...', 'admin@bank.com', 'Admin', 'User');
```

**Role**

Сущность роли пользователя.

**Основные компоненты:**

1. id - уникальный идентификатор роли

2. name - название роли (уникальное)

**Значения в БД:**

```sql
ROLE_ADMIN

ROLE_USER
```

**Связи:**

`@ManyToMany` с **User** (многие пользователи могут иметь многие роли)

**Пример:**


```sql
INSERT INTO roles (name) VALUES ('ROLE_ADMIN'), ('ROLE_USER');
```

**Card**

Сущность банковской карты.

**Основные компоненты:**

1. id - уникальный идентификатор карты

2. cardNumber - номер карты (зашифрован, уникальный)

3. owner - имя владельца карты

4. expiryDate - срок действия карты

5. status - статус карты (`ACTIVE`, `BLOCKED`, `EXPIRED`)

6. balance - текущий баланс

7. createdAt - дата и время создания

8. updatedAt - дата и время последнего обновления

9. user - владелец карты (ссылка на **User**)

**Связи:**

`@ManyToOne` с **User** (много карт может принадлежать одному пользователю)

**Индексы:**

1. card_number - уникальный индекс

2. user_id - индекс для быстрого поиска карт пользователя

3. status - индекс для фильтрации по статусу

4. expiry_date - индекс для поиска просроченных карт

**Ограничения:**

1. status - только значения `ACTIVE`, `BLOCKED`, `EXPIRED`

2. balance - не может быть отрицательным (проверка в бизнес-логике)

**Пример:**


```sql 
INSERT INTO cards (card_number, owner, expiry_date, status, balance, user_id)
VALUES ('encrypted_value', 'Ivan Petrov', '2025-12-31', 'ACTIVE', 10000.00, 1);
```

**Схема базы данных**

Таблица `users`


```sql
CREATE TABLE users (
id BIGSERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
first_name VARCHAR(50),
last_name VARCHAR(50),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Таблица `roles`

```sql
CREATE TABLE roles (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(20) NOT NULL UNIQUE
);
Таблица user_roles (связующая)
sql
CREATE TABLE user_roles (
user_id BIGINT NOT NULL,
role_id BIGINT NOT NULL,
PRIMARY KEY (user_id, role_id),
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);
```

Таблица `cards`

```sql
CREATE TABLE cards (
id BIGSERIAL PRIMARY KEY,
card_number VARCHAR(255) NOT NULL UNIQUE,
owner VARCHAR(100) NOT NULL,
expiry_date DATE NOT NULL,
status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,
user_id BIGINT NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
CONSTRAINT check_card_status CHECK (status IN ('ACTIVE', 'BLOCKED', 'EXPIRED'))
);
```