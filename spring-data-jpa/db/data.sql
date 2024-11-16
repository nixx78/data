delete from CUSTOMER_TBL where id!=0; 
delete from TASK_DEPENDENCY where dependency_id!=0;
delete from TASK where task_id!=0;

insert into CUSTOMER_TBL(id, sName, sSurname, dtDateOfBirth, sType) VALUES
(1, 'Name1', 'Surname1', '1990-01-02', 'Simple'),
(2, 'Name2', 'Surname1', '1980-10-05', 'Vip'),
(3, 'John', 'Deere', '1978-01-01', 'Student'),
(4, 'John', 'Rambo', '1991-12-15', 'Student');


# -----------------------
INSERT INTO TASK (task_id,name,description) VALUES
(1,'Parent task1','Placeholder for parent task'),
(2,'Subtask1',''),
(3,'Subtask2',''),
(4,'Subtask21',''),
(5,'Subtask32','');

INSERT INTO TASK_DEPENDENCY (dependency_id, task_id, depends_on_task_id, dependencyType, createdAt) VALUES
(1, 2, 1, 'PARENT', '2024-11-16 12:00:01'),
(2, 3, 1, 'PARENT', '2024-11-16 12:00:02'),
(3, 4, 3, 'PARENT', '2024-11-16 12:00:03'),
(4, 5, 4, 'PARENT', '2024-11-16 12:00:04');