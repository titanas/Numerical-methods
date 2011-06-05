package skaitiniaimetodai;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

public class Uzd3_2 {

    public static void main(String[] args) {

        //Duomenų nuskaitymas
        double[][] T = MatrixReader.readFile("C:\\SkaitiniaiMetodai\\data files\\uzd2T.txt", 4, 4);
        double[][] C = MatrixReader.readFile("C:\\SkaitiniaiMetodai\\data files\\uzd2C.txt", 4, 4);
        double[][] A = sumMatrix(T, multiplyByK(C, 44));
        System.out.println("*************** Duomenų matrica ***************");
        printMatrix(A);

        // Tikrinės reikšmės radimas
        System.out.println("*************** Tikrinių reikšmių radimas ***************");
        Solver tsolver = new Solver(A, null);
        System.out.println("LAMBDA paieška intervale.");
        System.out.println("Įveskite intervalo pradžią: ");
        int from = readInt();
        System.out.println("Įveskite intervalo pabaigą: ");
        int to = readInt();
        int[] evCount = tsolver.findEigenvalues(from, to);
        for (int i = 0; i < evCount.length; i++) {
            System.out.printf("[%d %d) intervale: %d TR\n", i+from, i+from+1, evCount[i]);
        }


        // Tikrinio vektoriaus radimas
        System.out.println("*************** Tikrinio vektoriaus radimas ***************");
        System.out.println("Įveskite lambda: ");
        double flambda = readDouble();
        System.out.println("Įveskite tikslumą (e): ");
        double e = readDouble();

        
        ArrayOfVectors allX = new ArrayOfVectors(A.length);
        Vector<Double> lambda = new Vector<Double>();
        Vector<Double> x = new Vector<Double>();

        // pradiniai X duomenys
        for (int i = 0; i < allX.array.length; i++) allX.array[i].add(1.0);
        lambda.add(flambda);
        x = prepareX(allX);

        double[][] temp = A.clone();
        
        MatrixInvert mi = new MatrixInvert();
        int k = 0;
        do {
            // Atimam iš pagrindinės istrižainės lambda
            for (int i = 0; i < temp.length; i++) {
                temp[i][i] = A[i][i] - lambda.lastElement();
            }

            // Apskaiciuojam y
            double[][] invertedA = mi.invert(temp);
            Vector<Double> y = multiplyByVector(invertedA, x);
            
            // Apskaičiuojam x. Naujas TV artinys.
            x = updateX(y);
            allX = addVector(allX, x);

            // Tikrinam ar ||Xm - Xm_1|| =~ 2
            allX = correctX(allX, 0.4);

            // Apskaiciuojam lambda artini.
            Vector<Double> Ax = multiplyByVector(A, x);
            double Axx = multiplyVectorByVector(Ax, x);
                    //Axx = Axx / multiplyVectorByVector(x, x);     // Bendruoju atveju
            lambda.add(Axx);

            k++;
        } while  (iterateEpsilon(allX, lambda, e) && (k <= 1000));      //(iterateEpsilon(allX, lambda, e) &&

        printResult(allX, lambda);

        // Tikrinam ar Ax=lambdaX
        System.out.println("*************** Ax = lambdax ***************");
        System.out.println("Ax sandauga;    lambdax sandauga;");
        Vector<Double> ax = multiplyByVector(A, x);
        Vector<Double> lambdax = multiplyBylambda(x, lambda.lastElement());
        for (int i = 0; i < ax.size(); i++) {
            System.out.println(ax.get(i) + " = " + lambdax.get(i));
        }
    }

    static ArrayOfVectors correctX(ArrayOfVectors allX, double diff) {
        Vector<Double> XminusX = new Vector<Double>();
        int last = allX.array[0].size() - 1;
        for (int i = 0; i < allX.array.length; i++) {
            XminusX.add(allX.array[i].get(last) - allX.array[i].get(last-1));
        }
        double rez = norm(XminusX);

        Double cDiff = Math.abs(2.0 - rez);

        if (cDiff.compareTo(diff) < 0) {
            System.out.println("Koreguojam");
            for (int i = 0; i < allX.array.length; i++) {
                double temp = allX.array[i].lastElement();
                allX.array[i].remove(allX.array[i].size()-1);
                allX.array[i].add(-1 * temp);
            }
        }
        return allX;
    }


    static boolean iterateEpsilon (ArrayOfVectors a, Vector<Double> lambda, Double e) {
        Double normalize = 0.0;
        Vector<Double> XminusX = new Vector<Double>();
        for (int i = 0; i < a.array.length; i++) {
            int num = a.array[i].size() - 1;
            XminusX.add(a.array[i].get(num) - a.array[i].get(num-1));
        }
        normalize = norm(XminusX);
        int last = lambda.size()-1;
        Double diff = Math.abs(lambda.get(last) - lambda.get(last-1));
        
        if ((normalize.compareTo(e) > 0) || (diff.compareTo(e) > 0)) return true;
                else return false;
    }

    static ArrayOfVectors addVector(ArrayOfVectors a, Vector<Double> x) {
        for (int i = 0; i < a.array.length; i++) {
            a.array[i].add(x.get(i));
        }
        return a;
    }

    static Vector<Double> prepareX(ArrayOfVectors a) {
        Vector<Double> x = new Vector<Double>();
        for (int i = 0; i < a.array.length; i++) {
            x.add(a.array[i].lastElement());
        }
        return x;
    }

    static Vector<Double> updateX(Vector<Double> x) {
        Vector<Double> ret = new Vector<Double>();
        //Double normalizeX = maxModulus(x);
        Double normalizeX = norm(x);
        for (int i = 0; i < x.size(); i++) {
            ret.add(x.get(i) / normalizeX);
        }
        return ret;
    }

    public static double maxModulus(Vector<Double> x) {
        double max = 0.0;
        for (int i = 0; i < x.size(); i++) {
            Double temp = Math.abs(x.get(i));
            if (temp.compareTo(max) > 0)
                max = temp;
        }
        return max;
    }

    public static double norm (Vector<Double> x) {
        double normalizeX = 0.0;
        for (int i = 0; i < x.size(); i++) {
            normalizeX += x.get(i) * x.get(i);
        }
        normalizeX = Math.sqrt(normalizeX);
        return normalizeX;
    }

    public static double multiplyVectorByVector(Vector<Double> a, Vector<Double> b) {
        double ats = 0.0;
        for (int i = 0; i < a.size(); i++) {
            ats += a.get(i) * b.get(i);
        }
        return ats;
    }

    public static Vector<Double> multiplyByVector(double[][] A, Vector<Double> v) {
        Vector<Double> ats = new Vector<Double>();
        for (int row = 0; row < A.length; row++) {
            double sum = 0.0;
            for (int col = 0; col < A[0].length; col++) {
                sum += A[row][col] * v.get(col);
            }
            ats.add(sum);
        }
        return ats;
    }

    public static Vector<Double> multiplyBylambda(Vector<Double> x, Double lambda) {
        Vector<Double> ats = new Vector<Double>();
        for (int i = 0; i < x.size(); i++) {
            ats.add(x.get(i) * lambda);
        }
        return ats;
    }

    public static double[][] multiplyByK(double[][] A, int k) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A[i][j] = A[i][j] * k;
            }
        }
        return A;
    }

    public static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
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

    public static void printVector(Vector<Double> v) {
        for (int j = 0; j < v.size(); j++) {
            System.out.print(v.get(j) + " ");
        }
        System.out.println();
    }

    public static Double readDouble() {
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
        return num;
    }

    public static int readInt() {
        int num = 0;
        Scanner in = new Scanner(System.in);
        try {
            num = in.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Įvedėte ne skaičių.");
            System.exit(0);
        } catch(NoSuchElementException e) {
            System.out.println("Įvedėte ne skaičių.");
            System.exit(0);
        }
        return num;
    }


    public static void printResult(ArrayOfVectors a, Vector<Double> v) {
        //System.out.println("vectorLen: "+this.array[0].size());
        int countIters = a.array[0].size();
        int countVectors = a.array.length;

        System.out.print("iter. ");
        for (int i = 0; i < countVectors; i++) {
            System.out.print("X" + (i+1) + "    ");
        }
        System.out.print("lambda    ");
        System.out.println();

        for (int j = 0; j < countIters; j++) {
            System.out.print((j) + ".   ");
                for (int i = 0; i < countVectors; i++) {
                System.out.print(a.array[i].get(j) + " ");
            }
            if ((v.size()-1) >= j) System.out.print(v.get(j) + " ");
            System.out.println();
        }
    }
}

