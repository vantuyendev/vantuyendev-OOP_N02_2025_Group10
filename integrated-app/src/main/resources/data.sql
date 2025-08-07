-- Sample data for Shop Management System
-- This will be loaded automatically when the application starts

-- Sample Employees
INSERT INTO Employee (user_id, employee_name, phone_number, role, salary, department) VALUES
('EMP001', 'John Smith', '+1-555-0101', 'Manager', 75000.00, 'Management'),
('EMP002', 'Sarah Johnson', '+1-555-0102', 'Sales Associate', 45000.00, 'Sales'),
('EMP003', 'Mike Wilson', '+1-555-0103', 'Cashier', 35000.00, 'Sales'),
('EMP004', 'Emily Davis', '+1-555-0104', 'Inventory Specialist', 48000.00, 'Inventory'),
('EMP005', 'Robert Brown', '+1-555-0105', 'IT Support', 55000.00, 'IT');

-- Sample Products (auto-generated ID)
INSERT INTO Product (product_name, price, quantity, category, supplier) VALUES
('Laptop Computer', 899.99, 25, 'Electronics', 'Tech Supplier Co.'),
('Smartphone', 699.99, 50, 'Electronics', 'Mobile Corp'),
('Office Chair', 199.99, 15, 'Furniture', 'Office Solutions Ltd.'),
('Desk Lamp', 49.99, 30, 'Furniture', 'Lighting Plus'),
('Wireless Mouse', 29.99, 100, 'Electronics', 'Tech Supplier Co.'),
('Notebook', 5.99, 200, 'Stationery', 'Paper World'),
('Coffee Mug', 12.99, 75, 'Kitchenware', 'Home Essentials'),
('Water Bottle', 19.99, 60, 'Kitchenware', 'Hydro Products');

-- Sample Customers
INSERT INTO Customer (user_id, customer_name, phone_number, email, address) VALUES
('CUST001', 'Alice Cooper', '+1-555-0201', 'alice.cooper@email.com', '123 Main St, Anytown, USA'),
('CUST002', 'Bob Taylor', '+1-555-0202', 'bob.taylor@email.com', '456 Oak Ave, Somewhere, USA'),
('CUST003', 'Carol White', '+1-555-0203', 'carol.white@email.com', '789 Pine Rd, Elsewhere, USA'),
('CUST004', 'David Lee', '+1-555-0204', 'david.lee@email.com', '321 Elm St, Another Town, USA'),
('CUST005', 'Eva Martinez', '+1-555-0205', 'eva.martinez@email.com', '654 Cedar Blvd, New City, USA');

-- Sample Login Credentials  
-- Status: 0 = Employee, 1 = Customer
INSERT INTO Login (user_id, password, status) VALUES
-- Admin account
('admin', '123456', 0),
-- Employee accounts
('e001', '123456', 0),
('EMP001', 'admin123', 0),
('EMP002', 'manager123', 0),
('EMP003', 'sales123', 0),
('EMP004', 'cashier123', 0),
('EMP005', 'inv123', 0),
-- Customer accounts
('c001', '123456', 1),
('CUST001', 'password123', 1),
('CUST002', 'password123', 1),
('CUST003', 'password123', 1),
('CUST004', 'password123', 1),
('CUST005', 'password123', 1);
