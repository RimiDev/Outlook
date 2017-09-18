/**
 * Author:  Maxime Lacasse
 * Created: Sep 11, 2017
 * Run this only once. Create the database, then populate the database with
 * the tables given in the other sql file --> CreateAgendaTables.sql
 */
DROP DATABASE IF EXISTS AGENDAdb;
CREATE DATABASE AGENDAdb;

USE AGENDAdb;

DROP USER IF EXISTS Rimi@localhost;
CREATE USER Rimi@'localhost' IDENTIFIED WITH mysql_native_password BY 'RimBoy' REQUIRE NONE;
GRANT ALL ON AGENDAdb.* TO Rimi@'localhost';

FLUSH PRIVILEGES;

