-- Create the database
CREATE DATABASE IF NOT EXISTS school_db;
USE school_db;

-- Create the students table
CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    course VARCHAR(50) NOT NULL
);

-- Optional: Add a sample record to test
INSERT INTO students (name, email, course) 
VALUES ('Test Student', 'test@example.com', 'Computer Science');
