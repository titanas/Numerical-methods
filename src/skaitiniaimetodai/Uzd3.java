package skaitiniaimetodai;
// Iteraciniai metodai: 2. Zeidelio metodas; 5. Jungtinių gradientų metodas.

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Uzd3 {

    public static void main(String[] args) {
        
        System.out.println("Duomenų matrica");
        double[][] D = MatrixReader.readFile("C:\\SkaitiniaiMetodai\\data files\\D.txt", 4, 4);
        double[][] C = MatrixReader.readFile("C:\\SkaitiniaiMetodai\\data files\\C.txt", 4, 4);
        double[][] A = sumMatrix(D, multiplyByK(C, 20));
        printMatrix(A);

        System.out.println("Rezultatų vektorius");
        double[] B = MatrixReader.readVector("C:\\SkaitiniaiMetodai\\data files\\B.txt", 4);
        printVector(B);

        // ar matrica simetrine, ar teigiamai aprezta?
        switch (checkMatrix(A)) {
            case -1: {
                System.out.println("Duomenų matrica nėra simetrinė.");
                System.exit(0);
            }
            case -2: {
                System.out.println("Duomenų matrica nėra teigiamai aprėžta.");
                System.exit(0);
            }
            case 0: {
                System.out.println("Duomenų matrica teigiamai aprėžta ir simetrinė.");
                break;
            }
        }


        System.out.println("Įveskite tikslumo kriterijų e: ");
        Double e = readInt();

      
        System.out.println("****************** Zeidelio metodas ******************");  
        SeidelMethod seidel = new SeidelMethod(A, B);
        if (seidel.check2Rule(A)) System.out.println("Tenkinama istrizaines vyravimo salyga (eilutese ir stulpeliuose).");
        seidel.calculate(e);
        System.out.println("Rezultatai");
        seidel.printXWZ();

        System.out.println();
        System.out.println("****************** Jungtinių gradientų metodas ******************");
        ConjugateGradientsMethod cgm = new ConjugateGradientsMethod(A, B);
        cgm.calculate(e);
        cgm.printXWZ();
       

        System.out.println();
        System.out.println("Lygtys su gautomis x reikšmėmis");
        printMatrixWXWB(A, cgm.x, B);
        

    }

    public static void printMatrixWXWB(double[][] A, ArrayOfVectors x, double[] B) {
        Double temp = 0.0;
        for (int row = 0; row < B.length; row++) {
            for (int col = 0; col < B.length; col++) {
                if (A[row][col] < 0) System.out.print("+ (" + A[row][col] + ")");      
                else if (col != 0) System.out.print("+ " + A[row][col]);
                else System.out.print(A[row][col]);
                System.out.print(" * " + x.array[col].lastElement() + " ");
                temp += A[row][col] * x.array[col].lastElement();
            }
            System.out.print("= " + B[row]);
            System.out.print(" (ats: " + temp + ")");
            temp = 0.0;
            System.out.println();
        }
    }

    public static int checkMatrix(double[][] a) {
        for (int row = 0; row < a.length; row++) {
            double sum = 0.0;
            for(int col = 0; col < a[0].length; col++) {
                if (a[row][col] != a[col][row]) return -1;
                if (row != col) sum += Math.abs(a[row][col]);
            }
            if (sum > a[row][row]) return -2;
        }
        return 0;
    }

    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printVector(double[] v) {
        for (int j = 0; j < v.length; j++) {
            System.out.print(v[j] + " ");
        }
        System.out.println();
    }

    public static double[][] sumMatrix(double [][] A, double [][] B) {
        double [][] matrix = new double [A.length][B[0].length];
        
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                matrix[i][j] = A[i][j] + B[i][j];
            }
        }
        return matrix;
    }

    public static double[][] multiplyMatrix(double [][] A, double [][] B) {
        double[][] res = new double[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < B.length; k++) {
                    res[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return res;
    }

    public static double[][] multiplyByK(double[][] A, int k) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A[i][j] *=k;
            }
        }
        return A;
    }

    public static Double readInt() {
        Double num = 0.0;
        Scanner in = new Scanner(System.in);
        try {
            num = in.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Įvedėte ne skaičių.");
            System.exit(0);
        } catch(NoSuchElementException e) {
            System.out.println("Įvedėte ne skaičių.");
            System.exit(0);
        }
        System.out.println();
        return num;
    }
}
