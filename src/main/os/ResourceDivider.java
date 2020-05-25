package main.os;

import javafx.beans.binding.ListBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ResourceDivider {

    public static List<Process> run(Resource resource) {
        //if(!resource.active)
        //    return null;
        List<Process> servedProcesses = new ArrayList<Process>();
        resource.waitingProcesses.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return o1.status - o2.status;
            }
        });
        for (Process process : resource.waitingProcesses) {
            if (process.status == Process.BLOCK || process.status == Process.BLOCKS) {
                servedProcesses.add(process);
            }
        }
        return servedProcesses;
    }

}
