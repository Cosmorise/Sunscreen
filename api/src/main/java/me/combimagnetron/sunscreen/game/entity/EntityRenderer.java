package me.combimagnetron.sunscreen.game.entity;

import me.combimagnetron.sunscreen.game.entity.metadata.type.Vector3d;

import java.util.HashMap;

public class EntityRenderer {
    //private final ScheduledThreadPoolExecutor threadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newVirtualThreadPerTaskExecutor();
    private final HashMap<Entity, Vector3d> entityPositionMap = new HashMap<>();

    private EntityRenderer() {

    }

}
