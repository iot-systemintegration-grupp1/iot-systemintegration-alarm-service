# Alarm Service

Individual project for the System Integration course.

The project is part of a distributed IoT monitoring system.  
An ESP32 sends temperature measurements to an Integration Service, which forwards the measurement to this Alarm Service using SOAP.

The Alarm Service evaluates the measurement against configured thresholds and stores created alarms in Azure SQL.

## System flow

```text
ESP32
→ Integration Service
→ SOAP
→ Alarm Service
Alarm Service

Individual project for the System Integration course.
This project is part of a distributed IoT monitoring system.

Flow

ESP32
→ Integration Service
→ SOAP Alarm Service
→ Azure SQL

The ESP32 sends temperature measurements as JSON to the Integration Service.

The Integration Service creates a measurement ID and forwards the measurement to the Alarm Service using SOAP.

The Alarm Service checks the configured threshold and stores an alarm in Azure SQL if the threshold is exceeded.

# Technologies

- Java
- Spring Boot
- Apache CXF
- SOAP / WSDL
- Spring Data JPA
- Azure SQL
- ESP32
- HTTP / JSON

# Start Alarm Service

Set database credentials:

export DB_USER='your_username'
export DB_PASSWORD='your_password'

Start the service:
mvn spring-boot:run

SOAP endpoint:
http://localhost:8080/soap/alarms

WSDL:
http://localhost:8080/soap/alarms?wsdl

# Start Integration Service

From the Integration Service project:
dotnet run --urls "http://0.0.0.0:5144"

# Register ESP32

The device must be registered again if the Integration Service is restarted.

curl -i -X POST http://localhost:5144/api/devices/register \
  -H "Content-Type: application/json" \
  -d '{
    "deviceType":"temperature-sensor",
    "deviceId":"office-sensor-01"
  }'

# ESP32

Configure Wi-Fi and the local IP address of the computer running the Integration Service:

const char* wifiName = "YOUR_WIFI_NAME";
const char* wifiPassword = "YOUR_WIFI_PASSWORD";

const char* url =
    "http://YOUR_COMPUTER_IP:5144/api/measurements/temperature";

Upload the sketch and open Serial Monitor at:

115200

A successful request should return:

HTTP response: 200
## End-to-end test with DHT11
The complete flow has been tested with a real DHT11 temperature sensor connected to the ESP32.
Verified flow:

```text
DHT11
→ ESP32
→ HTTP/JSON
→ Integration Service
→ SOAP Alarm Service
→ Azure SQL

Temperature below 30 °C
→ measurement received
→ no alarm created

Temperature above 30 °C
→ measurement received
→ alarm created
→ alarm stored in Azure SQL

# Security
Do not commit real:
- Wi-Fi passwords
- Database passwords
- API secrets

Database credentials are supplied using the environment variables DB_USER and DB_PASSWORD.

# Author
Sally Altiae