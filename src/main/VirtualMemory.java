package main;

public class VirtualMemory {
    Word[] memory;

    public VirtualMemory(int size) {
        memory = new Word[size];
    }

    public Word read(int address) {
        return memory[address];
    }

    public void write(Word word, int address) {
        memory[address] = word;
    }
}
