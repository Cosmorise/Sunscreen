package me.combimagnetron.sunscreen.internal.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ByteBuffer {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;
    private ByteArrayDataInput byteArrayDataInput;
    private ByteArrayDataOutput byteArrayDataOutput;
    public static ByteBuffer of(byte[] bytes) {
        return new ByteBuffer(bytes);
    }
    public static ByteBuffer empty() {
        return new ByteBuffer();
    }
    private ByteBuffer(byte[] bytes) {
        this.byteArrayDataInput = ByteStreams.newDataInput(bytes);
    }
    private ByteBuffer() {
        this.byteArrayDataOutput = ByteStreams.newDataOutput();
    }
    public <T> ByteBuffer write(Adapter<T> type, T object) {
        type.write(byteArrayDataOutput, object);
        return this;
    }
    public <T> T read(Adapter<T> type) {
        return type.read(byteArrayDataInput);
    }
    public <T> void writeCollection(Adapter<T> type, Collection<T> collection) {
        write(Adapter.INT, collection.size());
        for (T t : collection) {
            write(type, t);
        }
    }
    public <T> Collection<T> readCollection(Adapter<T> type, Supplier<Collection<T>> collectionSupplier) {
        Collection<T> collection = collectionSupplier.get();
        int size = read(Adapter.INT);
        for (int i = 0; i < size; i++) {
            collection.add(read(type));
        }
        return collection;
    }
    public byte[] bytes() {
        if (byteArrayDataOutput == null) {
            throw new RuntimeException();
        }
        return byteArrayDataOutput.toByteArray();
    }
    public interface Adapter<T> {
        Adapter<String> STRING = Impl.of(ByteArrayDataInput::readUTF, ByteArrayDataOutput::writeUTF);
        Adapter<Integer> INT = Impl.of(ByteArrayDataInput::readInt, ByteArrayDataOutput::writeInt);
        Adapter<Integer> UNSIGNED_BYTE = Impl.of(ByteArrayDataInput::readUnsignedByte, ByteArrayDataOutput::writeInt);
        Adapter<Byte> BYTE = Impl.of(ByteArrayDataInput::readByte, (output, aByte) -> output.writeByte((int) aByte));
        Adapter<Short> SHORT = Impl.of(ByteArrayDataInput::readShort, ((output, aShort) -> output.writeShort((int) aShort)));
        Adapter<Integer> VAR_INT = Impl.of(input -> {
            int value = 0;
            int position = 0;
            byte currentByte;
            while (true) {
                currentByte = input.readByte();
                value |= (currentByte & SEGMENT_BITS) << position;
                if ((currentByte & CONTINUE_BIT) == 0) break;
                position += 7;
                if (position >= 32) throw new RuntimeException("VarInt is too big");
            }
            return value;
            }, ((output, integer) -> {
            while (true) {
                if ((integer & ~SEGMENT_BITS) == 0) {
                    output.writeByte(integer);
                    return;
                }
                output.writeByte((integer & SEGMENT_BITS) | CONTINUE_BIT);
                integer >>>= 7;
            }
        }));
        T read(ByteArrayDataInput byteArrayDataInput);
        void write(ByteArrayDataOutput output, T object);
        final class Impl<V> implements Adapter<V> {
            private final Function<ByteArrayDataInput, V> readFunction;
            private final BiConsumer<ByteArrayDataOutput, V> writeConsumer;
            private Impl(Function<ByteArrayDataInput, V> readFunction, BiConsumer<ByteArrayDataOutput, V> writeConsumer) {
                this.readFunction = readFunction;
                this.writeConsumer = writeConsumer;
            }
            public static <V> Impl<V> of(Function<ByteArrayDataInput, V> readFunction, BiConsumer<ByteArrayDataOutput, V> writeConsumer) {
                return new Impl<>(readFunction, writeConsumer);
            }
            @Override
            public V read(ByteArrayDataInput byteArrayDataInput) {
                return readFunction.apply(byteArrayDataInput);
            }
            @Override
            public void write(ByteArrayDataOutput output, V object) {
                writeConsumer.accept(output, object);
            }
        }

    }
}