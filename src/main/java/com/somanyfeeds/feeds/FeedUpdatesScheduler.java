package com.somanyfeeds.feeds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class FeedUpdatesScheduler implements Runnable {
    private final int delayBetweenRuns = 10;
    private final ScheduledExecutorService scheduledExecutorService;
    private final FeedUpdatesService feedUpdatesService;

    @Autowired
    public FeedUpdatesScheduler(ScheduledExecutorService scheduledExecutorService, FeedUpdatesService feedUpdatesService) {
        this.scheduledExecutorService = scheduledExecutorService;
        this.feedUpdatesService = feedUpdatesService;
    }

    public void start() {
        scheduleNextRun(0);
    }

    private void scheduleNextRun(int minutes) {
        scheduledExecutorService.schedule(this, minutes, TimeUnit.MINUTES);
    }

    @Override
    public void run() {
        feedUpdatesService.updateAll();
        scheduleNextRun(delayBetweenRuns);
    }
}
