package me.combimagnetron.sunscreen.internal.entity.impl;

import me.combimagnetron.sunscreen.internal.entity.Entity;
import me.combimagnetron.sunscreen.internal.entity.metadata.Metadata;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.Boolean;
import me.combimagnetron.sunscreen.internal.entity.metadata.type.Float;

public class Interaction extends Entity.AbstractEntity {
    private float width;
    private float height;
    private boolean responsive;

    @Override
    public Data data() {
        return null;
    }

    @Override
    public Metadata extend() {
        return Metadata.of(
                Float.of(width),
                Float.of(height),
                Boolean.of(responsive)
        );
    }
}
