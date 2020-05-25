package main.os.processes;

import main.os.Primitives;
import main.os.ResourceDescriptor;


public class PrintLine extends main.os.Process {
    public PrintLine() {
        super.name = "PrintLine";
    }
    public void run(){
        if(step == 0) {
            step++;
            Primitives.requestResource(ResourceDescriptor.EILUTE_ATMINTYJE);
        }
        else if(step == 1) {
            System.out.println("ISVEDIMAS");
            Primitives.requestResource(ResourceDescriptor.EILUTE_ATMINTYJE);
        }
    }
}
