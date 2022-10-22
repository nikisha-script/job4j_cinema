# Сервис по продаже билетов в кинотеатр.

Данное приложение иллюстрирует покупку билетов в кинотеатр.

Стек технологий: 

![java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/Spring-Boot-green)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.0.15-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-style-blue)
![JDBC](https://img.shields.io/badge/JDBC-DB-yellowgreen)
![Liquibase](https://img.shields.io/badge/Liquibase-core-red)
![PostgresSQL](https://img.shields.io/badge/PostgresSQL-42.3.6-brightgreen)
![H2](https://img.shields.io/badge/H2-Database-yellowgreen)
![Mockito](https://img.shields.io/badge/Mockito-test-brightgreen)
![Junit](https://img.shields.io/badge/Junit-test-red)
![Lombok](https://img.shields.io/badge/Lombok-1.18.24-lightgrey)

Перед запуском установите:
- Java 17
- Apache Maven 3.x
- PostgreSQL 14

## Запуск приложения

1. Создать бд:
```sql
    create database cinema;
```
2. Запуск приложения с maven. Перейдите в корень проекта через командную строку и выполните команды:
```
    mvn clean install
    mvn spring-boot:run
```
### Основная страница со всеми объявлениями и функционалом:
![](img/nav.png)
![](img/films.png)

### Страницы с авторизацией и аутентификацией пользователя:
![](img/reg.png)![](img/auth.png)


### Страницы с выбором места и оформлением билета:
![](img/view.png)
![](img/buy.png)

### Контакты:

email: danya.nikisha@mail.ru



