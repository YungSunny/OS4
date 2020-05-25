package main.os.processes;

import main.CPU;
import main.RealMachine;
import main.os.Primitives;
import main.os.ProcessDescriptor;
import main.os.ResourceDescriptor;

import javax.swing.*;


public class JobGovernor extends main.os.Process {

    public JobGovernor(){
        super.name = "JobGovernor";
    }

    int virtualMachineId;

    public void run() {
        if(step == 0) {
            step++;
            Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.PAKRAUK_PROGRAMA, false);

            int loaderId = ++ProcessDescriptor.id;
            Primitives.createProcess(new Loader(), loaderId, null, 0);

            Primitives.requestResource(ResourceDescriptor.PAKRAUTA);
            return;
        }
        else if(step == 1) {
            step++;
            Primitives.requestResource(ResourceDescriptor.VARTOTOJO_ATMINTIS);
            return;
        }
        else if(step == 2 || step == 22) {
            main.VirtualMachine virtualMachine = RealMachine.createVirtualMachine();

            virtualMachineId = ++ProcessDescriptor.id;
            VirtualMachine virtualMachineProcess = new VirtualMachine(virtualMachine);
            if(step == 22){
                virtualMachineProcess.step = 1;
            }
            Primitives.createProcess(virtualMachineProcess, virtualMachineId, null, 0);
            step = 3;
        }
        if(step == 3) {
            step++;
            Primitives.requestResource(ResourceDescriptor.PERTRAUKIMAS);
            return;
        }
        else if(step == 4) {
            step = 3;

            //Primitives.stopProcess(virtualMachineId);
            CPU.test();

            Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.APDOROTAS_PERTRAUKIMAS, false);
            Primitives.requestResource(ResourceDescriptor.PERTRAUKIMAS);

            return;
        }

        // TODO: interrupto pranesimas
        // FORK
    }


}
