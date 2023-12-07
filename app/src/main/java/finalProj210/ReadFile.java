package finalProj210;

import java.io.*;
import java.util.Scanner;

/** Class containing a file reading demo */
public class ReadFile {
    /** The main method reads from a file and prints the contents. */
    public static Scanner read(String fname) {
        // Figuring out input source
        Scanner file = null;
        String filename = fname;
        try {
            file = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate file.");
            System.exit(-1);
        }
        return file;
    }

}