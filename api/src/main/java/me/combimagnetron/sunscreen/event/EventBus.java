package me.combimagnetron.sunscreen.event;

public interface EventBus {

    <T extends Event> void post(T event);

    <C extends Event & Cancellable> void postCancellable(C event);

}
