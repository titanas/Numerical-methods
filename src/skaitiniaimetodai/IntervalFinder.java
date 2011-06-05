package skaitiniaimetodai;

import java.util.Vector;

public class IntervalFinder {

    public int N;
    public Double from;
    public Double to;
    public Vector<Double> xi = new Vector<Double>();
    public Vector<Double> yi = new Vector<Double>();

    public IntervalFinder(int N, Double from, Double to) {
        this.N = N;
        this.from = from;
        this.to = to;
    }

    public void makeXIntervals () {
        Double diff = (this.to - this.from) / this.N;
        if (diff != 0) {
            for (int i = 0; i <= this.N; i++) {
                this.xi.add(from + diff * i);
            }
        }
    }

    public void calculateYi () {
        for (int i = 0; i < this.xi.size(); i++) {
            this.yi.add(functionFx(this.xi.get(i)));
        }
    }

    private double functionFx (double x) {
        double Fx;
        Fx = 2 * Math.exp(-3 * x) + 1 - x;
        return Fx;
    }

    public Interval getFirstInterval () {
        makeXIntervals();
        calculateYi();
        for (int i = 1; i < this.N; i++) {
            if ((this.yi.get(i-1) < 0) && (this.yi.get(i) > 0))
                return (new Interval(this.xi.get(i-1), this.xi.get(i)));
            if ((this.yi.get(i-1) > 0) && (this.yi.get(i) < 0))
                return (new Interval(this.xi.get(i-1), this.xi.get(i)));
        }
        return null;
    }

    public void printVector (Vector x) {
        for (int i = 0; i < x.size(); i++) {
            System.out.print(x.get(i) + " ");
        }
        System.out.println();
    }
}
