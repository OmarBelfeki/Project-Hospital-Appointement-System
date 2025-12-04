-- ----------------------------------------
-- HospitalSystem Database
-- ----------------------------------------

-- 1. Create database
drop database hospital_system;
CREATE DATABASE IF NOT EXISTS hospital_system;

USE hospital_system;

-- 2. Table: doctors
CREATE TABLE IF NOT EXISTS doctors (
    doctor_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

-- 3. Table: patients
CREATE TABLE IF NOT EXISTS patients (
    patient_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

-- 4. Table: appointments
CREATE TABLE IF NOT EXISTS appointments (
    appointment_id VARCHAR(50) PRIMARY KEY,
    patient_id VARCHAR(50) NOT NULL,
    doctor_id VARCHAR(50) NOT NULL,
    date DATETIME NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
);

-- 5. Table: doctor_slots
CREATE TABLE IF NOT EXISTS doctor_slots (
    slot_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_id VARCHAR(50) NOT NULL,
    slot DATETIME NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
);

-- 6. Optional: add some indexes
CREATE INDEX idx_doctor_id_slots ON doctor_slots(doctor_id);
CREATE INDEX idx_doctor_id_appointments ON appointments(doctor_id);
CREATE INDEX idx_patient_id_appointments ON appointments(patient_id);
