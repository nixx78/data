# Проект с примерами использования Spring для доступа до баз данных 

* Spring-Data-JPA
* Spring-Data-Rest
* Spring-Data-JDBC

Swagger Endpoint: http://localhost:8080/swagger-ui/index.html

## База данных

### Live
В live среде используется MySQL (образ из /db/docker/)
Для создания таблиц необходимо запустить приложение с параметром:
spring.jpa.hibernate.ddl-auto=**create**  
После этого, параметр можно заменить на **validate**  
Скрипты с данными находятся в файле: data.sql, запускать можно через MySqlWorkbench

### Unit test
В тестовой среде используется Embedded H2. Cхема создается в файле schema.sql, таблицы создаются автоматически - Hibernate

## Случаи использования 

### Основные
 - Сохранение / изменение связанных сущностей
 - Примеры оптимизации запросов (JOIN FETCH / @EntityGraph)
 - Использование @Modifying запросов
 - Предоставление доступа к данным через REST (Spring-Data-Rest)
 - Использование Specification в репозитории
 - Использование Projection в репозитории
 - Использование Optimistic/Pessimistic Lock
 
### Custom Repository
* Создание запроса используя Criteria Builder - запроса к базе на основание полей, которые приходят в запросе с UI
* UserAware, сохранение сущности при помощи специального метода - показано, как можно вынести общую логику (добавление пользователя) в отдельный класс и использовать ее в нескольких репозиториях

### Transaction - основные случаи использования
* Управление транзакцией при использовании EntityManager
* Управление транзакцией при использовании Repository (@Transactional)
* Управление транзакцией при использовании Transaction Template

## Полезные ссылки
https://spring.io/projects/spring-data-rest
https://spring.io/projects/spring-data-jpa

## Docker (MySQL DB песочница)
При развёртывании контейнера создаются пользователи из файла:db_init.sql. В дальнейшем, эти пользователи используются в приложение. 

Запуск Docker c базой данных:
- docker-compose up -d
- Остановка Docker: docker-compose down
- Остановка Docker и удаление данных из Volume: docker-compose down -v

