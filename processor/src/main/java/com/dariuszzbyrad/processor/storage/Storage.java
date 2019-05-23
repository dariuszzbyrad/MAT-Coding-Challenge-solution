package com.dariuszzbyrad.processor.storage;


import com.dariuszzbyrad.processor.gis.GISCalculator;
import com.dariuszzbyrad.processor.mqtt.event.CarPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
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
        updateRank(carPosition);
    }

    //TODO to refactor + position -> rank
    private void updateRank(CarPosition carPosition) {
        int carId = carPosition.getCarIndex();
        CarInformation carInformation = cars.get(carId);
        double currentDistance = carInformation.getFullDistance();

        List<Double> ranks = cars.values().stream()
                .mapToDouble(c -> c.getFullDistance())
                .filter(d -> d > 0)
                .sorted().boxed()
                .collect(Collectors.toList());

        Collections.reverse(ranks);

        int currentRank = ranks.indexOf(currentDistance) + 1;

        carInformation.setPrevRank(carInformation.getCurrentRank());
        carInformation.setCurrentRank(currentRank);
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
    }

    public CarInformation getCarInformation(int carId) {
        if (cars.containsKey(carId)) {
            return cars.get(carId);
        }

        return new CarInformation();
    }
}
