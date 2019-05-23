package com.dariuszzbyrad.processor.listener;

import com.dariuszzbyrad.processor.mqtt.event.CarPosition;

public interface Listener {
    void notify(CarPosition carPosition);
}
