## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name recipe-app-mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=XXX -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE recipe_app_dev;
CREATE DATABASE recipe_app_prod;

#Create database service accounts
CREATE USER 'recipe_app_dev_user'@'localhost' IDENTIFIED BY 'XXX';
CREATE USER 'recipe_app_prod_user'@'localhost' IDENTIFIED BY 'XXX';
CREATE USER 'recipe_app_dev_user'@'%' IDENTIFIED BY 'XXX';
CREATE USER 'recipe_app_prod_user'@'%' IDENTIFIED BY 'XXX';

#Database grants
GRANT SELECT ON recipe_app_dev.* to 'recipe_app_dev_user'@'localhost';
GRANT INSERT ON recipe_app_dev.* to 'recipe_app_dev_user'@'localhost';
GRANT DELETE ON recipe_app_dev.* to 'recipe_app_dev_user'@'localhost';
GRANT UPDATE ON recipe_app_dev.* to 'recipe_app_dev_user'@'localhost';
GRANT SELECT ON recipe_app_prod.* to 'recipe_app_prod_user'@'localhost';
GRANT INSERT ON recipe_app_prod.* to 'recipe_app_prod_user'@'localhost';
GRANT DELETE ON recipe_app_prod.* to 'recipe_app_prod_user'@'localhost';
GRANT UPDATE ON recipe_app_prod.* to 'recipe_app_prod_user'@'localhost';
GRANT SELECT ON recipe_app_dev.* to 'recipe_app_dev_user'@'%';
GRANT INSERT ON recipe_app_dev.* to 'recipe_app_dev_user'@'%';
GRANT DELETE ON recipe_app_dev.* to 'recipe_app_dev_user'@'%';
GRANT UPDATE ON recipe_app_dev.* to 'recipe_app_dev_user'@'%';
GRANT SELECT ON recipe_app_prod.* to 'recipe_app_prod_user'@'%';
GRANT INSERT ON recipe_app_prod.* to 'recipe_app_prod_user'@'%';
GRANT DELETE ON recipe_app_prod.* to 'recipe_app_prod_user'@'%';
GRANT UPDATE ON recipe_app_prod.* to 'recipe_app_prod_user'@'%';