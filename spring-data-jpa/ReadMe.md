# Проект с примером использования Spring-Data-JPA & Spring-Data-Rest

Swagger Endpoint: http://localhost:8080/swagger-ui/index.html

## Основные случае использования 
 - Сохранение / изменение связанных случаев
 - Примеры оптимизации запросов (JOIN FETCH / @EntityGraph)
 - Использование @Modifying запросов

## Полезные ссылки
https://spring.io/projects/spring-data-rest
https://spring.io/projects/spring-data-jpa

## Docker (DB песочница)
Запуск Docker c базой данных:
- docker-compose up -d
- Остановка Docker: docker-compose down
- Остановка Docker и удаление данных из Volume: docker-compose down -v 