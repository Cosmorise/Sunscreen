package me.combimagnetron.sunscreen.event;

import me.combimagnetron.sunscreen.screen.Screen;
import me.combimagnetron.sunscreen.user.User;
import me.combimagnetron.sunscreen.util.Scheduler;

import java.util.Objects;

public final class UserCloseScreenEvent implements MenuEvent, Cancellable {
    private final User<?> user;
    private final Screen screen;
    private boolean cancelled = false;

    public UserCloseScreenEvent(User<?> user, Screen screen) {
        this.user = user;
        this.screen = screen;
    }

    public static UserCloseScreenEvent of(User<?> user, Screen screen) {
        return new UserCloseScreenEvent(user, screen);
    }

    @Override
    public boolean cancelled() {
        return cancelled;
    }

    @Override
    public void cancel(boolean bool) {
        this.cancelled = bool;
        if (!bool) {
            return;
        }
        Scheduler.delayTick(() -> user.show(screen));
    }

    @Override
    public User<?> user() {
        return user;
    }

    public Screen screen() {
        return screen;
    }

}
