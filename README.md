# Distributed System – Milestone
This project is for the milestone of the Distributed Systems course. It simulates an energy community system with a simple backend (REST API) and frontend (JavaFX GUI).

## What has been implemented
- The REST API is built with Spring Boot.
- The GUI is built with JavaFX.
- Two REST endpoints are available:
  - `/energy/current` – returns current energy data.
  - `/energy/historical?start=...&end=...` – returns example historical data.
- GUI allows:
  - Viewing current energy status.
  - Selecting start and end date/time to view historical values.
- The system can run without any database or external queue.

## How to run the project
1. Start the backend:
   - Run `RestapiApplication.java`
2. Start the GUI:
   - Run `MainApp.java`
The GUI fetches data from the API and displays it directly. All values are currently mocked for demonstration.

## Notes
- This version is made to fulfill the milestone requirements.
- All components are independent and can be run separately.
- No real messaging system or database is used yet.
