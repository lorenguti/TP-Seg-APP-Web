#!/bin/bash

# Es neceesario tener instalado mysql server


# Variables para la conexión
DB_USER="root"      
DB_HOST="localhost"
DB_NAME="rrhh_db"

# Conectarse a MySQL
mysql -u $DB_USER -p -h $DB_HOST <<EOF

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS $DB_NAME;

-- Usar la base de datos recién creada
USE $DB_NAME;

-- Crear la tabla 'users'
CREATE TABLE IF NOT EXISTS users (
  id int NOT NULL,
  dni int DEFAULT NULL,
  name varchar(45) DEFAULT NULL,
  user varchar(45) DEFAULT NULL,
  email varchar(45) DEFAULT NULL,
  password varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Poblar tabla
INSERT INTO users (id, dni, name, user, email, password) VALUES
(1, 12345678, 'John Doe', 'jdoe', 'jdoe@example.com', 'pass123'),
(2, 87654321, 'Jane Smith', 'jsmith', 'jsmith@example.com', 'pass456'),
(3, 13579246, 'Alice Johnson', 'ajohnson', 'alicej@example.com', 'alice789'),
(4, 24681357, 'Bob Brown', 'bbrown', 'bbrown@example.com', 'bob321'),
(5, 98765432, 'Charlie White', 'cwhite', 'cwhite@example.com', 'charlie654');


EOF

echo "Base de datos y tabla creadas exitosamente."
