CREATE TABLE IF NOT EXISTS instrument(
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS instrument_price(
    id INT AUTO_INCREMENT PRIMARY KEY,
    instrument_id int,
    date_time timestamp,
  	price numeric(10,3),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (instrument_id) REFERENCES instrument(id)
);

delete from instrument_price where id!=0;
delete from instrument where id!=0;

insert into instrument (id, name) values
 (1, 'instr1'),
 (2, 'instr2'),
 (3, 'instr3');
 
insert into instrument_price(instrument_id, price, date_time) values
	(1, 100.01, '2023-05-15 11:04:11'),
	(1, 100.12, '2023-05-15 11:00:00'),
	(1, 100.13, '2023-05-17 10:00:00'),
	(2, 10.00, '2023-05-15 10:00:00'),
	(2, 15.00, '2023-05-15 09:00:00')

-- Цена с максимальной датой для каждого инструмента c фильтром по дате
select i.id, i.name, p.price, p.date_time from instrument i
LEFT JOIN (SELECT id, instrument_id, price, date_time, ROW_NUMBER()
 OVER (PARTITION BY instrument_id ORDER BY date_time DESC) AS RowNum 
 FROM instrument_price
 WHERE DATE(date_time) = '2023-05-15') p
ON i.id = p.instrument_id
WHERE RowNum = 1 OR RowNum IS NULL;

-- Еще один способ как можно получить данные, JOIN таблицы с самой собой
select i.id, i.name, p.price, p.date_time from instrument i
LEFT JOIN (SELECT e.*
FROM instrument_price e
JOIN (
    SELECT instrument_id, MAX(date_time) AS maxDate
    FROM instrument_price
    WHERE DATE(date_time) = '2023-05-15'
    GROUP BY instrument_id
) t ON e.instrument_id = t.instrument_id AND e.date_time = t.maxDate
) p ON i.id = p.instrument_id;
