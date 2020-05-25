package main.os.processes;

import main.os.*;

public class MainProc extends main.os.Process {

    public MainProc(){
        super.name = "MainProc";
    }

    public void run() {
        if(step == 0) {
            step++;
            Primitives.requestResource(ResourceDescriptor.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE);
            return;
        }
        else if(step == 1) {
            main.os.Planner.currentProcess = this;
            int processId = ++ProcessDescriptor.id;
            Primitives.createProcess(new JobGovernor(), processId, null, 0);
            Primitives.requestResource(ResourceDescriptor.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE);

            return;
        }
    }

}
