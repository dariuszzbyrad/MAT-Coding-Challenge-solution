package com.dariuszzbyrad.processor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class MqttService {

    @Value("${mqtt.url}")
    private String mqttUrl = "tcp://localhost:1883";

    public static final String CAR_COORDINATES_TOPIC = "carCoordinates";

    private static final String publisherId = UUID.randomUUID().toString();

    @Bean
    public IMqttClient getClient() {
        IMqttClient client = null;

        try {
            client = new MqttClient(mqttUrl,publisherId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            client.connect(options);
        } catch (MqttException e) {
            log.error("Something was wrong", e);
        }

        return client;
    }

}
