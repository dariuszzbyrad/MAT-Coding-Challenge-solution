package com.dariuszzbyrad.processor.service;

import com.dariuszzbyrad.processor.model.CarPosition;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.dariuszzbyrad.processor.service.MqttService.CAR_COORDINATES_TOPIC;

@Service
@Slf4j
public class GPSSource {

    @Autowired
    private IMqttClient mqttClient;

    @Autowired
    private ObjectMapper mapper;

    public void processCurrentLocation() {
        try {
            CountDownLatch receivedSignal = new CountDownLatch(10);

            mqttClient.subscribe(CAR_COORDINATES_TOPIC, (topic, msg) -> {
                Optional<CarPosition> carPosition = CarPosition.fromMqttMessage(msg, mapper);
                carPosition.ifPresent(System.out::println);

                receivedSignal.countDown();
            });

            receivedSignal.await(1, TimeUnit.MINUTES);
        } catch (MqttException | InterruptedException e) {
            log.error("Something was wrong", e);
        }
    }

}
