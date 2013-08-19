package org.glydar.glydar.util;

import sun.security.util.BitArray;
import java.util.BitSet;

public class Bitops {

    public static byte[] flipBits(byte[] bytes) {
        byte[] output = new byte[bytes.length];
        int i = 0;
        for(byte b : bytes) {
            byte[] temp = fromByte(b).toByteArray();
            if(temp.length > 0)
                output[i] = fromByte(b).toByteArray()[0];
            else
                output[i] = (byte)0;
            i++;
        }

        return output;
    }

    public static BitSet fromByte(byte b)
    {
        BitSet bits = new BitSet(8);
        for (int i = 7; i >= 0; i--)
        {
            bits.set(i, (b & 1) == 1);
            b >>= 1;
        }
        return bits;
    }
}
