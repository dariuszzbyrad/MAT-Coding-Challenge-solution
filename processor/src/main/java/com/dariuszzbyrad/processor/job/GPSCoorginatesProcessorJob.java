package com.dariuszzbyrad.processor.job;

import com.dariuszzbyrad.processor.mqtt.subscriber.CoordinatesSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GPSCoorginatesProcessorJob implements Job {

    @Autowired
    private CoordinatesSubscriber gpsCoordinates;

    @Override
    public void run() {
        gpsCoordinates.processCurrentLocation();
    }

}
