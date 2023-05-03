CREATE TABLE IF NOT EXISTS table_a(
    id INT PRIMARY KEY,
    value_a VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS table_b(
    id INT PRIMARY KEY,
    value_b VARCHAR(20) NOT NULL
);

INSERT INTO table_a (id, value_a) VALUES
 (1, 'va_1'),
 (2, 'va_2'),
 (3, 'va_3'),
 (4, 'va_4');
 

INSERT INTO table_b (id, value_a) VALUES 
 (1, 'vb_1'),
 (2, 'vb_2'),
 (3, 'vb_3'),
 (5, 'vb_5');
 
 -- JOIN - Получаем только записи, которые есть в обоих таблицах
 SELECT * FROM table_a a JOIN table_b b ON a.id = b.id;
 
 -- LEFT JOIN - Все записи из таблицы A - отсуствующие записи из таблицы B заменены NULL
 SELECT * FROM table_a a LEFT JOIN table_b b ON a.id = b.id;
 
  -- RIGHT JOIN - Все записи из таблицы B - отсуствующие записи из таблицы A заменены NULL
 SELECT * FROM table_a a RIGHT JOIN table_b b ON a.id = b.id;
 
 -- В MySQL OUTER JOIN отсуствует, если необходимо его можно эмулировать  при помощи UNION (LEFT + RIGHT)
 SELECT * FROM table_a a LEFT JOIN table_b b ON a.id = b.id
 UNION
 SELECT * FROM table_a a RIGHT JOIN table_b b ON a.id = b.id;
 
 -- CROSS JOIN - Декартово произведение двух таблиц
 SELECT * FROM table_a CROSS JOIN table_b;
  
 
 
 
 