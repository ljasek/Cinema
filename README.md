
# Kino

> Simple application to manage bookings in the cinema.
In the current version the application allows you to "purchase" tickets to anonymous user or registered user.

## Quickstart
Run the app:

    In IDE set in server options -> Edit configuration -> VM options:  -Dspring.profiles.active=prod
    Configure database settings in resources/application.yml
    After run the application and generate the database schema, change hbm2ddl.method to 'update'
    Initialize the database with resources/sql
    
    Alternative way to run via command line if we already have configuration:
    On project root:
    mvn install
    java -jar target/CinemaProject-1.0.1.jar --spring.profiles.active=prod
    
The app will be available at `http://localhost:8080/CinemaProject/`</br>
For https channel the app will be available at `https://localhost:8443/CinemaProject/`

##### Note
For proper operation of email sending mechanism it is required to:</br>
1st: https://www.google.com/settings/security/lesssecureapps : Should be turned on.</br>
2nd: https://accounts.google.com/b/0/displayunlockcaptcha

## Plans for further application development
- Create an administrator module in Angular 2 + TypeScript and solve the problem of manually editing the database.
- Adding possibility to reserve a seat for a specified time
- Adding log in via social networking sites.
- Adding activate a new account by email (added)
- More user account functionality
- Adding http/https channel security (added)
- Reset password (added)
- and more...


## Tech stack and libraries
### Backend
- [Spring Boot](https://projects.spring.io/spring-boot/)
- [Spring MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html)
- [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
- [Spring Security](https://projects.spring.io/spring-security/)
- [Hibernate](http://hibernate.org/)
- [MySQL](https://www.mysql.com/)
- [H2](http://www.h2database.com/html/main.html)
- [Hikari CP](https://brettwooldridge.github.io/HikariCP/)
- [iText PDF](http://itextpdf.com/)
- [Maven](https://maven.apache.org/)
- [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [IntelliJ](https://www.jetbrains.com/idea/)

### Frontend
- [Thymeleaf](http://www.thymeleaf.org/)
- [HTML5](https://www.w3schools.com/html/html5_intro.asp)
- [CSS3](https://www.w3schools.com/css/default.asp)
- [Bootstrap](http://getbootstrap.com/)
- [jQuery](https://jquery.com/)
- [jQuery DataTables](https://datatables.net/)
- [Toastr](http://codeseven.github.io/toastr/)
---
