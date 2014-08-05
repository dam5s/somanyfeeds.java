package com.somanyfeeds.feeds;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
public class FeedUpdatesScheduler implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(FeedUpdatesScheduler.class);
    private final int delayBetweenRuns = 5 * 60;
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

    private void scheduleNextRun(int seconds) {
        scheduledExecutorService.schedule(this, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        try {
            feedUpdatesService.updateAll();
        } catch (Exception e) {
            logger.error("There was an error while updating feeds", e);
        } finally {
            scheduleNextRun(delayBetweenRuns);
        }
    }
}
