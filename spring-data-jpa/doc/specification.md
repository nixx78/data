# Specification
В Spring Data JPA, Specification представляет собой интерфейс, который позволяет строить динамические запросы к базе данных. 
Он используется для создания условий (предикатов) для запросов, которые могут варьироваться в зависимости от различных критериев.

## Основные моменты использования Specification:

* Интерфейс Specification: Specification<T> является интерфейсом, параметризованным типом T, который представляет сущность (Entity), для которой строится запрос.

* Построение условий  
Specification позволяет строить условия (предикаты), которые могут быть использованы для фильтрации данных. Эти условия строятся в коде на Java и могут включать различные критерии, такие как равенство значений, сравнения, проверки на наличие и другие.

* Применение в репозиториях  
Specification часто используется вместе с репозиториями Spring Data JPA. Например, можно объявить методы в репозитории,
которые принимают Specification в качестве аргумента и используют его для построения запросов.

* Комбинация условий  
Specifications могут быть объединены логически (AND, OR, NOT), что позволяет строить более сложные запросы с динамическими условиями.