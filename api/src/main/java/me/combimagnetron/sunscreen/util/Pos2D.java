package me.combimagnetron.sunscreen.util;

public record Pos2D(double x, double y) {

    public static Pos2D of(double x, double y) {
        return new Pos2D(x, y);
    }

    public Pos2D mul(double factor) {
        return new Pos2D(this.x * factor, this.y * factor);
    }

    public Pos2D mul(double x, double y) {
        return new Pos2D(this.x * x, this.y * y);
    }

    public Pos2D mul(Pos2D pos2D) {
        return mul(pos2D.x, pos2D.y);
    }

    public Pos2D div(double factor) {
        return new Pos2D(this.x / factor, this.y / factor);
    }

    public Pos2D div(double x, double y) {
        return new Pos2D(this.x / x, this.y / y);
    }

    public Pos2D div(Pos2D pos2D) {
        return div(pos2D.x, pos2D.y);
    }

    public Pos2D add(double factor) {
        return new Pos2D(this.x + factor, this.y + factor);
    }

    public Pos2D add(double x, double y) {
        return new Pos2D(this.x + x, this.y + y);
    }

    public Pos2D add(Pos2D pos2D) {
        return add(pos2D.x, pos2D.y);
    }

    public Pos2D sub(double factor) {
        return new Pos2D(this.x - factor, this.y - factor);
    }

    public Pos2D sub(double x, double y) {
        return new Pos2D(this.x - x, this.y - y);
    }

    public Pos2D sub(Pos2D pos2D) {
        return sub(pos2D.x, pos2D.y);
    }

}