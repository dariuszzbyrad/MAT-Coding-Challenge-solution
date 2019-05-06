package com.dariuszzbyrad.processor.listener;

import com.dariuszzbyrad.processor.mqtt.MqttService;
import com.dariuszzbyrad.processor.mqtt.event.CarPosition;
import com.dariuszzbyrad.processor.mqtt.publisher.PublisherService;
import com.dariuszzbyrad.processor.mqtt.publisher.RankPublisher;
import com.dariuszzbyrad.processor.storage.CarInformation;
import com.dariuszzbyrad.processor.storage.Storage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RankListener implements Listener{
    @Autowired
    private Storage basicStorage;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MqttService mqttService;

    @Autowired
    private PublisherService publisher;

    @Override
    public void notify(CarPosition carPosition) {
        int carId = carPosition.getCarIndex();
        CarInformation carInformation = basicStorage.getCarInformation(carId);
        int rank = carInformation.getCurrentRank();

        if (rank != 0) {
            RankPublisher rankPublisher = new RankPublisher(mqttService.getClient(), mapper, carId, rank);

            publisher.publish(rankPublisher);
        }
    }
}
