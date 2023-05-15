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
 
 select * from instrument;
 
insert into instrument_price(instrument_id, price, date_time) values
	(1, 100.01, '2023-05-15 11:04:11'),
	(1, 100.12, '2023-05-15 11:00:00'),
	(1, 100.13, '2023-05-12 10:00:00'),
	(2, 10.00, '2023-05-15 10:00:00'),
	(2, 15.00, '2023-05-15 09:00:00');


select i.id, i.name, p.price, p.date_time from instrument i
LEFT JOIN (SELECT id, instrument_id, price, date_time, ROW_NUMBER()
 OVER (PARTITION BY instrument_id ORDER BY date_time DESC) AS RowNum 
 FROM instrument_price) p
ON i.id = p.instrument_id
WHERE RowNum = 1;
 