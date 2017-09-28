drop table bugs;
create table bugs (id number(10),   open_date timestamp,   close_date timestamp,   severity number(1));

insert into bugs values (10, TO_DATE('07.09.2017', 'dd.mm.yyyy'),TO_DATE('07.09.2017', 'dd.mm.yyyy'), 1);
insert into bugs values (20, TO_DATE('07.09.2017', 'dd.mm.yyyy'),TO_DATE('08.09.2017', 'dd.mm.yyyy'), 1);
insert into bugs values (30, TO_DATE('09.09.2017', 'dd.mm.yyyy'),NULL, 1);
insert into bugs values (40, TO_DATE('08.09.2017', 'dd.mm.yyyy'),TO_DATE('09.09.2017', 'dd.mm.yyyy'), 1);
insert into bugs values (777, TO_DATE('06.12.2017', 'dd.mm.yyyy'),TO_DATE('06.12.2017', 'dd.mm.yyyy'), 1);

CREATE OR REPLACE PACKAGE sandbox_sp AS 
   PROCEDURE get_bugs_by_period(start_date DATE, end_date DATE, bugs OUT SYS_REFCURSOR); 
END sandbox_sp;
/
CREATE OR REPLACE PACKAGE BODY sandbox_sp IS
     PROCEDURE get_bugs_by_period(start_date DATE, end_date DATE, bugs OUT SYS_REFCURSOR) IS
      BEGIN
       OPEN BUGS FOR SELECT MONTH_DAY,count(id) count, listagg(id,',') within group (order by id) ids  from
        (SELECT MONTH_DAY, T.* FROM (SELECT start_date + ROWNUM-1 MONTH_DAY FROM DUAL CONNECT BY LEVEL<=(end_date-start_date)+1) V
        LEFT JOIN BUGS T ON V.MONTH_DAY BETWEEN OPEN_DATE AND CLOSE_DATE or (CLOSE_DATE IS NULL AND V.MONTH_DAY>=OPEN_DATE)
        )
        GROUP BY MONTH_DAY
        ORDER BY MONTH_DAY;
      END get_bugs_by_period;
END sandbox_sp;
/

--var c refcursor;
--execute sandbox_sp.get_bugs_by_period(TO_DATE('01.09.2017','DD.MM.YYYY'), TO_DATE('10.09.2017','DD.MM.YYYY'), :c);
--print c;