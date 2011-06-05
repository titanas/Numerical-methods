package skaitiniaimetodai;

import java.io.*;
import java.util.*;

public class Solver {

    private int N;
    private int MAX_LINE_ELEMENTS;
    private File file;
    private Vector <Double> a = new Vector<Double>();
    private Vector <Double> b = new Vector<Double>();
    private Vector <Double> c = new Vector<Double>();
    private Vector <Double> d = new Vector<Double>();
    private Vector <Double> C = new Vector<Double>();
    private Vector <Double> D = new Vector<Double>();
    private Vector <Double> x = new Vector<Double>();

    public Solver (int n, Vector <Double> a, Vector <Double> b,
            Vector <Double> c, Vector <Double> d) {
        this.N = n;
        this.MAX_LINE_ELEMENTS = this.N + 1;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Solver (int n, File file) {
        this.N = n;
        this.MAX_LINE_ELEMENTS = this.N + 1;
        this.file = file;     
    }

    public Solver(double[][] a, Vector<Double> d) {
        this.N = a.length;
        this.MAX_LINE_ELEMENTS = this.N + 1;

        for (int i = 0; i < this.N; i++) {
            if (i == 0) addFirstLine(a[i]);
            else if (i == (this.N-1)) addLastLine(a[i]);
                 else addLine(a[i], i);
        }
        this.d = d;
    }

    public void readFile() {
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            int lineNumber = 0;
            System.out.println("Skaitomi duomenys...");
            while (((line = br.readLine())  != null) && (lineNumber < this.N))   {
                System.out.println (line);
                double[] doubleLine = parseLine(line);
                checkLine(doubleLine, lineNumber);
                if (lineNumber == 0) addFirstLine(doubleLine);
                else if(lineNumber == this.N-1) addLastLine(doubleLine);
                     else addLine(doubleLine, lineNumber);
                lineNumber++;
            }
            in.close();
        } catch (Exception e) {
            quit(e.getMessage());
        }
    }

    public double[] parseLine (String line) {
        line = line + " ";
        char[] carray = line.toCharArray();
        String digit = new String();

        double[] rez = new double[MAX_LINE_ELEMENTS];
        int rezIndex = 0;

        for (int i = 0; i < carray.length; i++) {
            if (carray[i] != ' ') {
                digit += carray[i];
            } else {
                if (!digit.isEmpty()) {
                    try {
                        if (rezIndex <= MAX_LINE_ELEMENTS) {
                            rez[rezIndex] = Double.valueOf(digit);
                            rezIndex++;
                            digit = "";
                        } else quit("Eilute yra per daug parametru.");
                          
                    } catch (NumberFormatException e) { 
                        quit("Blogai ivestas skaicius.");
                    }
                }
            }
        }
        return rez;
    }

    public void checkLine(double[] line, int lineNumber) {
        if (line.length != MAX_LINE_ELEMENTS)
            quit("Eiluteje netinkamas skaicius reikiamu parametru.");
       
        if (lineNumber != 0)
            if (line[lineNumber-1] == 0) quit((lineNumber+1) + " eilutes, " + (lineNumber-1) +
                    " parametras lygus nuliui.");

        if (line[lineNumber] == 0) quit((lineNumber+1) + " eilutes, " + (lineNumber) +
                " parametras lygus nuliui.");

        if (lineNumber != N)
            if (line[lineNumber+1] == 0) quit((lineNumber+1) + " eilutes, " + (lineNumber+1) +
                    " parametras lygus nuliui.");
    }

    public boolean checkDiagonalRule() {
        for(int i = 0; i <= this.N-1; i++) {
            if (i == 0) {
                if (!(Math.abs(this.b.get(i)) >= Math.abs(this.c.get(i)))) return false;
            }

            if (i == this.N-1) {
                if (!(Math.abs(this.b.get(i)) >= Math.abs(this.a.get(i-1)))) return false;
            }
            
            if ((i != 0) && (i != this.N-1)) {
                if (!(Math.abs(this.b.get(i)) >= Math.abs(this.a.get(i-1)) + Math.abs(this.c.get(i))))
                    return false;
            }
        }
        return true;
    }

    public boolean checkDiagonalStrictRule() {
        for(int i = 0; i <= this.N-1; i++) {
            if (i == 0) {
                if (Math.abs(this.b.get(i)) > Math.abs(this.c.get(i))) return true;
            }

            if (i == this.N-1) {
                if (Math.abs(this.b.get(i)) > Math.abs(this.a.get(i-1))) return true;
            }

            if ((i != 0) && (i != this.N-1)) {
                if (Math.abs(this.b.get(i)) > Math.abs(this.a.get(i-1)) + Math.abs(this.c.get(i)))
                    return true;
            }
        }
        return false;
    }

    public void addFirstLine(double[] line) {
        this.b.add(line[0]);
        this.c.add(line[1]);
        //this.d.add(line[MAX_LINE_ELEMENTS-1]);
    }

    public void addLine(double[] line, int lineNumber) {
        this.a.add(line[lineNumber-1]);
        this.b.add(line[lineNumber]);
        this.c.add(line[lineNumber+1]);
        //this.d.add(line[MAX_LINE_ELEMENTS-1]);
    }

    public void addLastLine(double[] line) {
        this.a.add(line[MAX_LINE_ELEMENTS-3]);
        this.b.add(line[MAX_LINE_ELEMENTS-2]);
        //this.d.add(line[MAX_LINE_ELEMENTS-1]);
    }

    public void printVector (Vector x) {
        for (int i = 0; i < x.size(); i++) {
            System.out.print(x.get(i) + " ");
        }
        System.out.println();
    }

    public void calculateCD() {
        this.C.add(-this.c.get(0) / this.b.get(0));      // apskaiciuojam C1, cia jis C0
        this.D.add(this.d.get(0) / this.b.get(0));       // apskaiciuojam D1, cia jis D0
        // skaiciuojam Ck
        double temp;
        for (int k = 1; k <= N-2; k++) {
            temp = -(this.c.get(k)) / (this.a.get(k-1) * this.C.get(k-1) + this.b.get(k));
            this.C.add(temp);        //idedam reiksme i k-taja pozicija
        }
        // skaiciuojam Dk
        for (int k = 1; k <= N-1; k++) {
            temp = (this.d.get(k) - this.a.get(k-1) * this.D.get(k-1)) /
                    (this.a.get(k-1) * this.C.get(k-1) + this.b.get(k));
            this.D.add(temp);
        }
    }

    public int[] findEigenvalues(int from, int to) {
        int[] eigenvaluesCount = new int[to-from];

        for (int i = 0; i < (to - from); i++) {
            int tt = i + from + 1;
            double[] pn = calculateTypicalPolinom((double) tt);

            for (int j = 0; j < pn.length; j++) 
                if (pn[j] == 0.0) pn[j] = pn[j-1];

            double[] qm = new double[pn.length - 1];
            int count = 0;
            for (int j = 0; j < qm.length; j++) {
                qm[j] = pn[j+1] / pn[j];
                if (qm[j] < 0) count++;
            }
            eigenvaluesCount[i] = count;
            
        }
        for (int j = eigenvaluesCount.length-1; j >= 0; j--)
            if (j != 0) eigenvaluesCount[j] -= eigenvaluesCount[j-1];
        return eigenvaluesCount;
    }

    public double[] calculateTypicalPolinom (double lambda) {
        double[] p = new double[this.b.size() + 1];
        p[0] = 1;
        p[1] = this.b.get(0) - lambda;
        for (int i = 2; i < p.length; i++) {
            p[i] = (this.b.get(i-1) - lambda) * p[i-1] -
                    this.a.get(i-2) * this.c.get(i-2) * p[i-2];
        }
        //System.out.println("p: " + p[2]);
        return p;
    }

    public void calculateX () {
        calculateCD();
        makeVector(x);
        // Xn = Dn
        this.x.set(N-1, this.D.get(N-1));
        // skaiciuojam Xk
        double temp;
        for (int k = N-2; k >= 0; k--) {
            temp = this.C.get(k) * this.x.get(k+1) + this.D.get(k);
            this.x.set(k, temp);
        }
    }

    public void makeVector(Vector x) {
        for (int i = 0; i <= N-1; i++) {
            x.add(Double.NaN);
        }
    }

    public void quit(String str) {
        System.out.println(str);
        System.exit(0);
    }

    public Vector <Double> geta () {
        return this.a;
    }

    public Vector <Double> getb () {
        return this.b;
    }

    public Vector <Double> getc () {
        return this.c;
    }

    public Vector <Double> getd () {
        return this.d;
    }

    public Vector <Double> getC () {
        return this.C;
    }

    public Vector <Double> getD () {
        return this.D;
    }

    public void setX(Vector<Double> x) {
        this.x = x;
    }

    public Vector <Double> getx () {
        return this.x;
    }
}

