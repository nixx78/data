# Проект с примером использования Spring-Data-JPA & Spring-Data-Rest

Swagger Endpoint: http://localhost:8080/swagger-ui/index.html

## Случаи использования 

### Основные
 - Сохранение / изменение связанных сущностей
 - Примеры оптимизации запросов (JOIN FETCH / @EntityGraph)
 - Использование @Modifying запросов
 - Предоставление доступа к данным через REST (Spring-Data-Rest)
 - Использование Specification в репозитории
 - Использование Projection в репозитории
 
### Custom Repository
* Создание запроса используя Criteria Builder
Создание запроса к базе на основе полей, которые приходят в запросе с UI
* Сохранение сущности при помощи специального метода  
Показано, как можно вынести общую логику (добавление пользователя) в отдельный класс и использовать ее в нескольких репозиториях

## Полезные ссылки
https://spring.io/projects/spring-data-rest
https://spring.io/projects/spring-data-jpa

## Docker (MySQL DB песочница)
Запуск Docker c базой данных:
- docker-compose up -d
- Остановка Docker: docker-compose down
- Остановка Docker и удаление данных из Volume: docker-compose down -v 