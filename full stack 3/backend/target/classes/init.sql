-- Food Waste Management System - Database Initialization Script
-- MySQL/MariaDB SQL Commands

-- ========================================
-- 1. CREATE USERS TABLE
-- ========================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'DONOR', 'RECIPIENT', 'ANALYST') NOT NULL,
    phone_number VARCHAR(20),
    organization_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_role (role)
);

-- ========================================
-- 2. CREATE DONATIONS TABLE
-- ========================================
CREATE TABLE IF NOT EXISTS donations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    donor_id BIGINT NOT NULL,
    food_type VARCHAR(100) NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit VARCHAR(20) NOT NULL DEFAULT 'kg',
    expiry_date TIMESTAMP NOT NULL,
    description VARCHAR(500),
    location VARCHAR(255) NOT NULL,
    status ENUM('AVAILABLE', 'REQUESTED', 'APPROVED', 'PICKED_UP', 'DELIVERED', 'EXPIRED', 'CANCELLED') NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (donor_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_donor_id (donor_id),
    INDEX idx_status (status),
    INDEX idx_expiry_date (expiry_date)
);

-- ========================================
-- 3. CREATE FOOD_REQUESTS TABLE
-- ========================================
CREATE TABLE IF NOT EXISTS food_requests (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    recipient_id BIGINT NOT NULL,
    donation_id BIGINT NOT NULL,
    requested_quantity INT NOT NULL CHECK (requested_quantity > 0),
    notes VARCHAR(500),
    status ENUM('PENDING', 'APPROVED', 'REJECTED', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (recipient_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (donation_id) REFERENCES donations(id) ON DELETE CASCADE,
    INDEX idx_recipient_id (recipient_id),
    INDEX idx_donation_id (donation_id),
    INDEX idx_status (status)
);

-- ========================================
-- 4. CREATE LOGISTICS TABLE
-- ========================================
CREATE TABLE IF NOT EXISTS logistics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    request_id BIGINT NOT NULL UNIQUE,
    pickup_time TIMESTAMP,
    delivery_time TIMESTAMP,
    pickup_location VARCHAR(255),
    delivery_location VARCHAR(255),
    driver_name VARCHAR(100),
    driver_phone VARCHAR(20),
    vehicle_number VARCHAR(20),
    delivery_status ENUM('PENDING', 'ASSIGNED', 'PICKED_UP', 'IN_TRANSIT', 'DELIVERED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    notes VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (request_id) REFERENCES food_requests(id) ON DELETE CASCADE,
    INDEX idx_request_id (request_id),
    INDEX idx_delivery_status (delivery_status)
);

-- ========================================
-- 5. SAMPLE DATA (Optional)
-- ========================================

-- Insert sample admin user
INSERT INTO users (name, email, password, role) 
VALUES ('Admin User', 'admin@foodshare.com', '$2a$10$slYQmyNdGzin7olVN3p5be4DlH.PKZbv5H8KnzzVgXXbVxzy2QKQM', 'ADMIN');

-- Insert sample donor user
INSERT INTO users (name, email, password, role, phone_number) 
VALUES ('John Donor', 'donor@foodshare.com', '$2a$10$slYQmyNdGzin7olVN3p5be4DlH.PKZbv5H8KnzzVgXXbVxzy2QKQM', 'DONOR', '+1234567890');

-- Insert sample recipient user
INSERT INTO users (name, email, password, role, organization_name) 
VALUES ('Sarah Recipient', 'recipient@foodshare.com', '$2a$10$slYQmyNdGzin7olVN3p5be4DlH.PKZbv5H8KnzzVgXXbVxzy2QKQM', 'RECIPIENT', 'Food Bank XYZ');

-- Insert sample analyst user
INSERT INTO users (name, email, password, role) 
VALUES ('Data Analyst', 'analyst@foodshare.com', '$2a$10$slYQmyNdGzin7olVN3p5be4DlH.PKZbv5H8KnzzVgXXbVxzy2QKQM', 'ANALYST');

-- ========================================
-- 6. USEFUL QUERIES
-- ========================================

-- Get all pending donation requests
-- SELECT * FROM food_requests WHERE status = 'PENDING';

-- Get donations by a specific donor
-- SELECT * FROM donations WHERE donor_id = 1 ORDER BY created_at DESC;

-- Get available donations
-- SELECT * FROM donations WHERE status = 'AVAILABLE' AND expiry_date > NOW();

-- Get requests with logistics status
-- SELECT fr.*, l.delivery_status FROM food_requests fr 
-- LEFT JOIN logistics l ON fr.id = l.request_id 
-- WHERE fr.status = 'APPROVED';

-- Count donations by status
-- SELECT status, COUNT(*) as count FROM donations GROUP BY status;

-- Count requests by status
-- SELECT status, COUNT(*) as count FROM food_requests GROUP BY status;

-- Get top food types donated
-- SELECT food_type, COUNT(*) as count FROM donations GROUP BY food_type ORDER BY count DESC LIMIT 10;

-- Get total users by role
-- SELECT role, COUNT(*) as count FROM users GROUP BY role;

-- Get food saved (total quantity)
-- SELECT SUM(quantity) as total_food_saved FROM donations WHERE status IN ('DELIVERED', 'APPROVED');

-- Get pending logistics
-- SELECT * FROM logistics WHERE delivery_status = 'PENDING' ORDER BY created_at ASC;
