package me.combimagnetron.sunscreen.resourcepack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class ResourcePackPath implements Comparable<ResourcePackPath> {
    private final ResourcePackFeature<?> feature;
    private Path path;

    protected ResourcePackPath(ResourcePackFeature<?> feature) {
        this.feature = feature;
    }

    @Override
    public int compareTo(@NotNull ResourcePackPath o) {
        return o.path().compareTo(path);
    }

    public Path path() {
        return path;
    }

    public JsonElement json() {
        JsonObject object = new JsonObject();
        object.addProperty("path", path.toString());
        return object;
    }

}
