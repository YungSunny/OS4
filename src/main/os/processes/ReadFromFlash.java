package main.os.processes;

import main.*;
import main.os.Planner;
import main.os.Primitives;
import main.os.ResourceDescriptor;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFromFlash extends main.os.Process {
    public ReadFromFlash(){
        super.name = "ReadFromFlash";
    }
    public void run() {
        if(step == 0) {
            step++;
            Primitives.requestResource(ResourceDescriptor.FLASH_ATMINTINE);
        }
        else if(step == 1) {
            step++;

            try {
                InputDevice.openFile();
            } catch (FileNotFoundException e) {
                System.out.println("ner failo");
            }

            Primitives.requestResource(ResourceDescriptor.SUPERVIZORINE_ATMINTIS);
            return;
        }
        else if(step == 2) {
            step++;


            main.CPU.setMODE(main.CPU.SUPERVISOR);
            Word[] words;
            String line;
            int counter = 0;
            try {

                words = InputDevice.getInput();

                while (words != null) {
                    for (Word w : words) {
                        for (int i = 0; i < 4; i++) {
                            byte b = w.getByte(i);
                            if (b != 0x0) {
                                RealMachine.supervisorMemory.write(w, counter++);
                            }
                        }
                    }
                    words = InputDevice.getInput();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Primitives.createResource(ResourceDescriptor.id, ResourceDescriptor.UZDUOTIES_DUOMENYS_SUPERVIZORINEJE_ATMINTYJE, false);
            Primitives.createResource(ResourceDescriptor.id, ResourceDescriptor.UZDUOTIS_SUPERVIZORINEJE_ATMINTYJE, false);
            step = 2;
            Primitives.requestResource(ResourceDescriptor.FLASH_ATMINTINE);

            return;
        }
    }
}
