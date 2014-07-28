package com.somanyfeeds;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.somanyfeeds.feeds.FeedUpdatesScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import java.util.concurrent.*;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        FeedUpdatesScheduler feedUpdatesScheduler = ctx.getBean(FeedUpdatesScheduler.class);
        feedUpdatesScheduler.start();
    }

    @Bean
    ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(2);
    }

    @Bean
    HttpTransport httpTransport() {
        return new NetHttpTransport();
    }
}
