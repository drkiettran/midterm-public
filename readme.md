# JDBC sample project

## Activities

### Make sure MySQL database is up and running:

```
mysql -u student -p
use sakila;
show tables;
desc actor;
desc actor_info;
select first_name, last_name from actor;
select film_info from actor_info where last_name='GABLE'
```
### Build source code

```
mvn clean package
```

### Run the app:

```
java -cp ./target/greetings-jdbc-jar-with-dependencies.jar com.drkiettran.jdbc.greetings.Main WILLIS
```