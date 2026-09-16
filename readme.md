The Alarm Service evaluates the measurement against thresholds stored in Azure SQL and stores created alarms in the database.

# System flow

DHT11
→ ESP32-S3
→ REST / JSON
→ Integration Service
→ SOAP / XML
→ Alarm Service
→ Azure SQL

# Technologies

Java
Spring Boot
Apache CXF
SOAP / WSDL
Spring Data JPA
Azure SQL
ESP32-S3
DHT11
REST / JSON
SOAP operations

# The Alarm Service implements:
EvaluateMeasurement
GetAllAlarms
GetAlarmsByDeviceId

# Start Alarm Service
Configure the Azure SQL connection in src/main/resources/application.properties.
Do not commit real database credentials to GitHub.

# Start the service:
mvn spring-boot:run

SOAP endpoint:
http://localhost:8080/soap/alarms

WSDL:
http://localhost:8080/soap/alarms?wsdl

The shared WSDL contract is located at:
src/main/resources/wsdl/alarm-service.wsdl

# Start Integration Service
From the Integration Service project:

dotnet run --urls "http://0.0.0.0:5144"

# Register ESP32
If the Integration Service uses the in-memory device service, register the device after restarting the service:

curl -i -X POST http://localhost:5144/api/devices/register \
-H "Content-Type: application/json" \
-d '{
"deviceType":"temperature-sensor",
"deviceId":"office-sensor-01"
}'

# ESP32
Configure Wi-Fi and the local IP address of the computer running the Integration Service.

The ESP32 sends measurements to:
http://YOUR_COMPUTER_IP:5144/api/measurements/save

Example JSON:

{
"measurementType": "temperature",
"deviceId": "office-sensor-01",
"value": 24.3,
"unit": "C"
}

Open Serial Monitor at 115200 baud. A successful REST request should return HTTP status 200.

End-to-end test
The complete flow has been tested with a physical DHT11 sensor.

Temperature below threshold
→ measurement processed
→ no alarm created

Temperature above threshold
→ alarm created
→ alarm stored in Azure SQL

Threshold values and alarms are stored in Azure SQL.

# Security

Do not commit real Wi-Fi passwords, database passwords or other secrets to the repository.

# Author
Sally Altaie
