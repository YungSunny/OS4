package main.os;

import java.util.ArrayList;
import java.util.List;

public class Resource {
    public int id;
    public String name;
    public boolean reusable;
    public int father;
    public List<Process> waitingProcesses;
    public String info;
    public boolean active;


    public Resource(int id, String name, boolean reusable, int father) {
        this.id = id;
        this.name = name;
        this.reusable = reusable;
        this.father = father;
        this.waitingProcesses = new ArrayList<Process>();
    }
}
