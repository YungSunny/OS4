package main;

import main.os.ResourceDescriptor;
import main.os.processes.StartStop;

import javax.swing.*;

public class Main {

    private static GUI GUI;
    public static void main(String[] args){

        JFrame frame = new JFrame("RM");
        GUI = new GUI();
        frame.setContentPane(GUI.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        GUI.redraw();

        StartStop startStop = new StartStop();
        main.os.Planner.currentProcess = startStop;
        main.os.Resource resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.BENDRA_ATMINTIS, true, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.EILUTE_ATMINTYJE, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.FLASH_ATMINTINE, false, 0);
        resource.active = true;
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.IS_INPUT_OUTPUT, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.IS_INTERUPT, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.KANALU_IRENGINYS, true, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.PAKRAUK_PROGRAMA, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.PAKRAUTA, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.PERTRAUKIMAS, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.PERTRAUKIMO_IVYKIS, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.POS_PABAIGA, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.PROCESORIUS, true, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.PROGRAMA_PARENGTA, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.SUPERVIZORINE_ATMINTIS, true, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.UZDUOTIES_DUOMENYS_SUPERVIZORINEJE_ATMINTYJE, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.UZDUOTIES_PROGRAMA_SUPERVIZORINEJE_ATMINTYJE, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE, false, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.VARTOTOJO_ATMINTIS, true, 0);
        ResourceDescriptor.resources.add(resource);
        resource = new main.os.Resource(ResourceDescriptor.id, ResourceDescriptor.APDOROTAS_PERTRAUKIMAS, false, 0);
        ResourceDescriptor.resources.add(resource);
        //Primitives.createResource(ResourceDescriptor.id, ResourceDescriptor.FLASH_ATMINTINE, false);
        startStop.run();
        //VirtualMachine VM1 = RealMachine.createVirtualMachine();
        //RealMachine.loadVirtualMachine(VM1);



        //CPU.cmdREAD();
        //CPU.test();

        //GUI.redraw();
    }

    public static GUI getGUI(){
        return GUI;
    }

}
