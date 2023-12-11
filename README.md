# P6-Full-Stack-MDD

This is MVP for P6-MDD

## Prerequisites

To use this app, you need:

* Java 17
* NodeJS 16
* MySQL
* Angular CLI

Clone this repository on your computer.

## Install Database

Open your MySQL or your PHPMyAdmin as an admin.
Create a new table (default: P6_OCR)
Copy the content of /ressources/sql/script.sql

Open /back/src/main/resources/application.yml and change: spring: datasource:
* url (default: jdbc:mysql://127.0.0.1:3306/P6_OCR)
* username (your SQL username)
* password (your SQL password)

(These can be set in /back/credential.yml)

Don't forget to GRANT ALL PRIVILEGES to this user on this table

## Install BackEnd

Run `mvn install` (or `mvn package`) to install all dependencies.
Run `mvn spring-boot:run` to run BackEnd (default: port 8080)

## Install FrontEnd

Don't forget to install your node_modules before starting (`npm install`).
Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.
Run `ng build --base-href ./` to build the project. The build artifacts will be stored in the `dist/` directory, and can be served into your server folder (Apache, Nginx, etc.)
