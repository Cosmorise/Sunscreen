package me.combimagnetron.sunscreen.internal.entity;

import me.combimagnetron.sunscreen.internal.entity.metadata.type.Boolean;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.Byte;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.OptChat;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.Pose;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.VarInt;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.internal.entity.metadata.Metadata;
import net.kyori.adventure.text.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public interface Entity {

    Data data();

    interface Data {

    }

    abstract class AbstractEntity implements Entity {
        private final Metadata.Template template = Metadata.BASE;
        private final EntityId id = EntityId.next();
        private final UUID uuid = UUID.randomUUID();
        private boolean onFire = false;
        private boolean crouching = false;
        private boolean sprinting = false;
        private boolean swimming = false;
        private boolean invisible = false;
        private boolean glowing = false;
        private boolean flyingElytra = false;
        private Component name = Component.empty();
        private int air = 300;
        private boolean nameVisible = true;
        private boolean silent = false;
        private boolean noGravity = false;
        private Pose pose = Pose.of(Pose.Value.STANDING);
        private int frozenPowderedSnow = 0;
        private Metadata metadata;

        public void setOnFire(boolean onFire) {
            this.onFire = onFire;
        }

        public AbstractEntity() {
            prepare();
            metadata = Metadata.merge(metadata, extend());
        }

        public void setCrouching(boolean crouching) {
            this.crouching = crouching;
        }

        public void setSprinting(boolean sprinting) {
            this.sprinting = sprinting;
        }

        public void setSwimming(boolean swimming) {
            this.swimming = swimming;
        }

        public void setInvisible(boolean invisible) {
            this.invisible = invisible;
        }

        public void setGlowing(boolean glowing) {
            this.glowing = glowing;
        }

        public void setFlyingElytra(boolean flyingElytra) {
            this.flyingElytra = flyingElytra;
        }

        public void setName(Component name) {
            this.name = name;
        }

        public void setAir(int air) {
            this.air = air;
        }

        public void setNameVisible(boolean nameVisible) {
            this.nameVisible = nameVisible;
        }

        public void setSilent(boolean silent) {
            this.silent = silent;
        }

        public void setNoGravity(boolean noGravity) {
            this.noGravity = noGravity;
        }

        public void setPose(Pose pose) {
            this.pose = pose;
        }

        public void setFrozenPowderedSnow(int frozenPowderedSnow) {
            this.frozenPowderedSnow = frozenPowderedSnow;
        }

        public EntityId id() {
            return id;
        }

        public UUID uuid() {
            return uuid;
        }

        public boolean onFire() {
            return onFire;
        }

        public boolean crouching() {
            return crouching;
        }

        public boolean sprinting() {
            return sprinting;
        }

        public boolean swimming() {
            return swimming;
        }

        public boolean invisible() {
            return invisible;
        }

        public boolean glowing() {
            return glowing;
        }

        public boolean flyingElytra() {
            return flyingElytra;
        }

        public Component name() {
            return name;
        }

        public int air() {
            return air;
        }

        public boolean nameVisible() {
            return nameVisible;
        }

        public boolean silent() {
            return silent;
        }

        public boolean noGravity() {
            return noGravity;
        }

        public Pose pose() {
            return pose;
        }

        public int frozenPowderedSnow() {
            return frozenPowderedSnow;
        }

        public abstract Metadata extend();

        void prepare() {
            this.metadata = template.apply(
                    Byte.of((byte)0),
                    VarInt.of(air),
                    OptChat.of(name),
                    Boolean.of(nameVisible),
                    Boolean.of(silent),
                    Boolean.of(noGravity),
                    pose,
                    VarInt.of(frozenPowderedSnow)
            );
        }

    }

    record EntityId(int intValue) {
        private static final AtomicInteger INTEGER = new AtomicInteger(Integer.MIN_VALUE);

        public static EntityId next() {
            return new EntityId(INTEGER.getAndIncrement());
        }

    }

    interface Type {

        int id();

        Identifier identifier();

        Metadata metadata();

        record Impl(int id, Identifier identifier, Metadata metadata) {

        }

    }

}
