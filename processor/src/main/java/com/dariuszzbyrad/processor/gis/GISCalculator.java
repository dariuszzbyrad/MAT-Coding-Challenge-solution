package com.dariuszzbyrad.processor.gis;

import com.dariuszzbyrad.processor.mqtt.event.Location;
import org.springframework.stereotype.Component;

@Component
public class GISCalculator {

    private static final int RADIUS_OF_THE_EARTH = 6371;

    public double calculateDistance(Location first, Location second) {
        if (first == Location.EMPTY || second == Location.EMPTY) {
            return 0;
        }

        return distance(first.getLat(), second.getLat(), first.getLon(), second.getLon());
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0
     *
     * @returns Distance in Meters
     */
    //TODO refactor
    private double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = RADIUS_OF_THE_EARTH * c * 1000; // convert to meters

        return distance;
    }

}
