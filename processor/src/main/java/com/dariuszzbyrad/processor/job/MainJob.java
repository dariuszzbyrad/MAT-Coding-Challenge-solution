package com.dariuszzbyrad.processor.job;

import com.dariuszzbyrad.processor.service.GPSSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MainJob {

    @Autowired
    private GPSSource gpsSource;

    @Async
    public void run() {
        gpsSource.processCurrentLocation();
    }

}
