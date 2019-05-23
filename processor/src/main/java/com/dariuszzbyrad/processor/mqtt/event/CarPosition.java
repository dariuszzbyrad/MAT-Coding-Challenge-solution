package com.dariuszzbyrad.processor.mqtt.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.util.Optional;

@Data
@Slf4j
public class CarPosition {

    @JsonProperty(value="carIndex")
    private int carIndex;

    @JsonProperty(value="location")
    private Location location;

    @JsonProperty(value="timestamp")
    private long timestamp;

    public static Optional<CarPosition> fromMqttMessage(MqttMessage msg, ObjectMapper mapper) {
        CarPosition carPosition = null;

        String payload = msg.toString();
        try {
            carPosition = mapper.readValue(payload, CarPosition.class);
        } catch (IOException e) {
            log.warn("Incorrect paylod for car position", e);
        }

        return Optional.ofNullable(carPosition);
    }
}
