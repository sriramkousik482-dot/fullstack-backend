-- ========================================
-- FOOD WASTE MANAGEMENT SYSTEM
-- Useful SQL Queries for MySQL Workbench
-- ========================================

USE foodshare;

-- ========================================
-- 1. USER QUERIES
-- ========================================

-- Get all users
SELECT id, name, email, role, created_at FROM users;

-- Get all donors
SELECT * FROM users WHERE role = 'DONOR';

-- Get all recipients
SELECT * FROM users WHERE role = 'RECIPIENT';

-- Get all admins
SELECT * FROM users WHERE role = 'ADMIN';

-- Get all analysts
SELECT * FROM users WHERE role = 'ANALYST';

-- Count users by role
SELECT role, COUNT(*) as count FROM users GROUP BY role;

-- Get user details with contact info
SELECT id, name, email, role, phone_number, organization_name FROM users;

-- Get recently registered users
SELECT * FROM users ORDER BY created_at DESC LIMIT 10;

-- ========================================
-- 2. DONATION QUERIES
-- ========================================

-- Get all donations
SELECT * FROM donations;

-- Get available donations
SELECT * FROM donations WHERE status = 'AVAILABLE' AND expiry_date > NOW();

-- Get expired donations
SELECT * FROM donations WHERE expiry_date < NOW() OR status = 'EXPIRED';

-- Get donations by status
SELECT status, COUNT(*) as count FROM donations GROUP BY status;

-- Get donations by donor
SELECT d.*, u.name as donor_name 
FROM donations d 
JOIN users u ON d.donor_id = u.id 
ORDER BY d.created_at DESC;

-- Get donations with expiry date coming soon
SELECT *, DATEDIFF(expiry_date, NOW()) as days_until_expiry 
FROM donations 
WHERE status = 'AVAILABLE' 
AND expiry_date > NOW() 
AND DATEDIFF(expiry_date, NOW()) <= 7
ORDER BY expiry_date ASC;

-- Get top food types donated
SELECT food_type, COUNT(*) as count, SUM(quantity) as total_quantity 
FROM donations 
GROUP BY food_type 
ORDER BY count DESC 
LIMIT 10;

-- Get total food saved (delivered/approved)
SELECT SUM(quantity) as total_food_saved FROM donations 
WHERE status IN ('DELIVERED', 'APPROVED');

-- Get donations by location
SELECT location, COUNT(*) as count FROM donations GROUP BY location ORDER BY count DESC;

-- ========================================
-- 3. REQUEST QUERIES
-- ========================================

-- Get all requests
SELECT * FROM food_requests;

-- Get pending requests
SELECT * FROM food_requests WHERE status = 'PENDING';

-- Get approved requests
SELECT * FROM food_requests WHERE status = 'APPROVED';

-- Get rejected requests
SELECT * FROM food_requests WHERE status = 'REJECTED';

-- Get completed requests
SELECT * FROM food_requests WHERE status = 'COMPLETED';

-- Get requests with recipient and donation details
SELECT 
  fr.id as request_id,
  u.name as recipient_name,
  d.food_type,
  fr.requested_quantity,
  fr.notes as reason,
  fr.status,
  fr.created_at
FROM food_requests fr
JOIN users u ON fr.recipient_id = u.id
JOIN donations d ON fr.donation_id = d.id
ORDER BY fr.created_at DESC;

-- Get requests by recipient
SELECT 
  u.name as recipient,
  COUNT(*) as total_requests,
  SUM(CASE WHEN fr.status = 'APPROVED' THEN 1 ELSE 0 END) as approved,
  SUM(CASE WHEN fr.status = 'PENDING' THEN 1 ELSE 0 END) as pending,
  SUM(CASE WHEN fr.status = 'REJECTED' THEN 1 ELSE 0 END) as rejected
FROM food_requests fr
JOIN users u ON fr.recipient_id = u.id
GROUP BY u.id, u.name;

-- Get requests by status
SELECT status, COUNT(*) as count FROM food_requests GROUP BY status;

-- Get recently created requests
SELECT * FROM food_requests ORDER BY created_at DESC LIMIT 20;

-- Get requests waiting for approval (oldest first)
SELECT * FROM food_requests 
WHERE status = 'PENDING' 
ORDER BY created_at ASC;

-- ========================================
-- 4. LOGISTICS QUERIES
-- ========================================

-- Get all logistics
SELECT * FROM logistics;

-- Get pending deliveries
SELECT * FROM logistics WHERE delivery_status = 'PENDING';

-- Get in-transit deliveries
SELECT * FROM logistics WHERE delivery_status = 'IN_TRANSIT';

-- Get delivered orders
SELECT * FROM logistics WHERE delivery_status = 'DELIVERED';

-- Get logistics with delivery status details
SELECT 
  l.id,
  fr.id as request_id,
  u.name as recipient,
  d.food_type,
  l.driver_name,
  l.vehicle_number,
  l.delivery_status,
  l.pickup_time,
  l.delivery_time
FROM logistics l
JOIN food_requests fr ON l.request_id = fr.id
JOIN users u ON fr.recipient_id = u.id
JOIN donations d ON fr.donation_id = d.id
ORDER BY l.created_at DESC;

-- Count by delivery status
SELECT delivery_status, COUNT(*) as count FROM logistics GROUP BY delivery_status;

-- ========================================
-- 5. ANALYTICS QUERIES
-- ========================================

-- Dashboard summary
SELECT 
  (SELECT COUNT(*) FROM users) as total_users,
  (SELECT COUNT(*) FROM donations) as total_donations,
  (SELECT COUNT(*) FROM food_requests) as total_requests,
  (SELECT COUNT(*) FROM food_requests WHERE status = 'PENDING') as pending_requests,
  (SELECT SUM(quantity) FROM donations WHERE status IN ('DELIVERED', 'APPROVED')) as total_food_saved,
  (SELECT COUNT(*) FROM logistics WHERE delivery_status = 'DELIVERED') as completed_deliveries;

-- Get donation volume trend (last 30 days)
SELECT 
  DATE(created_at) as date,
  COUNT(*) as donations_count,
  SUM(quantity) as total_quantity
FROM donations
WHERE created_at > DATE_SUB(NOW(), INTERVAL 30 DAY)
GROUP BY DATE(created_at)
ORDER BY date DESC;

-- Get request approval rate
SELECT 
  (SELECT COUNT(*) FROM food_requests WHERE status = 'APPROVED') / 
  COUNT(*) * 100 as approval_rate,
  COUNT(*) as total_requests
FROM food_requests;

-- Donors with most donations
SELECT 
  u.id,
  u.name,
  COUNT(d.id) as donation_count,
  SUM(d.quantity) as total_quantity
FROM users u
LEFT JOIN donations d ON u.id = d.donor_id
WHERE u.role = 'DONOR'
GROUP BY u.id, u.name
ORDER BY donation_count DESC
LIMIT 10;

-- Recipients with most requests
SELECT 
  u.id,
  u.name,
  u.organization_name,
  COUNT(fr.id) as request_count,
  SUM(fr.requested_quantity) as total_quantity
FROM users u
LEFT JOIN food_requests fr ON u.id = fr.recipient_id
WHERE u.role = 'RECIPIENT'
GROUP BY u.id, u.name, u.organization_name
ORDER BY request_count DESC
LIMIT 10;

-- Top locations for donations
SELECT 
  location,
  COUNT(*) as donation_count,
  SUM(quantity) as total_quantity
FROM donations
GROUP BY location
ORDER BY donation_count DESC
LIMIT 10;

-- ========================================
-- 6. SEARCH & FILTER QUERIES
-- ========================================

-- Search users by name or email
SELECT * FROM users 
WHERE name LIKE '%john%' OR email LIKE '%john%';

-- Search donations by food type
SELECT * FROM donations 
WHERE food_type LIKE '%rice%' 
AND status = 'AVAILABLE'
AND expiry_date > NOW();

-- Get all requests for a specific recipient (change ID as needed)
SELECT * FROM food_requests 
WHERE recipient_id = 1;

-- Get all requests for a specific donation (change ID as needed)
SELECT * FROM food_requests 
WHERE donation_id = 1;

-- ========================================
-- 7. UPDATE QUERIES
-- ========================================

-- Update donation status (change values as needed)
-- UPDATE donations SET status = 'EXPIRED' WHERE expiry_date < NOW();

-- Update request status (change values as needed)
-- UPDATE food_requests SET status = 'APPROVED' WHERE id = 1;

-- Update logistics status (change values as needed)
-- UPDATE logistics SET delivery_status = 'DELIVERED' WHERE id = 1;

-- ========================================
-- 8. DELETE QUERIES (USE WITH CAUTION)
-- ========================================

-- Delete a specific user (will cascade delete related data)
-- DELETE FROM users WHERE id = 1;

-- Delete all expired donations
-- DELETE FROM donations WHERE status = 'EXPIRED' AND expiry_date < DATE_SUB(NOW(), INTERVAL 30 DAY);

-- ========================================
-- 9. ADVANCED QUERIES
-- ========================================

-- Get complete order status tracking
SELECT 
  'Donation' as entity_type,
  d.id as entity_id,
  d.food_type,
  d.quantity,
  d.status,
  u.name as donor_name,
  NULL as recipient_name,
  d.created_at
FROM donations d
JOIN users u ON d.donor_id = u.id

UNION ALL

SELECT 
  'Request' as entity_type,
  fr.id as entity_id,
  d.food_type,
  fr.requested_quantity,
  fr.status,
  u2.name as donor_name,
  u1.name as recipient_name,
  fr.created_at
FROM food_requests fr
JOIN users u1 ON fr.recipient_id = u1.id
JOIN donations d ON fr.donation_id = d.id
JOIN users u2 ON d.donor_id = u2.id

ORDER BY created_at DESC;

-- Get food matching efficiency (requests vs available)
SELECT 
  COUNT(DISTINCT d.id) as available_donations,
  COUNT(DISTINCT fr.id) as pending_requests,
  ROUND(COUNT(DISTINCT d.id) / COUNT(DISTINCT fr.id) * 100, 2) as fulfillment_ratio
FROM donations d
LEFT JOIN food_requests fr ON fr.status = 'PENDING'
WHERE d.status = 'AVAILABLE' AND d.expiry_date > NOW();
