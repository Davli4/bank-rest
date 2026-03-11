    CREATE TABLE IF NOT EXISTS users(
        id BIGSERIAL PRIMARY KEY,
        username VArCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        email VARCHAR(100) NOT NULL UNIQUE,
        first_name VARCHAR(50),
        last_name VARCHAR(50),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );


    CREATE TABLE IF NOT EXISTS roles (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(20) NOT NULL UNIQUE
    );


    CREATE TABLE IF NOT EXISTS user_roles (
        user_id BIGINT NOT NULL,
        role_id BIGINT NOT NULL,
        PRIMARY KEY (user_id, role_id),
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
        FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
    );

    CREATE TABLE IF NOT EXISTS cards (
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

    CREATE INDEX idx_cards_user_id ON cards(user_id);
    CREATE INDEX idx_cards_status ON cards(status);
    CREATE INDEX idx_cards_expiry_date ON cards(expiry_date);
    CREATE INDEX idx_users_username ON users(username);
    CREATE INDEX idx_users_email ON users(email);