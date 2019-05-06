package com.dariuszzbyrad.processor.mqtt.publisher;

import com.dariuszzbyrad.processor.mqtt.event.CarStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Date;
import java.util.concurrent.Callable;

public class SpeedPublisher implements Callable<Void> {

    private static final String TOPIC_NAME = "carStatus";
    private static final String EVENT_NAME = "SPEED";

    private IMqttClient client;
    private ObjectMapper mapper;
    private int carId;
    private int speed;

    public SpeedPublisher(IMqttClient client, ObjectMapper mapper, int carId, int speed) {
        this.client = client;
        this.mapper = mapper;
        this.carId = carId;
        this.speed = speed;
    }

    @Override
    public Void call() throws Exception {
        if ( !client.isConnected()) {
            return null;
        }
        MqttMessage msg = prepareSpeedEvent();
        msg.setQos(0);
        msg.setRetained(true);
        client.publish(TOPIC_NAME, msg);
        return null;
    }

    private MqttMessage prepareSpeedEvent() throws JsonProcessingException {
        CarStatus event = CarStatus.builder()
                .carIndex(carId)
                .timestamp(new Date().getTime())
                .type(EVENT_NAME) //TODO fix
                .value(speed) //TODO fix
                .build();

        String payload = mapper.writeValueAsString(event);

        System.out.println(payload);

        return new MqttMessage(payload.getBytes());
    }
}
