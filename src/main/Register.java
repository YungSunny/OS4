package main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import javax.swing.plaf.SeparatorUI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Register {
    public static final int SIZE = 4;
    private byte[] data; // FIXME: make data final?

    public Register() {
        this.data = new byte[SIZE];
    }

    public Register(Register word) {
        this.data = word.data.clone();
    }

    public byte getByte(int index) {
        return data[index];
    }

    // TODO: setByte, but should data be final?..
    public void setByte(int index, byte info) {
        data[index] = info;
    }

    @Override
    protected Register clone() {
        return new Register(this);
    }

    public static int wordToInt(Word word) {
        ByteBuffer bb = ByteBuffer.allocateDirect(SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.clear();
        for (int i = 0; i < SIZE; i++) {
            bb.put(word.getByte(i));
        }
        bb.position(0);
        return bb.getInt();
    }

    public static Register intToWord(int value) {
        ByteBuffer bb = ByteBuffer.allocate(SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.clear();
        bb.putInt(value);
        Register word = new Register();
        for (int i = 0; i < SIZE; i++) {
            word.setByte(i, bb.get(i));  // FIXME: data final????
        }
        return word;
    }

    public static String wordsToString(Word[] words) {
        String string = "";
        for (Word w : words) {
            //System.out.println(string);
            int wordAsInt = Word.wordToInt(w);
            for (int i = 0; i < SIZE; i++) {
                string += (char) w.getByte(i);
            }
        }
        return string;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }
}
