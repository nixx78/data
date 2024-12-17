SELECT '"app_user" user creation';
CREATE USER IF NOT EXISTS 'app_user'@'%' IDENTIFIED BY 'app_pass';

SELECT 'Set "app_user" permission';
GRANT ALL PRIVILEGES ON db_sandbox.* TO 'app_user'@'%';

FLUSH PRIVILEGES;
SELECT '"app_user" created';
