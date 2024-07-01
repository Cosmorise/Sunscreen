package me.combimagnetron.sunscreen.resourcepack;

import me.combimagnetron.sunscreen.SunscreenLibrary;

import java.nio.file.Path;

public interface ResourcePackFeature<T> {
    Path ASSETS_FOLDER = SunscreenLibrary.library().path().resolve("pack/assets/sunscreen/");



}
