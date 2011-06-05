package skaitiniaimetodai;

import java.util.Vector;

public class ArrayOfVectors {

    public Vector<Double>[] array;

    public ArrayOfVectors (int n) {
        this.array = new Vector[n];
        for (int i = 0; i < n; i++) {
            this.array[i] = new Vector<Double>();
        }
    }

    public void printX() {
        //System.out.println("vectorLen: "+this.array[0].size());
        int countIters = this.array[0].size();
        int countVectors = this.array.length;
        
        System.out.print("iter. ");
        for (int i = 0; i < countVectors; i++) {
            System.out.print("X" + (i+1) + "    ");
        }
        System.out.println();

        for (int j = 0; j < countIters; j++) {
            System.out.print((j) + ".   ");
                for (int i = 0; i < countVectors; i++) {
                System.out.print(this.array[i].get(j) + " ");
            }
            System.out.println();
        }
    }

    public void printXWAnother(ArrayOfVectors a) {
        //System.out.println("vectorLen: "+this.array[0].size());
        int countIters = this.array[0].size();
        int countVectors = this.array.length;

        System.out.print("iter. ");
        for (int i = 0; i < countVectors; i++) {
            System.out.print("X" + (i+1) + "    ");
        }
        System.out.print("Netektys (z)   ");
        System.out.println();

        for (int j = 1; j < countIters; j++) {
            System.out.print((j) + ".   ");
                for (int i = 0; i < countVectors; i++) {
                System.out.print(this.array[i].get(j) + " ");
            }

            //System.out.println();
            //System.out.print("     ");
            Double sum = 0.0;
            for (int i = 0; i < countVectors; i++) {
                sum += a.array[i].get(j) * a.array[i].get(j);
            }
            System.out.println(Math.sqrt(sum) + " ");
        }
    }
    
}
