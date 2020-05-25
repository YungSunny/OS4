package main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;


public class Word {
    public static final int SIZE = 4;
    private byte[] data;

    public Word() {
        this.data = new byte[SIZE];
    }

    public Word(Word word) {
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
    protected Word clone() {  // FIXME: This method should throw: throws CloneNotSupportedException
        return new Word(this);
    }

    public boolean equals(Word word) {
        return Arrays.equals(data, word.data);
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

    public static Word intToWord(int value) {
        ByteBuffer bb = ByteBuffer.allocate(SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.clear();
        bb.putInt(value);
        Word word = new Word();
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
