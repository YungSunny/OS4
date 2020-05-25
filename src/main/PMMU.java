package main;

public class PMMU {

    public final static int BLOCK_SIZE = 256;
    public final static int WORDS_IN_BLOCK = 16;

    static void write(Word word, int address) {
        if (RealMachine.getCPU().getMODE() == CPU.SUPERVISOR) {
            RealMachine.getRealMemory().write(word, address);
        } else {
            int realAddress = virtualToRealAddress(address);
            RealMachine.getRealMemory().write(word, realAddress);
        }
    }

    static Word read(int address) {

        if (RealMachine.getCPU().getMODE() == CPU.SUPERVISOR) {
            return RealMachine.getRealMemory().read(address);
        } else {
            int realAddress = virtualToRealAddress(address);
            // TO DO: can VM read this?
            return RealMachine.getRealMemory().read(realAddress);
        }
    }

    public static int virtualToRealAddress(int address) {
        /*System.out.println("a: " + ((CPU.getPTR() & 0x0000ff00) >> 8));
        System.out.println("a: " + (CPU.getPTR() & 0x000000ff));
        System.out.println("a: " + ((address & 0x0000ff00) >> 8));
        System.out.println("a: " + (address & 0x000000ff));*/
        //(CPU.getPTR() & 0x0000ff00) >> 8) * PMMU.WORDS_IN_BLOCK + (CPU.getPTR() & 0x000000ff)

        int virtualBlockIndex = (int) Math.floor(address / WORDS_IN_BLOCK);
        //System.out.println("v: " + virtualBlockIndex);
        int blockIndexAddress = CPU.getPTR()* PMMU.WORDS_IN_BLOCK + virtualBlockIndex; // * PMMU.WORDS_IN_BLOCK
        //System.out.println("b: " + blockIndexAddress);
        Word blockIndexInRealMemory = RealMachine.getRealMemory().read(blockIndexAddress);
        //System.out.println("a: " + Word.wordToInt(blockIndexInRealMemory));
        int blockRealAddress = Word.wordToInt(blockIndexInRealMemory) * PMMU.WORDS_IN_BLOCK;

        //System.out.println("PTR: " + CPU.getPTR() + " Add: " + address + " Real: " + (address - virtualBlockIndex * PMMU.WORDS_IN_BLOCK));

        return blockRealAddress + (address - virtualBlockIndex * PMMU.WORDS_IN_BLOCK);
    }
    public static void printBlock(int address){
        String output = "";
        for(int i = address; i < address + PMMU.WORDS_IN_BLOCK; i++){
            System.out.println(i + ": " + Word.wordToInt(read(i)));
        }
    }
}
