package main.os.processes;

import main.os.Primitives;
import main.os.ResourceDescriptor;

public class JCL extends main.os.Process {

    public JCL(){
        super.name = "JCL";
    }

    public void run() {
        if(step == 0) {
            step++;
            Primitives.requestResource(ResourceDescriptor.SUPERVIZORINE_ATMINTIS);
            return;
        }
        else if(step == 1) {
            step++;
            Primitives.requestResource(ResourceDescriptor.UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE);
            return;
        }
        else if(step == 2) {
            Primitives.createResource(ResourceDescriptor.id, ResourceDescriptor.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE, false);
            Primitives.requestResource(ResourceDescriptor.UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE);
            return;
        }
    }

}
