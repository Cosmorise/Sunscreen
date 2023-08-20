package me.combimagnetron.sunscreen.v1_20_1.type;

import me.combimagnetron.sunscreen.internal.Item;
import me.combimagnetron.sunscreen.v1_20_1.type.mapping.Mapping;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftMagicNumbers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Function;

public interface TypeTransformer<T, V> {
    TypeTransformer<Component, net.minecraft.network.chat.Component> COMPONENT_COMPONENT = TypeTransformer.of(component -> net.minecraft.network.chat.Component.Serializer.fromJson(GsonComponentSerializer.gson().serialize(component)));
    TypeTransformer<Integer, MenuType<?>> INTEGER_MENU_TYPE = TypeTransformer.of(integer -> BuiltInRegistries.MENU.get(ResourceLocation.of(Mapping.MENU_TYPE.convert(integer), ':')));
    TypeTransformer<Item<?>, ItemStack> ITEM_ITEM_STACK = TypeTransformer.of(item -> {
        Material material = null;
        if (item.material() instanceof Material material1) {
            material = material1;
        } else {
            material = Arrays.stream(Material.values()).filter(m -> {
                final int id;
                try {
                    Field field = Material.class.getDeclaredField("id");
                    field.setAccessible(true);
                    id = (int) field.get(m);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
                return id == (int) item.material();
            }).findAny().orElseThrow();
        }
        return new ItemStack(CraftMagicNumbers.getItem(material));
    });

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