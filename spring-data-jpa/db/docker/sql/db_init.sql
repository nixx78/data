CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'app_pass';
GRANT ALL PRIVILEGES ON db_sandbox.* TO 'app_user'@'%';

SELECT '"app_user" created & permissions set';

CREATE USER 'admin_user'@'%' IDENTIFIED BY 'admin_pass';
GRANT ALL PRIVILEGES ON *.* TO 'admin_user'@'%' WITH GRANT OPTION;

SELECT '"admin_user" created & permissions set';
FLUSH PRIVILEGES;

