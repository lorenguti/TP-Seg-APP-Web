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
  password varchar(45) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

EOF

echo "Base de datos y tabla creadas exitosamente."
