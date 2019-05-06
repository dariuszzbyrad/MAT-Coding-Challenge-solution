package com.dariuszzbyrad.processor.storage;

import com.dariuszzbyrad.processor.gis.GISCalculator;
import com.dariuszzbyrad.processor.mqtt.event.Location;
import lombok.Data;

@Data
public class CarInformation {

    private Location prevLocation = Location.EMPTY;

    private Location currentLocation = Location.EMPTY;

    private Long prevPositionTimestamp;

    private Long currentPositionTimestamp;

    private int instantaneousSpeed = 0;

    private double fullDistance = 0;

    private int prevPosition;

    private int currentPosition;

    public void calculateSpeedAndDistance(GISCalculator gisCalculator) {
        if (prevLocation == Location.EMPTY) {
            return;
        }

        double diffDistance = gisCalculator.calculateDistance(prevLocation, currentLocation); //in meters
        long diffTime = Math.abs(currentPositionTimestamp - prevPositionTimestamp) ; //in milliseconds

        instantaneousSpeed = (int) ((diffDistance * 3600) / diffTime); //in km/h
        fullDistance += diffDistance;

        //System.out.println(instantaneousSpeed);
    }
}
