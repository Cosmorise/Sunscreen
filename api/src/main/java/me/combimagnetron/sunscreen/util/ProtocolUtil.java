package me.combimagnetron.sunscreen.util;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import java.nio.ByteBuffer;

public class ProtocolUtil {
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;

    public static int readVarInt(ByteArrayDataInput input) {
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
    }

    public static void writeVarInt(ByteArrayDataOutput output, int integer) {
        while (true) {
            if ((integer & ~SEGMENT_BITS) == 0) {
                output.writeByte((byte) integer);
                return;
            }
            output.writeByte((byte) ((integer & SEGMENT_BITS) | CONTINUE_BIT));
            integer >>>= 7;
        }
    }

    public static long readVarLong(ByteArrayDataInput input) {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = input.readByte();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }

    public static void writeVarLong(ByteArrayDataOutput output, long value) {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                output.writeByte((int) value);
                return;
            }

            output.writeByte((int) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

}
