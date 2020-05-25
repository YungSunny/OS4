package main;

public class VirtualCPU {

    // Registers
    private int PC;
    private int SP;
    private int PID;


    // Default Constructor
    public VirtualCPU(){
        setPC(PC);
        setSP(SP);
        setPID(PID);
    }


    // Setters
    public void setPC(int PC) {
        this.PC = PC;
    }

    public void setSP(int SP) {
        this.SP = SP;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }


    // Getters
    public int getPC() {
        return PC;
    }

    public int getSP() {
        return SP;
    }

    public int getPID() {
        return PID;
    }

}
