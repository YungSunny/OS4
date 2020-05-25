package main.os.processes;

import main.os.*;


public class Loader extends main.os.Process {

    public Loader(){
        super.name = "Loader";
    }

    public void run() {
        if(step == 0) {
            step++;
            Primitives.requestResource(ResourceDescriptor.PAKRAUK_PROGRAMA);
            return;
        }
        else if(step == 1) {
            step++;
            Primitives.requestResource(ResourceDescriptor.VARTOTOJO_ATMINTIS);
            return;
        }
        else if(step == 2) {
            step++;
            Primitives.requestResource(ResourceDescriptor.UZDUOTIES_DUOMENYS_SUPERVIZORINEJE_ATMINTYJE);
            return;
        }
        else if(step == 3) {
            step++;
            Primitives.createResource(ResourceDescriptor.id, ResourceDescriptor.PAKRAUTA, false);
            Primitives.freeResource(ResourceDescriptor.PAKRAUTA);
            step = 1;
            Primitives.requestResource(ResourceDescriptor.PAKRAUK_PROGRAMA);
            return;
        }

    }
}
