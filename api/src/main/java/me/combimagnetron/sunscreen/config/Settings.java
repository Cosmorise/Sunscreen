package me.combimagnetron.sunscreen.config;

public class Settings {
    private boolean strict = false;
    private int indent = 4;

    public static Settings settings() {
        return new Settings();
    }

    public Settings strict() {
        this.strict = true;
        return this;
    }

    public Settings strict(boolean bool) {
        this.strict = bool;
        return this;
    }
}
