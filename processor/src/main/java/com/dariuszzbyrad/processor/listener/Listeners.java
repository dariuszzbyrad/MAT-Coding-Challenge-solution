package com.dariuszzbyrad.processor.listener;

import com.dariuszzbyrad.processor.mqtt.event.CarPosition;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Listeners {

    private final List<Listener> listeners = new LinkedList<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void notify(CarPosition carPosition) {
        listeners.forEach(listener -> listener.notify(carPosition));
    }

}
