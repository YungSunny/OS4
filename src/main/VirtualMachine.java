package main;

public class VirtualMachine {

    private VirtualCPU virtualCPU;

    private int index;

    // Memory
    private VirtualMemory virtualMemory;

    public static final int MEMORY_SIZE = 256;

    final static int DATA_START = 0;
    final static int DATA_SIZE = 112;

    final static int PROGRAM_START = 112;
    final static int PROGRAM_SIZE = 112;

    final static int STACK_START = 224;

    final int PC_ADDRESS = 109;
    final int SP_ADDRESS = 110;
    final int PID_ADDRESS = 111;

    // Default constructor
    public VirtualMachine(int index){
        this.virtualCPU = new VirtualCPU();
        this.virtualMemory = new VirtualMemory(MEMORY_SIZE);
        this.index = index;
    }

    public VirtualMemory getVirtualMemory(){
        return virtualMemory;
    }

    public VirtualMachine clone(){
        VirtualMachine VM = RealMachine.createVirtualMachine();
        VirtualMemory virtualMemory = VM.getVirtualMemory();

        for(int i = 0; i < MEMORY_SIZE; i++){
            this.virtualMemory.write(PMMU.read(i), i);
            virtualMemory.write(this.virtualMemory.read(i), i);
        }

        return VM;
    }

    public int getPC(){
        return Word.wordToInt(PMMU.read(PC_ADDRESS));
    }
    public int getSP(){
        return Word.wordToInt(PMMU.read(SP_ADDRESS));
    }
    public int getPID(){
        return Word.wordToInt(PMMU.read(PID_ADDRESS));
    }
    public int getIndex(){
        return index;
    }

    public void savePC(int PC){
        PMMU.write(Word.intToWord(PC), PC_ADDRESS);
    }
    public void saveSP(int SP){
        PMMU.write(Word.intToWord(SP), SP_ADDRESS);
    }
    public void savePID(int PID){
        PMMU.write(Word.intToWord(PID), PID_ADDRESS);
    }
}
