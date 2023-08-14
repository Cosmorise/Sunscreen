package me.combimagnetron.sunscreen.v1_20_1.type;

import me.combimagnetron.sunscreen.v1_20_1.type.mapping.Mapping;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Function;

public interface TypeTransformer<T, V> {
    TypeTransformer<Component, net.minecraft.network.chat.Component> COMPONENT_COMPONENT = TypeTransformer.of(component -> net.minecraft.network.chat.Component.literal(GsonComponentSerializer.gson().serialize(component)));
    TypeTransformer<Integer, MenuType<?>> INTEGER_MENU_TYPE = TypeTransformer.of(integer -> BuiltInRegistries.MENU.get(ResourceLocation.of(Mapping.MENU_TYPE.convert(integer), ':')));

    Function<T, V> transformer();

    default V transform(T t) {
        return transformer().apply(t);
    }

    static <T, V> TypeTransformer<T, V> of(Function<T, V> transformer) {
        return new Impl<>(transformer);
    }

    record Impl<T, V>(Function<T, V> transformer) implements TypeTransformer<T, V> {
    }

}