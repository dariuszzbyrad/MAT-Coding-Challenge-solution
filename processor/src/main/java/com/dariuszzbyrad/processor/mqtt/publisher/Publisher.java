package com.dariuszzbyrad.processor.mqtt.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.concurrent.Callable;

public abstract class Publisher implements Callable<Void> {
    protected final String TOPIC_NAME = "carStatus";

    protected IMqttClient client;
    protected ObjectMapper mapper;
    protected int carId;

    public Publisher(IMqttClient client, ObjectMapper mapper, int carId) {
        this.client = client;
        this.mapper = mapper;
        this.carId = carId;
    }

    @Override
    public Void call() throws Exception {
        if ( !client.isConnected()) {
            return null;
        }
        MqttMessage msg = prepareEvent();
        msg.setQos(0);
        msg.setRetained(true);
        client.publish(TOPIC_NAME, msg);

        return null;
    }

    protected abstract MqttMessage prepareEvent() throws JsonProcessingException;

    ;
}
