
# Kino

> Simple application to manage bookings in the cinema.
In the current version the application allows you to "purchase" tickets to anonymous user or registered user.

## Quickstart
Run the app:

    In IDE set in server options -> Edit configuration -> VM options:  -Dspring.profiles.active = prod
    Configure database settings in resources/application.yml
    After run the application and generate the database schema, change hbm2ddl.method to 'update'
    Initialize the database with resources/sql
    
    Alternative way to run via command line if we already have configuration:
    On project root:
    mvn install
    java -jar target/CinemaProject-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
    
The app will be available at `http://localhost:8080/CinemaProject/`. 

## Plans for further application development
- Create an administrator module in Angular 2 + TypeScript and solve the problem of manually editing the database.
- Adding possibility to reserve a seat for a specified time
- Adding log in via social networking sites.
- Adding activate a new account by email
- More user account functionality
- Adding http/https channel security
- Reset password
- and more...


## Tech stack and libraries
### Backend
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Hibernate
- MySQL
- H2
- Hikari CP
- iText PDF
- Maven
- Java 1.8
- IntelliJ

### Frontend
- Thymeleaf
- HTML5
- CSS3
- Bootstrap
- jQuery
- jQuery DataTables
---
