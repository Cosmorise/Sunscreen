package me.combimagnetron.sunscreen.internal.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.combimagnetron.sunscreen.internal.Item;
import me.combimagnetron.sunscreen.util.ProtocolUtil;
import org.jglrxavpok.hephaistos.nbt.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ByteBuffer {
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
        write(Adapter.VAR_INT, collection.size());
        for (T t : collection) {
            write(type, t);
        }
    }

    public <T extends Writeable> void writeCollection(Collection<T> collection) {
        write(Adapter.VAR_INT, collection.size());
        for (T t : collection) {
            t.write(this);
        }
    }

    public <T extends Enum<?>> T readEnum(Class<T> clazz) {
        return clazz.getEnumConstants()[read(Adapter.VAR_INT)];
    }

    public <T> Collection<T> readCollection(Function<ByteBuffer, T> function) {
        final int size = read(Adapter.VAR_INT);
        final List<T> values = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            values.add(function.apply(this));
        }
        return values;
    }

    public <T> Collection<T> readCollection(Adapter<T> type, Supplier<Collection<T>> collectionSupplier) {
        Collection<T> collection = collectionSupplier.get();
        int size = read(Adapter.INT);
        for (int i = 0; i < size; i++) {
            collection.add(read(type));
        }
        return collection;
    }

    public ByteArrayDataOutput output() {
        return byteArrayDataOutput;
    }

    public byte[] bytes() {
        if (byteArrayDataOutput == null) {
            throw new RuntimeException();
        }
        return byteArrayDataOutput.toByteArray();
    }

    public interface Writeable {

        void write(final ByteBuffer byteBuffer);

    }

    public interface Adapter<T> {
        Adapter<String> STRING = Impl.of(ByteArrayDataInput::readUTF, ByteArrayDataOutput::writeUTF);
        Adapter<Long> LONG = Impl.of(ByteArrayDataInput::readLong, ByteArrayDataOutput::writeLong);
        Adapter<Float> FLOAT = Impl.of(ByteArrayDataInput::readFloat, ByteArrayDataOutput::writeFloat);
        Adapter<Integer> INT = Impl.of(ByteArrayDataInput::readInt, ByteArrayDataOutput::writeInt);
        Adapter<Integer> UNSIGNED_BYTE = Impl.of(ByteArrayDataInput::readUnsignedByte, ByteArrayDataOutput::writeInt);
        Adapter<Boolean> BOOLEAN = Impl.of(ByteArrayDataInput::readBoolean, ByteArrayDataOutput::writeBoolean);
        Adapter<Byte> BYTE = Impl.of(ByteArrayDataInput::readByte, (output, aByte) -> output.writeByte((int) aByte));
        Adapter<Short> SHORT = Impl.of(ByteArrayDataInput::readShort, (output, aShort) -> output.writeShort((int) aShort));
        Adapter<org.jglrxavpok.hephaistos.nbt.NBT> NBT = Impl.of(
            input -> {
                NBTReader nbtReader = new NBTReader(new InputStream() {
                    @Override
                    public int read() {
                        return input.readByte() & 0xFF;
                    }
                }, CompressedProcesser.NONE);
                try {
                    return nbtReader.read();
                } catch (IOException | NBTException e) {
                    throw new RuntimeException(e);
                }
            }, (output, value) -> {
                    NBTWriter nbtWriter = new NBTWriter(new OutputStream() {
                        @Override
                        public void write(int b) {
                            output.writeByte((byte) b);
                        }
                    }, CompressedProcesser.NONE);
                    try {
                        nbtWriter.writeNamed("", value);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
            });
        Adapter<Item<?>> ITEM = Impl.of(input -> {
            if (!input.readBoolean()) {
                return Item.empty();
            }
            int material = ProtocolUtil.readVarInt(input);
            int amount = (int) input.readByte();
            return Item.item(material, amount);
        }, ((output, item) -> {
            output.writeBoolean(item != null);
            if (item == null) {
                return;
            }
            ProtocolUtil.writeVarInt(output, (int) item.material());
            output.writeByte(item.amount());
            if (item.nbt().isEmpty()) {
                output.writeByte(0);
                return;
            }
            ByteBuffer byteBuffer = ByteBuffer.empty();
            byteBuffer.write(NBT, item.nbt());
            output.write(byteBuffer.bytes());
        }));
        Adapter<Integer> VAR_INT = Impl.of(ProtocolUtil::readVarInt, ProtocolUtil::writeVarInt);
        Adapter<Long> VAR_LONG = Impl.of(ProtocolUtil::readVarLong, ProtocolUtil::writeVarLong);
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