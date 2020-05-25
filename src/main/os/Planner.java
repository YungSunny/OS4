package main.os;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Planner {
    public static List<Process> ready = new ArrayList<Process>();
    public static List<Process> blocked = new ArrayList<Process>();

    public static Process currentProcess;

    public static void run() {
        System.out.println("_________________________________________________");
        System.out.println("Processes:");
        for(Process process : ProcessDescriptor.processes){
            System.out.println(" " + process.name + "  " + Process.statuses[process.status]);
        }
        System.out.println("Resources:");
        for(Resource resource : ResourceDescriptor.resources){
            if(resource.active)
                System.out.println(" " + resource.name);
        }


        if(ready.size() == 0) {
            System.out.println("EXIT");
            System.exit(0);
        }
        /*ready.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return o1.status - o2.status;
            }
        });*/
        currentProcess = ready.get(0);
        ready.get(0).status = Process.RUN;
        System.out.print("Planner run: ");
        System.out.println(ready.get(0).name);
        ready.get(0).run();
    }
}
