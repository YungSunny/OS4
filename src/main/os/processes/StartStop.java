package main.os.processes;

import main.os.Primitives;
import main.os.ProcessDescriptor;
import main.os.ResourceDescriptor;


public class StartStop extends main.os.Process {
    public StartStop(){
        super.name = "StartStop";
    }
    public void run() {
        if(step == 0) {
            step++;
            Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.SUPERVIZORINE_ATMINTIS, true);
            Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.VARTOTOJO_ATMINTIS, true);
            Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.BENDRA_ATMINTIS, true);
            Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.PROCESORIUS, true);
            Primitives.createResource(++ResourceDescriptor.id, ResourceDescriptor.KANALU_IRENGINYS, true);

            Primitives.createProcess(new ReadFromFlash(), ++ProcessDescriptor.id, null, 0);
            Primitives.createProcess(new JCL(), ++ProcessDescriptor.id, null, 0);
            Primitives.createProcess(new MainProc(), ++ProcessDescriptor.id, null, 0);
            Primitives.createProcess(new Interrupt(), ++ProcessDescriptor.id, null, 0);
            Primitives.createProcess(new PrintLine(), ++ProcessDescriptor.id, null, 0);

            Primitives.requestResource(ResourceDescriptor.POS_PABAIGA);
            return;
        }
        else if(step == 1) {
            step++;
            // Sisteminiu procesu naikinimas
            // Sisteminiu resursu naikinimas
            //Primitives.stopProcess(this);
            return;
        }


    }
}
