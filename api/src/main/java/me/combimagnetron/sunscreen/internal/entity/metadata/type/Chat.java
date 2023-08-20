package me.combimagnetron.sunscreen.internal.entity.metadata.type;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.combimagnetron.sunscreen.internal.network.ByteBuffer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;

public record Chat(@NonNull Component component) implements MetadataType {

    public static Chat of(Component component) {
        return new Chat(component);
    }

    @Override
    public byte[] bytes() {
        final ByteBuffer buffer = ByteBuffer.empty();
        buffer.write(ByteBuffer.Adapter.STRING, GsonComponentSerializer.gson().serialize(component));
        return buffer.bytes();
    }
}
