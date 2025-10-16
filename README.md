# Parking System (Spring Boot)

A simple parking system REST API built with Spring Boot 3, JPA, and PostgreSQL. It supports vehicle registration, parking slot management, and parking/unparking vehicles with tickets.

---

## Prerequisites
- Java 17+
- Maven 3.9+
- Docker (optional, for PostgreSQL via Docker Compose)

---

## Quick Start

### 1) Start PostgreSQL via Docker Compose
```bash
cd /Users/abhijeet/Downloads/Parking-System
docker compose up -d
```
This starts PostgreSQL on port 5433 with DB `parkingdb`, user `parking_user`, password `parking_pass`.

### 2) Configure Spring Boot
The application is already configured to connect to the Docker DB via the root-level `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/parkingdb
spring.datasource.username=parking_user
spring.datasource.password=parking_pass
spring.jpa.hibernate.ddl-auto=update
```

### 3) Run the App
```bash
./mvnw spring-boot:run
```
App starts on `http://localhost:8080`.

### 4) Health Check
```bash
curl -s http://localhost:8080/ping
# pong
```

---

## Security
`SecurityConfig` disables CSRF/basic/form login and permits unauthenticated access to `/ping` and `/api/**`.

---

## Data Model

- Vehicle
  - `id: UUID`
  - `licensePlate: string` (unique, required)
  - `ownerName: string` (required)
  - `vehicleType: enum` one of `CAR`, `BIKE`, `TRUCK`

- ParkingSlot
  - `id: UUID`
  - `slotNumber: string` (unique, required)
  - `vehicleType: enum` one of `CAR`, `BIKE`, `TRUCK`
  - `isAvailable: boolean` (default true)

- ParkingTicket
  - `id: UUID`
  - `vehicle: Vehicle` (required)
  - `parkingSlot: ParkingSlot` (required)
  - `entryTime: LocalDateTime`
  - `exitTime: LocalDateTime | null`

---

## API Overview

Base URL: `http://localhost:8080`

### Health
- `GET /ping` → `"pong"`

### Vehicles
- `POST /api/vehicles` register vehicle
- `GET /api/vehicles` list vehicles
- `GET /api/vehicles/{id}` get vehicle by ID

### Parking Slots
- `POST /api/slots` create parking slot
- `GET /api/slots` list slots with optional filters `available`, `type`

### Parking
- `POST /api/park` park a vehicle (by license plate in request)
- `POST /api/unpark/{ticketId}` unpark by ticket ID
- `GET /api/tickets/{id}` get ticket details

---

## cURL Examples

### Health
```bash
curl -s http://localhost:8080/ping
```

### Vehicles

Register vehicle:
```bash
curl -s -X POST http://localhost:8080/api/vehicles \
  -H 'Content-Type: application/json' \
  -d '{
    "licensePlate": "MH12AB1234",
    "ownerName": "Alice",
    "vehicleType": "CAR"
  }'
```

List vehicles:
```bash
curl -s http://localhost:8080/api/vehicles
```

Get vehicle by ID:
```bash
VEHICLE_ID="<uuid>" # e.g. 2d31e8a5-1c2b-4b52-8c7e-1b7a7ff0b3d1
curl -s http://localhost:8080/api/vehicles/$VEHICLE_ID
```

### Parking Slots

Create slot:
```bash
curl -s -X POST http://localhost:8080/api/slots \
  -H 'Content-Type: application/json' \
  -d '{
    "slotNumber": "S1",
    "vehicleType": "CAR",
    "available": true
  }'
```

List all slots:
```bash
curl -s http://localhost:8080/api/slots
```

List available CAR slots:
```bash
curl -s "http://localhost:8080/api/slots?available=true&type=CAR"
```

### Parking Flow

Park a vehicle (by license plate):
```bash
curl -s -X POST http://localhost:8080/api/park \
  -H 'Content-Type: application/json' \
  -d '{
    "vehicle": { "licensePlate": "MH12AB1234" }
  }'
# Response contains a ParkingTicket with id, vehicle, parkingSlot, entryTime
```

Unpark a vehicle (by ticket ID):
```bash
TICKET_ID="<uuid>"
curl -s -X POST http://localhost:8080/api/unpark/$TICKET_ID
# Response is the updated ParkingTicket including exitTime
```

Get ticket details:
```bash
TICKET_ID="<uuid>"
curl -s http://localhost:8080/api/tickets/$TICKET_ID
```

---

## Error Handling
- Validation errors return `400` with `{ "error": "message" }`.
- Not found returns `404` when ticket/vehicle not found.
- Server errors return `500` with `{ "error": "Internal server error" }`.

---

## Development Notes
- Entities use JPA with `ddl-auto=update` for schema evolution in dev.
- Logging is enabled; check the console for SQL and request logs.
- Security allows all requests to `/api/**` for simplicity.

---

## Useful Commands
- Build: `./mvnw clean package`
- Run: `./mvnw spring-boot:run`
- Check DB container logs: `docker logs -f parking_postgres`

---

## License
MIT (or your preferred license)
