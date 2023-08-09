package me.combimagnetron.sunscreen.util;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

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
                output.writeByte(integer);
                return;
            }
            output.writeByte((integer & SEGMENT_BITS) | CONTINUE_BIT);
            integer >>>= 7;
        }
    }
}
