package com.dariuszzbyrad.processor.mqtt.publisher;

import com.dariuszzbyrad.processor.mqtt.event.CarStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Date;
import java.util.concurrent.Callable;

public class SpeedPublisher extends Publisher {
    private static final String EVENT_NAME = "SPEED";

    private int speed;

    public SpeedPublisher(IMqttClient client, ObjectMapper mapper, int carId, int speed) {
        super(client, mapper, carId);
        this.speed = speed;
    }

     protected MqttMessage prepareEvent() throws JsonProcessingException {
        CarStatus event = CarStatus.builder()
                .carIndex(carId)
                .timestamp(new Date().getTime())
                .type(EVENT_NAME)
                .value(speed)
                .build();

        String payload = mapper.writeValueAsString(event);

        return new MqttMessage(payload.getBytes());
    }
}
