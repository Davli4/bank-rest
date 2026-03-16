# Тесты
Общие классы и конфигурации для тестирования.

## application-test.yml

Конфигурационный файл для тестовой среды. Использует H2 in-memory базу данных вместо PostgreSQL.

### База данных H2

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    username: sa
    password:
    driver-class-name: org.h2.Driver
```
**Параметры подключения:**

1. url - подключение к in-memory базе данных H2

2. mem:testdb - база данных в памяти с именем testdb

3. MODE=PostgreSQL - режим совместимости с PostgreSQL

4. DB_CLOSE_DELAY=-1 - база данных не закрывается при последнем подключении

5. DB_CLOSE_ON_EXIT=FALSE - база данных не закрывается при завершении JVM

6. username: sa - стандартный пользователь H2

7. password: - пустой пароль

8. driver-class-name - драйвер H2

**JPA для тестов**

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.H2Dialect
        format_sql: true
```
**Настройки:**

1. ddl-auto: create-drop - создание таблиц при запуске и удаление при остановке

2. show-sql: true - вывод SQL запросов в консоль

3. dialect - диалект H2 (совместимый с PostgreSQL)

4. format_sql: true - форматирование SQL запросов

**Liquibase**


```yaml
spring:
  liquibase:
    enabled: false
```

Liquibase отключен в тестах, так как Hibernate создает схему автоматически через ddl-auto.

**H2 Console**

```yaml
spring:
  h2:
    console:
      enabled: true
      path: /h2-console
```
Веб-интерфейс для просмотра и управления тестовой базой данных:

1. Доступен по адресу: http://localhost:8080/h2-console

2. Полезен для отладки тестов

**Логирование в тестах**

```yaml
logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql: TRACE
```
**Уровни логирования:**

1. org.hibernate.SQL: DEBUG - вывод всех SQL запросов

2. org.hibernate.type.descriptor.sql: TRACE - вывод параметров запросов (значения подставляемых параметров)

### Использование

**Активация тестового профиля:**


```java
@SpringBootTest
@ActiveProfiles("test")
class SomeTest {
// ттестики
}
```