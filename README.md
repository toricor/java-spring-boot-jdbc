# java-spring-boot-jdbc
java/spring boot/spring jdbc training 
# 

## Run the application
i) create database, create table and insert data (see the below section)  
ii)`./gradlew bootRun` or `./gradlew build && java -jar java-spring-boot-jdbc-0.0.1-SNAPSHOT.jar`

## 仕様
エンジニア向けイベント予約サイトをつくる

## API Endpoint
port: 8080
### Event(s)
- `GET    /api/events`      # All events
- `GET    /api/events/:id`  # A event
- `POST   /api/events`      # Create a new event 
- `PUT    /api/events/:id`  # Update the event
- `DELETE /api/events/:id`  # Delete the event

### User(s)
- `GET    /api/users`      # All users
- `GET    /api/users/:id`  # A user
- `POST   /api/users`      # Create a new user 
- `PUT    /api/users/:id`  # Update the user
- `DELETE /api/users/:id`  # Delete the user

### Reservation(s)
- `GET    /api/reservations`        # All reservations
- `GET    /api/reservations/joined` # All reservations with event and user names
- `GET    /api/reservations/:id`    # A reservation
- `POST   /api/reservations`        # Create a new reservation 
- `PUT    /api/reservations/:id`    # Update the reservation
- `DELETE /api/reservations/:id`    # Delete the reservation

## RDBMS
MySQL 5.6

## DDL
src/main/resources/schema.sql

## POST EXAMPLE
```
curl -H "Content-type: application/json" -X POST -d '{"name": "John"}' http://localhost:8080/api/users/1
```

