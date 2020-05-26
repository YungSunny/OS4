package main.os;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Primitives {

    public static void createProcess(Process process, int processId, List<Resource> resourceList, int priority) {
        process.id = processId;
        process.resources = resourceList;
        process.createdResources = new ArrayList<Resource>();
        process.status = Process.READY;
        Planner.ready.add(process);
        process.father = Planner.currentProcess.id;
        process.children = new ArrayList<Integer>();
        process.priority = priority;

        ProcessDescriptor.processes.add(process);
    }

    public static void createResource(int id, String name, boolean reusable) {
        System.out.println("create resource " + name);

        int index = getResourceIndex(name);
        Resource resource = ResourceDescriptor.resources.get(index);

        resource.active = true;
        resource.reusable = reusable;

    }

    public static void requestResource(String name) {
        int index = getResourceIndex(name);
        Resource resource = ResourceDescriptor.resources.get(index);
        resource.waitingProcesses.add(Planner.currentProcess);
        List<Process> servedProcesses = ResourceDivider.run(resource);
        if(!resource.active) {
            Planner.currentProcess.status = Process.BLOCK;
            Planner.ready.remove(Planner.currentProcess);
            Planner.blocked.add(Planner.currentProcess);
            Planner.currentProcess = null;
            Planner.run();
            return;
        }
        if(!resource.reusable)
            resource.active = false;
        boolean found = true;
        for (Process process : servedProcesses) {
            if (process != Planner.currentProcess) {
                if (Planner.blocked.contains(process)) {
                    Planner.blocked.remove(process);
                }
                Planner.ready.add(process);
                if (process.status == Process.BLOCK) {
                    process.status = Process.READY;
                }
                else {
                    process.status = Process.READYS;
                }
            }
            else {
                found = false;
            }
        }
        if (!found) {
            Planner.currentProcess.status = Process.BLOCK;
            Planner.ready.remove(Planner.currentProcess);
            Planner.blocked.add(Planner.currentProcess);
            Planner.currentProcess = null;
        }
        Planner.run();
    }
    public static void freeResource(String name)
    {
        freeResource(name, false);
    }

    public static void freeResource(String name, boolean planner) {
        int index = getResourceIndex(name);
        Resource resource = ResourceDescriptor.resources.get(index);
        resource.active = false;
        List<Process> servedProcesses = ResourceDivider.run(resource);
        for (Process process : servedProcesses) {
            if (process.status == Process.BLOCK) {
                process.status = Process.READY;
                Planner.ready.add(process);
                Planner.blocked.remove(process);
            }
            else {
                process.status = Process.READYS;
            }
        }

        if (planner) {
            Planner.run();
        }
    }

    private static int getResourceIndex(String name) {
        for (int i = 0; i < ResourceDescriptor.resources.size(); i++) {
            if (ResourceDescriptor.resources.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
