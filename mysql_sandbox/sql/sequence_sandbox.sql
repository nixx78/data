DROP TABLE IF EXISTS table_with_grouped_data;

CREATE TABLE IF NOT EXISTS table_with_grouped_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    instrumentID INTEGER NOT NULL,
    state ENUM('SUCCESS', 'FAIL'),
    nvalue NUMERIC(10 , 3 ),
    date_time TIMESTAMP
);

DELETE FROM table_with_grouped_data 
WHERE
    id != 0;

INSERT INTO table_with_grouped_data (instrumentID, state, nvalue, date_time) VALUES
 (1, 'SUCCESS', 1.10, '2023-09-06 12:00:00'),
 (1, 'SUCCESS', 1.100, '2023-09-05 11:59:00'),
 (1, 'FAIL',	1.10, '2023-09-03 11:58:00'),
 (1, 'FAIL',	2.00, '2023-09-02 11:57:00'),
 (1, 'SUCCESS', 3.00, '2023-09-01 11:56:00'),
 (1, 'SUCCESS', 1.10, '2023-09-01 11:55:00'),
 
 (2, 'FAIL', 	1.00, '2023-09-06 12:00:00'),
 (2, 'FAIL', 	1.00, '2023-09-06 11:59:00'),
 (2, 'FAIL',	2.00, '2023-09-06 11:58:00'),
 (2, 'SUCCESS',	1.00, '2023-09-06 11:57:00'),
 
 (3, 'FAIL', 	1.00, '2023-09-06 12:00:00'),
 (3, 'FAIL', 	2.00, '2023-09-06 11:59:00'),
 (3, 'FAIL',	1.00, '2023-09-06 11:57:00'),
 (3, 'FAIL',	1.00,'2023-09-06 11:56:00');

SELECT * FROM  table_with_grouped_data;

-- Запрос расскрашиывает последовательности по группам для одного инстремента
/*
SELECT	*,  ROW_NUMBER() OVER (ORDER BY date_time desc) - ROW_NUMBER() OVER (PARTITION BY state ORDER BY date_time desc) AS seqId
FROM table_with_grouped_data t
WHERE t.instrumentID = 1
ORDER BY  t.date_time desc;
*/

-- Запрос, который должен работать для всех инструментов
/*
SELECT	*,
ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time desc) AS seq,
ROW_NUMBER() OVER (PARTITION BY instrumentID ORDER BY date_time desc) - ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time desc) AS seqId
FROM table_with_grouped_data t
ORDER BY  instrumentID, date_time desc;
*/

-- Маркируем группы для всех инструментов
/*
SELECT	*,
ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time desc) AS seq,
ROW_NUMBER() OVER (PARTITION BY instrumentID ORDER BY date_time desc) - ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time desc) AS seqId
FROM table_with_grouped_data t
ORDER BY  instrumentID, date_time desc;
*/

-- Получаем длину последовательности по state 
WITH RankedData AS (
	SELECT	*,
	ROW_NUMBER() OVER (PARTITION BY instrumentID ORDER BY date_time desc) - ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time desc) AS seqId
	FROM table_with_grouped_data t
	ORDER BY  instrumentID, date_time desc
)
SELECT r.instrumentID, count(*) AS LastSequenceSize_ByValue
FROM RankedData r
WHERE r.seqId=0
GROUP BY instrumentID;

-- Получаем длину последовательности по nvalue
WITH RankedData AS (
	SELECT	*,
	ROW_NUMBER() OVER (PARTITION BY instrumentID ORDER BY date_time desc) - ROW_NUMBER() OVER (PARTITION BY instrumentID, nvalue ORDER BY date_time desc) AS seqId
	FROM table_with_grouped_data t
) 
SELECT r.instrumentID, count(*) AS LastSequenceSize_ByValue
FROM RankedData r
WHERE r.seqId=0
GROUP BY instrumentID;
