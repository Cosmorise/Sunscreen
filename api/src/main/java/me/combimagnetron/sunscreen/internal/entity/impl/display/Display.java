package me.combimagnetron.sunscreen.internal.entity.impl.display;

import me.combimagnetron.sunscreen.internal.entity.Entity;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.Quaternion;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.Vector3d;
import me.combimagnetron.sunscreen.util.Pos2D;

@SuppressWarnings("unused")
public abstract class Display extends Entity.AbstractEntity {
    private int interpolationDelay = 0;
    private int interpolationDuration = 0;
    private Transformation transformation = Transformation.transformation();
    private Billboard billboard = Billboard.FIXED;
    private int brightness = -1;
    private float viewRange = 1;
    private Shadow shadow = Shadow.shadow();
    private Pos2D pos2D = Pos2D.of(0, 0);
    private int glowOverride = -1;

    @Override
    public Data data() {
        return null;
    }

    public record Transformation(Vector3d translation, Vector3d scale, Quaternion rotationLeft, Quaternion rotationRight) {

        public static Transformation transformation() {
            return of(Vector3d.vec3(0, 0, 0), Vector3d.vec3(1, 1, 1), Quaternion.of(0, 0, 0, 1), Quaternion.of(0, 0, 0, 1));
        }

        public static Transformation of(Vector3d translation, Vector3d scale, Quaternion rotationLeft, Quaternion rotationRight) {
            return new Transformation(translation, scale, rotationLeft, rotationRight);
        }

    }

    public record Shadow(float radius, float strength) {

        public static Shadow of(float radius, float strength) {
            return new Shadow(radius, strength);
        }

        public static Shadow shadow() {
            return of(0, 1);
        }

    }

    public interface Billboard {
        Billboard FIXED = of(0);
        Billboard VERTICAL = of(1);
        Billboard HORIZONTAL = of(2);
        Billboard CENTER = of(3);

        byte constraint();

        static Billboard of(int constraint) {
            return new Impl((byte)constraint);
        }

        record Impl(byte constraint) implements Billboard {

        }

    }

    public int interpolationDelay() {
        return interpolationDelay;
    }

    public void interpolationDelay(int interpolationDelay) {
        this.interpolationDelay = interpolationDelay;
    }

    public int interpolationDuration() {
        return interpolationDuration;
    }

    public void interpolationDuration(int interpolationDuration) {
        this.interpolationDuration = interpolationDuration;
    }

    public Transformation transformation() {
        return transformation;
    }

    public void transformation(Transformation transformation) {
        this.transformation = transformation;
    }

    public Billboard billboard() {
        return billboard;
    }

    public void billboard(Billboard billboard) {
        this.billboard = billboard;
    }

    public int brightness() {
        return brightness;
    }

    public void brightness(int brightness) {
        this.brightness = brightness;
    }

    public float viewRange() {
        return viewRange;
    }

    public void viewRange(float viewRange) {
        this.viewRange = viewRange;
    }

    public Shadow shadow() {
        return shadow;
    }

    public void shadow(Shadow shadow) {
        this.shadow = shadow;
    }

    public Pos2D pos2D() {
        return pos2D;
    }

    public void pos2D(Pos2D pos2D) {
        this.pos2D = pos2D;
    }

    public int glowOverride() {
        return glowOverride;
    }

    public void glowOverride(int glowOverride) {
        this.glowOverride = glowOverride;
    }
}
