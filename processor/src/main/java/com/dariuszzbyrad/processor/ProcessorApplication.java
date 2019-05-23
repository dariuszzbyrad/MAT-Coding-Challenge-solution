package com.dariuszzbyrad.processor;

import com.dariuszzbyrad.processor.job.Job;
import com.dariuszzbyrad.processor.job.GPSCoorginatesProcessorJob;
import com.dariuszzbyrad.processor.listener.BasicStorageListener;
import com.dariuszzbyrad.processor.listener.Listeners;
import com.dariuszzbyrad.processor.listener.RankListener;
import com.dariuszzbyrad.processor.listener.SpeedListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProcessorApplication {

    private static ApplicationContext context;

    public static void main(String[] args) {
        context  = SpringApplication.run(ProcessorApplication.class, args);

        Listeners listeners = context.getBean(Listeners.class);
        BasicStorageListener basicStorageListener = context.getBean(BasicStorageListener.class);
        SpeedListener speedListener = context.getBean(SpeedListener.class);
        RankListener rankListener = context.getBean(RankListener.class);

        listeners.addListener(basicStorageListener);
        listeners.addListener(speedListener);
        listeners.addListener(rankListener);

        Job mainJob = context.getBean(GPSCoorginatesProcessorJob.class);
        mainJob.run();
    }

}
