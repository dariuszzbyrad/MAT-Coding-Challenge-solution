package com.dariuszzbyrad.processor.mqtt.publisher;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class PublisherService {

    private ScheduledExecutorService executorService;

    public PublisherService() {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void publish(Callable<Void> publisher) {
        executorService.schedule(publisher, 1L, TimeUnit.MILLISECONDS);
    }
}
