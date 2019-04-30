package com.dariuszzbyrad.processor;

import com.dariuszzbyrad.processor.job.MainJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProcessorApplication {

    private static ApplicationContext context;

    public static void main(String[] args) {
        context  = SpringApplication.run(ProcessorApplication.class, args);

        MainJob mainJob = context.getBean(MainJob.class);
        mainJob.run();

    }

}
