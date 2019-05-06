package com.dariuszzbyrad.processor.storage;


import com.dariuszzbyrad.processor.gis.GISCalculator;
import com.dariuszzbyrad.processor.mqtt.event.CarPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Storage {

    @Autowired
    GISCalculator gisCalculator;

    private final Map<Integer, CarInformation> cars = new HashMap<>();

    public void updateCarInformation(CarPosition carPosition) {
        initCarInformationObject(carPosition);

        updateLocation(carPosition);
        updateSpeedAndDistance(carPosition);
        updateRank();
    }

    //TODO to refactor
    private void updateRank() {
        Map<Integer, Double> sortedCarDistances = cars
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getFullDistance()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

    }

    private void updateSpeedAndDistance(CarPosition carPosition) {
        CarInformation carInformation = cars.get(carPosition.getCarIndex());
        carInformation.calculateSpeedAndDistance(gisCalculator);
    }

    private void initCarInformationObject(CarPosition carPosition) {
        int carId = carPosition.getCarIndex();

        if (!cars.containsKey(carId)) {
            cars.put(carId, new CarInformation());
        }
    }

    private void updateLocation(CarPosition carPosition) {
        CarInformation carInformation = cars.get(carPosition.getCarIndex());

        carInformation.setPrevLocation(carInformation.getCurrentLocation());
        carInformation.setCurrentLocation(carPosition.getLocation());

        carInformation.setPrevPositionTimestamp(carInformation.getCurrentPositionTimestamp());
        carInformation.setCurrentPositionTimestamp(carPosition.getTimestamp());

        carInformation.setPrevPosition(carInformation.getCurrentPosition());
    }

    public CarInformation getCarInformation(int carId) {
        if (cars.containsKey(carId)) {
            return cars.get(carId);
        }

        return new CarInformation();
    }
}
