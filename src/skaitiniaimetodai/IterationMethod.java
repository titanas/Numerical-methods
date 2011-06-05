package skaitiniaimetodai;

import java.util.Vector;

public class IterationMethod {

    public int iter = 0;
    public Double e;
    public Double q;
    public Double Q;
    public Vector<Double> xi = new Vector<Double>();
    public Vector<Double> accur = new Vector<Double>();

    public IterationMethod() {
    }
/**
    private double functionFx (double x) {
        double Fx;
        Fx = 2 * Math.exp(-3 * x) + 1;
        return Fx;
    }
**/
    //Juliaus
    private double functionFx (double x) {
        double Fx;
        Fx = Math.exp(-3 * x) + Math.sin(x);
        return Fx;
    }
/**
    private double functionDerFx (double x) {
        double derFx;
        derFx = -6 * Math.exp(-3 * x);
        return derFx;
    }
**/
    //Juliaus
    private double functionDerFx (double x) {
        double derFx;
        derFx = Math.cos(x) - 3 * Math.exp(-3*x);
        return derFx;
    }

   public Double calculateQ (Double from, Double to){
        Double q1 = Math.abs(functionDerFx(from));
        Double q2 = Math.abs(functionDerFx(to));
        System.out.println(q1 + " " + q2);
        if (q1 >= q2) {
            this.q = q1;
            return (1 - q1) / q1;
        } 
        else {
            this.q = q2;
            return (1 - q2) / q2;
        }
    }
    
    public void solve (Double e, Double from, Double to) {
        this.e = e;
        this.Q = calculateQ(from, to);
        this.xi.add(to);
        this.accur.add(99.0);
        while (this.accur.lastElement() > (this.Q * this.e)) {
            //System.out.println(this.accur.lastElement() + " > " + (this.Q * this.e));
            this.iter++;
            this.xi.add(functionFx(this.xi.get(iter-1)));      //functionGx
            this.accur.add(Math.abs(this.xi.get(iter) - this.xi.get(iter-1)));
            //System.out.println("q * e: " + this.accur.get(iter));
        }
        this.accur.setElementAt(0.0, 0);
        this.iter++;
        this.xi.add(functionFx(this.xi.get(iter-1)));      //functionGx
        this.accur.add(Math.abs(this.xi.get(iter) - this.xi.get(iter-1)));
    } 

    public void outputResult() {
        System.out.println("Iteracijų skaičius: " + this.iter);
        for (int i = 0; i < this.iter; i++) {
            System.out.print((i+1) + ". ");
            System.out.printf("    Xi: %.17f", this.xi.get(i));
            System.out.printf("    |Xi - Xi-1|: %.17f", this.accur.get(i));
            System.out.println();
        }
    }
}
