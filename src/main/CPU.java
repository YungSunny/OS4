package main;

import main.os.Primitives;
import main.os.ProcessDescriptor;
import main.os.processes.JobGovernor;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CPU {

    public static final Map<String, Integer> cmdList;
    static {
        Map<String, Integer> tempMap = new HashMap<String, Integer>();
        tempMap.put("ADD", 0);
        tempMap.put("SUB", 0);
        tempMap.put("MUL", 0);
        tempMap.put("DIV", 0);
        tempMap.put("WR", 1);
        tempMap.put("RD", 1);
        tempMap.put("CMP", 0);
        tempMap.put("CPID", 0);
        tempMap.put("LD", 2);
        tempMap.put("PT", 2);
        tempMap.put("PUN", 1);
        tempMap.put("PUS", 1);
        tempMap.put("P", 3);
        tempMap.put("JP", 2);
        tempMap.put("JE", 2);
        tempMap.put("JL", 2);
        tempMap.put("JG", 2);
        tempMap.put("FO", 2);
        cmdList = Collections.unmodifiableMap(tempMap);
    }
    private static int x, y, z;
    public static final int SUPERVISOR = 0;
    public static final int USER = 1;
    // Registers
    private static int PTR;
    private static int PC;
    private static int SP;
    private static int SM;
    private static int PID;
    private static int MODE;
    private static int TI;
    private static int PI;

    // Commands
    private static int SI;
    private static int CH1;
    private static int CH2;
    private static int CH3;
    // Additional variables
    private static int supervisor = 0;
    private static int time = 20;


    // Default constructor
    public CPU() {
        setPTR(0);
        setPC(0);
        setSP(0);
        setSM(0);
        setPID(0);
        setTI(time);
        setPI(0);
        setSI(0);
        setCH1(0);
        setCH2(0);
        setCH3(0);
        setMODE(supervisor);
    }

    // Getters
    public static int getPTR() {
        return PTR;
    }

    // Setters
    public static void setPTR(int PTR) {
        CPU.PTR = PTR;
    }

    public static int getPC() {
        return PC;
    }

    public static void setPC(int PC) {
        CPU.PC = PC;
    }

    public static int getSP() {
        return SP;
    }

    public static void setSP(int SP) {
        CPU.SP = SP;
    }

    public void resetInterrupts() {
        resetTI();
        SI = 0;
        PI = 0;
    }

    public void resetTI() {
        setTI(time);
    }

    public int getInterrupt() {
        if (TI == 0) {
            return 1;
        }

        switch (PI) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
        }

        switch (SI) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 8;
            case 4:
                return 9;
            case 5:
                return 10;
            case 6:
                return 11;
        }

        return 0;
    }

    public void cmdADD() {
        PMMU.write(Word.intToWord(Word.wordToInt(PMMU.read(SP)) + Word.wordToInt(PMMU.read(SP - 1))), SP - 1);
        SP--;
        TI--;
    }

    public void cmdSUB() {
        PMMU.write(Word.intToWord(Word.wordToInt(PMMU.read(SP)) - Word.wordToInt(PMMU.read(SP - 1))), SP - 1);
        SP--;
        TI--;
    }

    public void cmdMUL() {
        PMMU.write(Word.intToWord(Word.wordToInt(PMMU.read(SP)) * Word.wordToInt(PMMU.read(SP - 1))), SP - 1);
        SP--;
        TI--;
    }

    public void cmdDIV() {
        PMMU.write(Word.intToWord(Word.wordToInt(PMMU.read(SP)) / Word.wordToInt(PMMU.read(SP - 1))), SP - 1);
        SP--;
        TI--;
    }

    public void cmdWR(int x) {
        PMMU.write(PMMU.read(SP), x);
        TI--;
    }

    public void cmdRD(int x) {
        PMMU.write(PMMU.read(x), SP);
        SP--;
        TI--;
    }

    public void cmdCMP() {
        //System.out.println(Word.wordToInt(PMMU.read(SP)) + ">" + Word.wordToInt(PMMU.read(SP-1)));
        if (Word.wordToInt(PMMU.read(SP)) > Word.wordToInt(PMMU.read(SP - 1))) {
            PMMU.write(Word.intToWord(0), SP);
        } else if (Word.wordToInt(PMMU.read(SP)) == Word.wordToInt(PMMU.read(SP - 1))) {
            PMMU.write(Word.intToWord(1), SP);
        } else {
            PMMU.write(Word.intToWord(2), SP);
        }
        //SP++;
        TI--;
    }

    public void cmdCPID() {
        if (Word.wordToInt(PMMU.read(SP)) != PID) {
            PMMU.write(Word.intToWord(0), SP);
        } else {
            PMMU.write(Word.intToWord(1), SP);
        }
        //SP--;
        TI--;
    }

    public void cmdLD(int x, int y) {
        PMMU.write(PMMU.read(RealMachine.VM_SIZE_IN_BLOCKS * x + y), SP);
        SP++;
        TI--;
    }

    public void cmdPT(int x, int y) {
        if((RealMachine.VM_SIZE_IN_BLOCKS * x + y) > VirtualMachine.DATA_START+VirtualMachine.DATA_SIZE || (RealMachine.VM_SIZE_IN_BLOCKS * x + y) < VirtualMachine.DATA_START){
            CPU.setPI(1);
            return;
        }
        SP++;
        PMMU.write(PMMU.read(SP), RealMachine.VM_SIZE_IN_BLOCKS * x + y);
        TI--;
    }

    public void cmdPUN(int x) {
        SP++;
        main.PMMU.write(Word.intToWord(x), SP);
        TI--;
    }

    public void cmdPUS(Word x) {
        SP++;
        main.PMMU.write(x, SP);
        TI--;
    }

    public void cmdPRTN() {
        TI -= 3;
        SI = 2;
    }

    public void cmdPRTS(){
        TI -= 3;
        SI = 1;
    }

    public void cmdP(int x, int y, int z){
        if(x < 0 || x > 6 || y >= z) {
            CPU.setPI(1);
            return;
        }
        this.x = x;
        this.y = y;
        this.z = z;
        TI -= 3;
        SI = 3;
    }

    public void cmdJP(int x, int y) {
        if((RealMachine.VM_SIZE_IN_BLOCKS * x + y) > VirtualMachine.PROGRAM_START+VirtualMachine.PROGRAM_SIZE || (RealMachine.VM_SIZE_IN_BLOCKS * x + y) < VirtualMachine.PROGRAM_START){
            CPU.setPI(1);
            return;
        }
        setPC(PMMU.WORDS_IN_BLOCK * x + y);
        TI--;
    }

    public void cmdJE(int x, int y) {
        if((RealMachine.VM_SIZE_IN_BLOCKS * x + y) > VirtualMachine.PROGRAM_START+VirtualMachine.PROGRAM_SIZE || (RealMachine.VM_SIZE_IN_BLOCKS * x + y) < VirtualMachine.PROGRAM_START){
            CPU.setPI(1);
            return;
        }
        if (Word.wordToInt(main.PMMU.read(SP)) == 1) {
            setPC(PMMU.WORDS_IN_BLOCK * x + y);
            SP--;
        }
        TI--;
    }

    public void cmdJL(int x, int y) {
        if((RealMachine.VM_SIZE_IN_BLOCKS * x + y) > VirtualMachine.PROGRAM_START+VirtualMachine.PROGRAM_SIZE || (RealMachine.VM_SIZE_IN_BLOCKS * x + y) < VirtualMachine.PROGRAM_START){
            CPU.setPI(1);
            return;
        }
        if (Word.wordToInt(main.PMMU.read(SP)) == 0) {
            setPC(PMMU.WORDS_IN_BLOCK * x + y);
            SP--;
        }
        TI--;
    }

    public void cmdJG(int x, int y) {
        if((RealMachine.VM_SIZE_IN_BLOCKS * x + y) > VirtualMachine.PROGRAM_START+VirtualMachine.PROGRAM_SIZE || (RealMachine.VM_SIZE_IN_BLOCKS * x + y) < VirtualMachine.PROGRAM_START){
            CPU.setPI(1);
            return;
        }
        if (Word.wordToInt(main.PMMU.read(SP)) == 2) {
            setPC(PMMU.WORDS_IN_BLOCK * x + y);
            SP--;
        }
        TI--;
    }

    public void cmdFO(int x, int y) {
        if((RealMachine.VM_SIZE_IN_BLOCKS * x + y) > VirtualMachine.DATA_START+VirtualMachine.DATA_SIZE || (RealMachine.VM_SIZE_IN_BLOCKS * x + y) < VirtualMachine.DATA_START){
            CPU.setPI(1);
            return;
        }
        this.x = x;
        this.y = y;
        TI--;
        SI = 6;
    }

    public void cmdST(int x, int y) {
        if((RealMachine.VM_SIZE_IN_BLOCKS * x + y) > VirtualMachine.DATA_START+VirtualMachine.DATA_SIZE || (RealMachine.VM_SIZE_IN_BLOCKS * x + y) < VirtualMachine.DATA_START){
            CPU.setPI(1);
            return;
        }
        int PID = Word.wordToInt(PMMU.read(PMMU.WORDS_IN_BLOCK * x + y));
        if (PID != 0) {
            PMMU.write(Word.intToWord(0), PMMU.WORDS_IN_BLOCK * x + y);
            RealMachine.killVirtualMachine(PID);
        }
        TI--;
    }

    public static void cmdSTOPF() {

        SI = 5;

    }

    public static void cmdREAD() {

        TI -= 3;
        SI = 4;
    }

    public static boolean test() {
        //System.out.print("Interrupt: ");
        if (TI <= 0) {
            //System.out.println("TI");
            //int index = RealMachine.getNextVirtualMachineIndex();
            RealMachine.unloadVirtualMachine();
            //RealMachine.loadVirtualMachine(index);

            TI = time;
        }

        if (PI != 0) {
            //System.out.println("PI");
            cmdSTOPF();
            return false;
        }

        if (SI != 0) {
            //System.out.println("SI");
            setMODE(SUPERVISOR);
            Main.getGUI().redraw();
            try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
            }
            if(SI == 1) {
                CPU.setMODE(CPU.USER);
                setCH2(1);
                OutputDevice.printString("" + Word.wordToInt(main.PMMU.read(SP)));
                setCH2(0);
            }
            else if(SI == 2){
                CPU.setMODE(CPU.USER);
                setCH2(1);
                OutputDevice.printWord(main.PMMU.read(SP));
                setCH2(0);
            }
            else if(SI == 3){
                setMODE(USER);
                for (int i = y; i < z; i++){

                    setCH2(1);
                    OutputDevice.printWord(main.PMMU.read(16 * x + i));
                    setCH2(0);
                }
            }
            else if(SI == 4){
                setCH1(1);
                try {
                    InputDevice.openFile();
                } catch (FileNotFoundException e) {
                    CPU.setPI(1);
                    CPU.test();
                    return false;
                }
                setCH1(0);
                setMODE(USER);
                Word[] words;
                String line;
                int counter = 0;
                try {
                    line = Word.wordsToString(InputDevice.getInput());

                    if (line.equals("DATA")) {
                        setCH1(1);
                        words = InputDevice.getInput();
                        setCH1(0);
                        line = Word.wordsToString(words);
                        while (!line.equals("CODE")) {
                            for (Word w : words) {
                                for (int i = 0; i < 4; i++) {
                                    byte b = w.getByte(i);
                                    if (b != 0x0) {
                                        PMMU.write(Word.intToWord(b), VirtualMachine.DATA_START + counter++);
                                    }
                                }
                            }
                            setCH1(1);
                            words = InputDevice.getInput();
                            setCH1(0);
                            line = Word.wordsToString(words);
                        }

                        counter = 0;
                        setCH1(1);
                        words = InputDevice.getInput();
                        setCH1(0);
                        line = Word.wordsToString(words);
                        while (!line.equals("STOP")) {
                            for (Word w : words) {
                                for (int i = 0; i < 4; i++) {
                                    if (w.getByte(i) != 0x0)
                                        PMMU.write(Word.intToWord(w.getByte(i)), VirtualMachine.PROGRAM_START + counter++);
                                }
                            }
                            setCH1(1);
                            words = InputDevice.getInput();
                            setCH1(0);
                            line = Word.wordsToString(words);
                        }
                    }
                } catch (Exception e) {
                    //System.out.println("Reading finished.");
                }

            }
            else if(SI == 5){
                Main.getGUI().redraw();
                Main.getGUI().showError(RealMachine.processInterupt());
            }
            else if(SI == 6) {
                //System.out.println("FORK");
                // clone
                CPU.setMODE(CPU.USER);
                RealMachine.getCurrentVirtualMachine().savePC(CPU.getPC());
                RealMachine.getCurrentVirtualMachine().saveSP(CPU.getSP());

                VirtualMachine VM = RealMachine.getCurrentVirtualMachine().clone();
                PMMU.write(Word.intToWord(RealMachine.getCPU().getPID()), PMMU.WORDS_IN_BLOCK * x + y);
                RealMachine.unloadVirtualMachine();
                RealMachine.loadVirtualMachine(VM);
                for (int i = 0; i < VirtualMachine.MEMORY_SIZE; i++) {
                    PMMU.write(VM.getVirtualMemory().read(i), i);
                }
                CPU.setSP(VM.getSP());
                CPU.setPC(VM.getPC());

                JobGovernor processes = new JobGovernor();
                processes.step = 22;
                Primitives.createProcess(processes, ++ProcessDescriptor.id, null, 0);
                //Primitives.stopProcess(processes);

            }
            setMODE(USER);
            SI = 0;
        }
        return true;
    }

    public int getSM() {
        return SM;
    }

    public static void setSM(int SM) {
        CPU.SM = SM;
    }

    public int getPID() {
        return PID;
    }

    public static void setPID(int PID) {
        CPU.PID = PID;
    }

    public int getMODE() {
        return MODE;
    }

    public static void setMODE(int MODE) {
        CPU.MODE = MODE;
    }

    public int getTI() {
        return TI;
    }

    public static void setTI(int TI) {
        CPU.TI = TI;
    }

    public int getPI() {
        return PI;
    }

    public static void setPI(int PI) {
        CPU.PI = PI;
    }

    public int getSI() {
        return SI;
    }

    public static void setSI(int SI) {
        CPU.SI = SI;
    }

    public int getCH1() {
        return CH1;
    }

    public static void setCH1(int CH1) {
        CPU.CH1 = CH1;
    }

    public int getCH2() {
        return CH2;
    }

    public static void setCH2(int CH2) {
        CPU.CH2 = CH2;
    }

    public int getCH3() {
        return CH3;
    }

    public static void setCH3(int CH3) {
        CPU.CH3 = CH3;
    }
}
