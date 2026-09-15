package se.group1.alarmservice.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "Alarm")
public class AlarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "Id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "DeviceId", nullable = false, length = 64)
    private String deviceId;

    @Column(name = "MeasurementId", nullable = false, length = 64)
    private String measurementId;

    @Column(name = "CreatedAt", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "MeasuredAt", nullable = false)
    private OffsetDateTime measuredAt;

    @Column(name = "Value", nullable = false)
    private BigDecimal value;

    @Column(name = "MeasurementType", nullable = false, length = 50)
    private String getMeasurementType;

    @Column(name = "Unit", nullable = false, length = 20)
    private String unit;

    @Column(name = "Message", nullable = false, length = 255)
    private String message;

    protected AlarmEntity() {

    }

    public AlarmEntity (
            String deviceId,
            String measurementId,
            OffsetDateTime createdAt,
            OffsetDateTime measuredAt,
            BigDecimal value,
            String measurementType,
            String unit,
            String message) {

        this.deviceId = deviceId;
        this.measurementId = measurementId;
        this.createdAt = createdAt;
        this.measuredAt = measuredAt;
        this.value = value;
        this.getMeasurementType = measurementType;
        this.unit = unit;
        this.message = message;

    }

    public UUID getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getMeasurementId() {
        return measurementId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getMeasuredAt() {
        return measuredAt;
    }

    public BigDecimal getvalue() {
        return value;
    }

    public String getGetMeasurementType() {
        return getMeasurementType;
    }

    public String getUnit() {
        return unit;
    }

    public String getMessage() {
        return message;
    }
}
