package skaitiniaimetodai;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MatrixReader {

    public static File openFile(String fileName) {
        File file = new File(fileName);
        if (file.canRead()) return file;
        else return null;
    }

    public static double[] readVector (String fileName, int n) {
        double [] vector = new double[n];
        File file = openFile(fileName);
        if (file == null) {
            System.out.println("Failo nuskaitymo klaida");
            System.exit(0);
        }
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            int count = 0;
            while (((line = br.readLine()) != null) && (count < n)) {
               try {
                    vector[count] = Double.valueOf(line);
                    count++;
                } catch (NumberFormatException e) {
                    quit(e.getMessage());
                }
            }
            in.close();
        } catch (Exception e) {
            quit(e.getMessage());
        }
        return vector;
    }

    public static double[][] readFile(String fileName, int rows, int collumns) {
        double [][] matrix = new double[rows][collumns];
        File file = openFile(fileName);
        if (file == null) {
            System.out.println("Failo nuskaitymo klaida");
            System.exit(0);
        }
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            
            int count = 0;
            while (((line = br.readLine()) != null) && (count < rows)) {
                matrix[count] = parseLine(line, collumns);
                count++;
            }
            in.close();
        } catch (Exception e) {
            quit(e.getMessage());
        }
        return matrix;
    }

    public static double[] parseLine (String line, int collumns) {
        line = line + " ";
        char[] carray = line.toCharArray();
        String digit = new String();
        double[] rez = new double[collumns];
        int count = 0;
        for (int i = 0; i < carray.length; i++) {
            if (carray[i] != ' ') {
                digit += carray[i];
            } else {
                if (!digit.isEmpty()) {
                    try {
                        rez[count] = Double.valueOf(digit);
                        count++;
                    } catch (NumberFormatException e) {
                        quit(e.getMessage());
                    }
                    digit = "";
                }
            }
        }
        return rez;
    }

    public static void quit(String str) {
        System.out.println(str);
        System.exit(0);
    }

    public static void printLine(double[] line) {
        for (int i = 0; i < line.length; i++) {
            System.out.print(line[i] + " ");
        }
        System.out.println();
    }
}
