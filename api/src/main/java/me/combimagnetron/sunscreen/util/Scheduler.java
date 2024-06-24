package me.combimagnetron.sunscreen.util;

import java.util.concurrent.*;

public class Scheduler {
    private final static ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(2);

    public static void delayTick(Runnable code) {
        SCHEDULED_EXECUTOR_SERVICE.schedule(code, 50L, TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> repeat(Runnable code, Duration duration) {
        return SCHEDULED_EXECUTOR_SERVICE.scheduleAtFixedRate(code, 0L, duration.time(), duration.timeUnit());
    }

    public record Duration(long time, TimeUnit timeUnit) {
    }

    public static <T> T async(Callable<T> runnable) {
        try {
            return SCHEDULED_EXECUTOR_SERVICE.schedule(runnable, 0L, TimeUnit.MICROSECONDS).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


}
