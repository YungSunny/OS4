package main;

import javafx.util.converter.CharacterStringConverter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Kompiuteris on 15-03-23.
 */
public class GUI {

    private JPanel panel;
    private JScrollPane memoryPanel;
    private JTextField SP;
    private JTextField PTR;
    private JTextField PID;
    private JButton stepButton;
    private JButton executeButton;
    private JButton memoryButton;
    private JButton pageForwardButton;
    private JButton pageBackButton;
    private JTextField PC;
    private JTextField TI;
    private JTextField PI;
    private JTextField SI;
    private JTextField CH1;
    private JTextField CH2;
    private JTextField CH3;
    private JTextField MODE;
    private JTextField SM;
    private JTextField printer;
    private int memoryMode;
    public final int MEMORY_MODE_VIRTUAL = 0;
    public final int MEMORY_MODE_REAL = 1;
    public final int MEMORY_PAGE_SIZE = 256;
    private int memoryPage;
    private JTextField[] memoryField;

    public GUI() {
        memoryMode = MEMORY_MODE_VIRTUAL;
        memoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(memoryMode == MEMORY_MODE_VIRTUAL)
                    memoryMode = MEMORY_MODE_REAL;
                else {
                    memoryMode = MEMORY_MODE_VIRTUAL;
                    memoryPage = 0;
                }
                redraw();
            }
        });
        pageBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(memoryMode == MEMORY_MODE_REAL){
                    if(memoryPage == 0){
                        memoryPage = 15;
                    }
                    else {
                        memoryPage--;
                    }
                    redraw();
                }
            }
        });
        pageForwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(memoryMode == MEMORY_MODE_REAL){
                    if(memoryPage == 15){
                        memoryPage = 0;
                    }
                    else {
                        memoryPage++;
                    }
                    redraw();
                }

            }
        });
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable(){
                    public void run(){
                        RealMachine.executeProgram(false);
                        redraw();
                    }
                };
                new Thread(runnable).start();

            }
        });
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new Runnable(){
                    public void run(){
                        RealMachine.executeProgram(true);
                        redraw();
                    }
                };
                new Thread(runnable).start();
            }
        });
    }

    public JPanel getPanel(){
        return panel;
    }



    private void createUIComponents() {
        JPanel insidePanel = new JPanel(new SpringLayout());
        memoryPanel = new JScrollPane(insidePanel);
        int rows = VirtualMachine.MEMORY_SIZE+1;
        int cols = 6;
        int i = 0;
        memoryField = new JTextField[(VirtualMachine.MEMORY_SIZE+1)*cols];
        memoryField[i] = new JTextField("Real Address");
        insidePanel.add(memoryField[i]);
        memoryField[i++].setEditable(false);
        memoryField = new JTextField[(VirtualMachine.MEMORY_SIZE+1)*cols];
        memoryField[i] = new JTextField("Virtual Address");
        insidePanel.add(memoryField[i]);
        memoryField[i++].setEditable(false);
        for (int c = 1; c >= 0; c--) {
            memoryField[i] = new JTextField("Byte " + c);
            insidePanel.add(memoryField[i]);
            memoryField[i++].setEditable(false);
        }
        memoryField[i] = new JTextField("As Int");
        insidePanel.add(memoryField[i]);
        memoryField[i++].setEditable(false);
        memoryField[i] = new JTextField("As Char");
        insidePanel.add(memoryField[i]);
        memoryField[i++].setEditable(false);
        for (int r = 0; r < rows-1; r++) {

            if(memoryMode == MEMORY_MODE_REAL) {
                memoryField[i] = new JTextField("" + (r + (memoryPage * MEMORY_PAGE_SIZE)));
                insidePanel.add(memoryField[i]);
                memoryField[i++].setEditable(false);
            }
            else{
                memoryField[i] = new JTextField("" + PMMU.virtualToRealAddress(r));
                insidePanel.add(memoryField[i]);
                memoryField[i++].setEditable(false);
            }

            memoryField[i] = new JTextField("" + (r + (memoryPage*MEMORY_PAGE_SIZE)));
            insidePanel.add(memoryField[i]);
            memoryField[i++].setEditable(false);

            for (int c = 1; c >= 0; c--) {
                if(memoryMode == MEMORY_MODE_REAL) {
                    memoryField[i] = new JTextField(String.format("%02X ", RealMachine.getRealMemory().read(r + memoryPage * MEMORY_PAGE_SIZE).getByte(c)));
                }
                else {
                    memoryField[i] = new JTextField(String.format("%02X ", PMMU.read(r).getByte(c)));
                }
                insidePanel.add(memoryField[i]);
                memoryField[i++].setEditable(false);
            }
            // as int
            if(memoryMode == MEMORY_MODE_REAL) {
                memoryField[i] = new JTextField(Integer.toString(Word.wordToInt(RealMachine.getRealMemory().read(r + memoryPage * MEMORY_PAGE_SIZE))));
            }
            else {
                //System.out.println(PMMU.read(r));
                memoryField[i] = new JTextField(Integer.toString(Word.wordToInt(PMMU.read(r))));
            }
            insidePanel.add(memoryField[i]);
            memoryField[i++].setEditable(false);
            // as char
            if(memoryMode == MEMORY_MODE_REAL) {
                char c = (char) Word.wordToInt(RealMachine.getRealMemory().read(r + memoryPage * MEMORY_PAGE_SIZE));
                memoryField[i] = new JTextField(Character.toString(c));
            }
            else {
                char c = (char) Word.wordToInt(PMMU.read(r));
                memoryField[i] = new JTextField(Character.toString(c));
            }
            insidePanel.add(memoryField[i]);
            memoryField[i++].setEditable(false);
        }
        SpringUtilities.makeCompactGrid(insidePanel, //parent
                rows, cols,
                2, 2,  //initX, initY
                2, 2); //xPad, yPad
    }

    public void showError(String str){
        int rez = JOptionPane.showOptionDialog(panel, str, "Error", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);
        System.exit(0);

    }

    public void print(String str){
        printer.setText(printer.getText() + str);
    }

    public void redraw(){
        SP.setText(String.format("%04X ", RealMachine.getCPU().getSP()));
        PTR.setText(String.format("%04X ", RealMachine.getCPU().getPTR()));
        PID.setText(Integer.toString(RealMachine.getCPU().getPID()));
        PC.setText(String.format("%04X ", RealMachine.getCPU().getPC()));
        TI.setText(Integer.toString(RealMachine.getCPU().getTI()));
        PI.setText(Integer.toString(RealMachine.getCPU().getPI()));
        SI.setText(Integer.toString(RealMachine.getCPU().getSI()));
        CH1.setText(Integer.toString(RealMachine.getCPU().getCH1()));
        CH2.setText(Integer.toString(RealMachine.getCPU().getCH2()));
        CH3.setText(Integer.toString(RealMachine.getCPU().getCH3()));
        if(RealMachine.getCPU().getMODE() == CPU.SUPERVISOR) {
            MODE.setText("Supervisor");
        }
        else
            MODE.setText("User");
        SM.setText(Integer.toString(RealMachine.getCPU().getSM()));

        int rows = VirtualMachine.MEMORY_SIZE+1;
        if(memoryMode == MEMORY_MODE_REAL) {
            memoryField[1].setText("Real Address");
        }else{
            memoryField[1].setText("Virtual Address");
        }
        int cols = 6;
        int i = cols;
        for (int r = 0; r < rows-1; r++) {

            if(memoryMode == MEMORY_MODE_REAL) {
                memoryField[i++].setText("" + String.format("%02X ", (r + (memoryPage * MEMORY_PAGE_SIZE))));
            }else{
                memoryField[i++].setText("" + String.format("%04X ", (PMMU.virtualToRealAddress(r))));
            }

            memoryField[i++].setText("" + String.format("%02X ", (r + (memoryPage * MEMORY_PAGE_SIZE))));

            for (int c = 1; c >= 0; c--) {
                if(memoryMode == MEMORY_MODE_REAL) {
                    memoryField[i++].setText(String.format("%02X ", RealMachine.getRealMemory().read(r + memoryPage * MEMORY_PAGE_SIZE).getByte(c)));
                }
                else {
                    memoryField[i++].setText(String.format("%02X ", PMMU.read(r).getByte(c)));
                }
            }
            // as int
            if(memoryMode == MEMORY_MODE_REAL) {
                memoryField[i++].setText(Integer.toString(Word.wordToInt(RealMachine.getRealMemory().read(r + memoryPage * MEMORY_PAGE_SIZE))));
            }
            else {
                memoryField[i++].setText(Integer.toString(Word.wordToInt(PMMU.read(r))));
            }
            // as char
            if(memoryMode == MEMORY_MODE_REAL) {
                char c = (char) Word.wordToInt(RealMachine.getRealMemory().read(r + memoryPage * MEMORY_PAGE_SIZE));
                memoryField[i++].setText(Character.toString(c));
            }
            else {
                char c = (char) Word.wordToInt(PMMU.read(r));
                memoryField[i++].setText(Character.toString(c));
            }
        }
    }
}
