package com.dariuszzbyrad.processor.listener;

import com.dariuszzbyrad.processor.mqtt.event.CarPosition;
import com.dariuszzbyrad.processor.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasicStorageListener implements Listener{
    @Autowired
    private Storage storage;

    @Override
    public void notify(CarPosition carPosition) {
        storage.updateCarInformation(carPosition);
    }
}
