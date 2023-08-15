package me.combimagnetron.sunscreen.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private final static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public static void delayTick(Runnable code) {
        SCHEDULED_EXECUTOR_SERVICE.schedule(code, 50L, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> repeat(Runnable code, Duration duration) {
        return SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(code, 0L, duration.time(), duration.timeUnit());
    }

    public record Duration(long time, TimeUnit timeUnit) {
    }


}
