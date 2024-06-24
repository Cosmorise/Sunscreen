package me.combimagnetron.sunscreen.internal;

public record Position(float x, float y, float z, float pitch, float yaw) {

    public static Position pos(float x, float y, float z, float pitch, float yaw) {
        return new Position(x, y, z, pitch, yaw);
    }


}
