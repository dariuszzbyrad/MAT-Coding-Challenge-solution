package com.dariuszzbyrad.processor.mqtt.publisher;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class Publisher {

    private ScheduledExecutorService executorService;

    public Publisher() {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void publish(SpeedPublisher speedPublisher) {
        executorService.schedule(speedPublisher, 1L, TimeUnit.MILLISECONDS);
    }
}
