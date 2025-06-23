# Distributed System – Energy Communities

This is the final project for the Distributed Systems course. It simulates a basic energy community using microservices. Each service runs independently and communicates using RabbitMQ. Data is stored in a PostgreSQL database and visualized through a JavaFX GUI.

## Project Structure

- **energy-producer**: sends simulated production data (in kWh) every 1–5 seconds to the message queue.
- **energy-user**: sends simulated consumption data (in kWh) every 1–5 seconds to the message queue.
- **usage-service**: receives data from both producer and user, stores it in the database (usage table), and sends an update message.
- **current-percentage-service**: receives update messages, calculates the grid portion and community depletion percentage, and stores the result in a separate table.
- **Spring-Boot-REST-API**: provides two REST endpoints to retrieve:
  - the current percentage
  - historical usage data for a selected time range
- **JavaFX-GUI**: a graphical user interface to view current values and explore historical data.

## Technologies Used

- Java 17
- Spring Boot
- JavaFX
- PostgreSQL
- RabbitMQ
- Maven

## How to Run

1. Start RabbitMQ and PostgreSQL using Docker.
2. Build and run each service separately using Maven:
 
## REST Endpoints

- `/energy/current`: returns the current grid portion and community percentage.
- `/energy/historical?start=...&end=...`: returns usage data for the given time range.
