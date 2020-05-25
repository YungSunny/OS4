package main;

public class OutputDevice {

    public static void printByte(byte b) {
        //System.out.print((char)b);
        Main.getGUI().print(Character.toString((char) b));
    }

    public static void printWord(Word w) {
        Main.getGUI().print(Character.toString((char)Word.wordToInt(w)));
    }

    public static void printString(String string) {
        //System.out.println(string);
        Main.getGUI().print(string);
    }
}