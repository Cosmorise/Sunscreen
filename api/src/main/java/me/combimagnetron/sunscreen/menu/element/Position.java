package me.combimagnetron.sunscreen.menu.element;

public interface Position {

    double x();

    double y();

    static Position pixel(double x, double y) {
        return new PixelPosition(x, y);
    }

    static Position percentage(double x, double y) {
        return new RelativePosition(x, y);
    }

    record PixelPosition(double x, double y) implements Position {

    }

    final class RelativePosition implements Position {
        private final double relativeX;
        private final double relativeY;
        private int x, y;

        RelativePosition(double x, double y) {
            this.relativeX = x/100;
            this.relativeY = y/100;
        }

        public void set(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public double x() {
            return relativeX * x;
        }

        @Override
        public double y() {
            return relativeY * y;
        }
    }


}
