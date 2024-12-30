-- Get top record with max amont
select * from transaction order by amount desc limit 1;

-- Get second largest transaction by amound
select max(amount) from transaction where amount < (select max(amount) from transaction);

-- Get second largerst amount for each date
SELECT DATE(t1.date_time), MAX(t1.amount) AS second_max_amount
FROM transaction t1
WHERE t1.amount < (
    SELECT MAX(t2.amount)
    FROM transaction t2
    WHERE DATE(t2.date_time) = DATE(t1.date_time)
) GROUP BY DATE(t1.date_time);

-- Get transaction statistics for each date for transaction amount more than 10
SELECT count(id) as TxnCount, DATE(date_time) as TxnDate, SUM(amount) as Total
FROM transaction
GROUP BY TxnDate
HAVING Total > 10
ORDER by TxnDate DESC;

select * from transaction;

-- Sample, how we can concatenate fields into one text field
SELECT GROUP_CONCAT(id) AS ids, DATE(date_time) as TxnDate FROM transaction  GROUP BY TxnDate;
                
-- https://dev.mysql.com/blog-archive/mysql-8-0-2-introducing-window-functions/
-- Получить две саме большие транзакции за каждый день
SELECT * FROM
(SELECT id, amount, date_time, ROW_NUMBER()
 OVER (PARTITION BY DATE(date_time) order by amount desc) AS RowNum 
 FROM transaction
) as Limited 
WHERE RowNum < 3
ORDER BY date_time DESC;

-- https://dev.mysql.com/doc/refman/8.0/en/window-functions-usage.html
-- Получаем статистику транзакций по каждому счету по каждой валюте за каждый день
SELECT distinct a.account_name, t.account_id, currency, DATE(date_time) as date, 
SUM(amount) OVER (PARTITION BY account_id, currency) as TotalAmountAccountCurrency, 
SUM(amount) OVER (PARTITION BY DATE(date_time), currency, account_id) AS SumByDateCurrency
FROM transaction t JOIN account a ON t.account_id = a.id;
