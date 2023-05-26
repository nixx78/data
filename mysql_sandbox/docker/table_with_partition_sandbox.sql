-- https://dev.mysql.com/doc/mysql-partitioning-excerpt/8.0/en/partitioning-range.html
-- https://vertabelo.com/blog/everything-you-need-to-know-about-mysql-partitions/

DROP TABLE IF EXISTS transaction_with_part;

CREATE TABLE IF NOT EXISTS transaction_with_part(
    id INT NOT NULL,
	amount numeric(10,3),
    date_time timestamp,
    currency int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)
PARTITION BY LIST (currency) (
    PARTITION USD VALUES IN (1),
    PARTITION EUR VALUES IN (2)
);

insert into transaction_with_part(id, amount, date_time, currency) values 
  (1, 0.39, '2023-04-15 11:04:11', 1),
  (2, 10.39, '2023-04-10 12:04:11', 2),
  (3, 100.39, '2023-04-11 12:04:11', 2),
  (4, 200.39, '2023-04-12 13:04:11', 1)
;

select * from transaction_with_part PARTITION (USD);
