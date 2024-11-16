# Структура таблиц для приложения TaskManager

## Таблицы

### Person
id
name
surname

### Task
 id
 header
 description
 start_date
 end_date
 category
 color

# TaskDependency - зависимости между задачами

# Team
 team_id
 person_id
 
## Зависимости

Team (n) - (n)Person (1) -> Task(1) -> (n) Category
                      -> (n) Color 
