DROP TABLE IF EXISTS table_with_grouped_data;

CREATE TABLE IF NOT EXISTS table_with_grouped_data(
    id INT AUTO_INCREMENT PRIMARY KEY,
    instrumentID integer NOT NULL,
    state ENUM('SUCCESS', 'FAIL'),
    date_time timestamp
);

DELETE FROM table_with_grouped_data WHERE id!=0;

INSERT INTO table_with_grouped_data (instrumentID, state, date_time) VALUES
 (1, 'SUCCESS', '2023-09-06 12:00:00'),
 (1, 'SUCCESS', '2023-09-06 11:59:00'),
 (1, 'FAIL',	'2023-09-06 11:58:00'),
 (1, 'FAIL',	'2023-09-06 11:57:00'),
 (1, 'SUCCESS', '2023-09-06 11:56:00'),
 (2, 'FAIL', 	'2023-09-06 12:00:00'),
 (2, 'FAIL', 	'2023-09-06 11:59:00'),
 (2, 'FAIL',	'2023-09-06 11:58:00'),
 (2, 'SUCCESS',	'2023-09-06 11:57:00'),
 (3, 'FAIL', 	'2023-09-06 12:00:00'),
 (3, 'FAIL', 	'2023-09-06 11:59:00'),
 (3, 'FAIL',	'2023-09-06 11:57:00'),
 (3, 'FAIL',	'2023-09-06 11:56:00');
 
 -- SELECT * FROM  table_with_grouped_data;

 
-- Запрос расскрашиывает последовательности по группам для одного инстремента
/*
SELECT	*,  ROW_NUMBER() OVER (ORDER BY date_time) - ROW_NUMBER() OVER (PARTITION BY state ORDER BY date_time) AS seqId
FROM table_with_grouped_data t
WHERE t.instrumentID = 1
ORDER BY  t.date_time desc;
*/

-- Запрос, который должен работать для всех инструментов
/*
SELECT	*,
ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time desc) AS seq,
ROW_NUMBER() OVER (PARTITION BY instrumentID ORDER BY date_time) - ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time) AS seqId
FROM table_with_grouped_data t
ORDER BY  instrumentID, date_time desc;
*/

-- Надо получить длину последней последовательности для каждого инструментов
/*
SELECT	*,
ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time desc) AS seq,
ROW_NUMBER() OVER (PARTITION BY instrumentID ORDER BY date_time) - ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time) AS seqId
FROM table_with_grouped_data t
ORDER BY  instrumentID, date_time desc;
*/

WITH RankedData AS (
	SELECT	*,
	ROW_NUMBER() OVER (PARTITION BY instrumentID ORDER BY date_time) - ROW_NUMBER() OVER (PARTITION BY instrumentID, state ORDER BY date_time) AS seqId
	FROM table_with_grouped_data t
	ORDER BY  instrumentID, date_time desc
)
SELECT instrumentID, count(*) as LastSequenceSize
from RankedData
where concat(instrumentID, "_", seqId) IN (
	SELECT concat(instrumentID, "_", MAX(seqId)) as ID from RankedData
	GROUP BY instrumentID
    )
GROUP BY instrumentID;
 
 
 
 