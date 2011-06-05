
package skaitiniaimetodai;

import java.io.*;
import java.util.*;
import java.lang.Math;

public class Spline {
    private int N;
    File file;
    private Vector <Double> x = new Vector<Double>();
    private Vector <Double> y = new Vector<Double>();
    private Vector <Double> h = new Vector<Double>();
    private Vector <Double> f = new Vector<Double>();
    private Vector <Double> g = new Vector<Double>();
    private Vector <Double> e = new Vector<Double>();
    private Vector <Double> G = new Vector<Double>();
    private Vector <Double> H = new Vector<Double>();

    public Spline (File file) {
        this.file = file;
        readFile();
        if (checkXYLength()) this.N = this.x.size();
        else quit("Vektoriu X ir Y ilgiai skiriasi.");
    }
    
    public void readFile() {
        try {
            FileInputStream fstream = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            int lineNumber = 0;
            System.out.println("Skaitomi duomenys...");
            int count = 0;
            while (((line = br.readLine()) != null) && (count < 2)) {
                System.out.println (line);
                if (count == 0)
                    this.x = parseLine(line);
                if (count == 1) {
                    this.y = parseLine(line);
                }
                count++;
            }
            in.close();
        } catch (Exception e) {
            quit(e.getMessage());
        }
    }

    public Vector<Double> parseLine (String line) {
        line = line + " ";
        char[] carray = line.toCharArray();
        String digit = new String();

        Vector <Double> rez = new Vector <Double>();
        Double temp;
        for (int i = 0; i < carray.length; i++) {
            if (carray[i] != ' ') {
                digit += carray[i];
            } else {
                if (!digit.isEmpty()) {
                    try {
                        rez.add(temp = Double.valueOf(digit));
                    } catch (NumberFormatException e) {
                        quit(e.getMessage());
                    }
                    digit = "";
                }
            }
        }
        return rez;
    }

    public boolean checkXYLength() {
        if (this.x.size() == this.x.size()) return true;
            else return false;
    }

    public void calculateH() {
        for (int i = 1; i < this.x.size(); i++) {
            h.add(this.x.get(i) - this.x.get(i-1));
        }
    }

    public void calculateF() {
        for (int i = 1; i < this.N; i++) {
            this.f.add((this.y.get(i) - this.y.get(i-1)) /
                    this.h.get(i-1));
        }
    }

    public void calculateG () {
        Vector<Double> a = new Vector<Double>();
        for (int i = 0; i < this.h.size()-1; i++)
            a.add(this.h.get(i));
        a.add(0.0);

        Vector<Double> b = new Vector<Double>();
        b.add(1.0);
        for (int i = 1; i < this.h.size(); i++)
            b.add(2*(this.h.get(i) + this.h.get(i-1)));
        b.add(1.0);

        Vector<Double> c = new Vector<Double>();
        c.add(0.0);
        for(int i = 1; i < this.h.size(); i++)
            c.add(this.h.get(i));

        Vector<Double> d = new Vector<Double>();
        d.add(0.0);
        for (int i = 1; i < this.f.size(); i++)
            d.add(6*((this.f.get(i))-(this.f.get(i-1))));
        d.add(0.0);

        Solver solver = new Solver(this.N, a, b, c, d);
        solver.calculateX();
        setg(solver.getx());    
    }

    public void calculateSplineCoef() {
        for (int i = 0; i < this.N-1; i++) {
            this.e.add((this.y.get(i+1) - this.y.get(i)) / this.h.get(i) -
                    this.g.get(i+1) * this.h.get(i) / 6 -
                    this.g.get(i) * this.h.get(i) / 3);
        }

        for (int i = 0; i < this.N-1; i++) {
            this.G.add(this.g.get(i) / 2);
        }

        for (int i = 0; i < this.N-1; i++) {
            this.H.add((this.g.get(i+1) - this.g.get(i)) /
                    (6 * this.h.get(i)));
        }
    }

    public double calculateXValue (double x) {
        double y;
        int in = findInterval(x);
        
        y = (this.y.get(in) + this.e.get(in)*(x - this.x.get(in)) +
                this.G.get(in)*(x - this.x.get(in))*(x - this.x.get(in)) +
                this.H.get(in)*(x - this.x.get(in))*(x - this.x.get(in))*(x - this.x.get(in)));
        return y;
    }

    public int findInterval (double x) {
        int i;

        for (i = 1; i < this.x.size(); i++) {
            if ((i == 0) && (this.x.get(i) > x))
                return -1;
            if ((i == (this.x.size()-1)) && (this.x.get(this.x.size()-1) < x))
                return -1;

            if ((this.x.get(i).compareTo(x) >= 0) && (this.x.get(i-1).compareTo(x) <= 0))
                return (i-1);            
        }

        return -1;
    }

    public void getFunctionY () {
        for (int i = 0; i < this.x.size(); i++) {
            this.y.add((Math.sin(this.x.get(i)) + 1) / (this.x.get(i) * this.x.get(i) + 1));
        }
    }

   

    public void printVector (Vector x) {
        for (int i = 0; i < x.size(); i++) {
            System.out.print(x.get(i) + " ");
        }
        System.out.println();
    }

    public void quit(String str) {
        System.out.println(str);
        System.exit(0);
    }

    public Vector <Double> getx () {
        return this.x;
    }

    public Vector <Double> gety () {
        return this.y;
    }

    public Vector <Double> geth () {
        return this.h;
    }

    public Vector <Double> getf () {
        return this.f;
    }

    public Vector <Double> getg () {
        return this.g;
    }

    public Vector <Double> gete () {
        return this.e;
    }

    public Vector <Double> getG () {
        return this.G;
    }

    public Vector <Double> getH () {
        return this.H;
    }

    public void setg (Vector<Double> x) {
        this.g = x;
    }
}
