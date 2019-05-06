package com.dariuszzbyrad.processor.listener;

import com.dariuszzbyrad.processor.mqtt.event.CarPosition;
import com.dariuszzbyrad.processor.mqtt.publisher.Publisher;
import com.dariuszzbyrad.processor.mqtt.publisher.SpeedPublisher;
import com.dariuszzbyrad.processor.mqtt.MqttService;
import com.dariuszzbyrad.processor.storage.CarInformation;
import com.dariuszzbyrad.processor.storage.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpeedListener implements Listener{
    @Autowired
    private Storage basicStorage;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MqttService mqttService;

    @Autowired
    private Publisher publisher;

    @Override
    public void notify(CarPosition carPosition) {
        int carId = carPosition.getCarIndex();
        CarInformation carInformation = basicStorage.getCarInformation(carId);
        int speed = carInformation.getInstantaneousSpeed();

        if (speed != 0) {
            SpeedPublisher speedPublisher = new SpeedPublisher(mqttService.getClient(), mapper, carId, speed);

            publisher.publish(speedPublisher);
        }
    }
}
