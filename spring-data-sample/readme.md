# Проект песочница для Spring-data-JDBC

https://spring.io/projects/spring-data-jdbc
https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/

## Основные принципы и возможности
* ORM с ограниченными возможностями (нет кеша, ленивой загрузки, write-behind)

# Основные случаи использования
* H2 in - memory - db
* Создания таблиц при помощи schema.sql
* CRUD - репозиторий
* Использование JdbcTemplate
* Аннотации для Entity
* Обработка @DomainEvents
* Запрос из repository: аннотация @Query
* Запрос из repository: по имени метода

# TODO
* Account (1) -> Transaction (n) -> https://javabydeveloper.com/spring-data-jdbc-one-to-many-example/
* https://dev.to/peholmst/handling-domain-events-with-spring-2bmm - прочитать статью