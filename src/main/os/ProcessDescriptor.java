package main.os;

import java.util.ArrayList;
import java.util.List;


public class ProcessDescriptor {
    public static int id = 0;
    public static List<Process> processes = new ArrayList<Process>();



    public static final String INTERRUPT = "Interrupt";
    public static final String JCL = "JCL";
    public static final String JOB_GOVERNOR = "JobGovernor";
    public static final String LOADER = "Loader";
    public static final String MAIN_PROC = "MainProc";
    public static final String PRINT_LINE = "PrintLine";
    public static final String READ_FROM_FLASH = "ReadFromFlash";
    public static final String START_STOP = "StartStop";
    public static final String VIRTUAL_MACHINE = "VirtualMachine";
}
