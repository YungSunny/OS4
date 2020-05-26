package main.os;

import java.util.ArrayList;
import java.util.List;

public class Process {
    public static final int RUN = 0;
    public static final int READY = 1;
    public static final int BLOCK = 2;
    public static final int READYS = 3;
    public static final int BLOCKS = 4;

    public static final String[] statuses = {"RUN", "READY", "BLOCK", "READYS", "BLOCKS"};

    public int id;
    public String name;
    public int step;

    public List<Resource> resources;
    public List<Resource> createdResources;
    public int status;

    public int father;
    public List<Integer> children = new ArrayList<Integer>();
    public int priority;

    public void run() {
        System.out.println("---> RUN " + id);
    }
}
