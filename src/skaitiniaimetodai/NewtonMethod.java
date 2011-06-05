package skaitiniaimetodai;

import java.util.Vector;

public class NewtonMethod {

    public Vector<Double> xi = new Vector<Double>();
    public Vector<Double> Fx = new Vector<Double>();
    public Vector<Double> derFx = new Vector<Double>();
    public Vector<Double> e = new Vector<Double>();

    public NewtonMethod () {
    }

     public void solve (Double e, Double x0) {
        this.xi.add(x0);
        this.Fx.add(functionFx(this.xi.get(0)));
        this.derFx.add(functionDerFx(this.xi.get(0)));
        this.e.add(0.0);
        double xi_1;
        double fx;
        double derfx;
        do {
            fx = functionFx(this.xi.lastElement());
            derfx = functionDerFx(this.xi.lastElement());
            this.xi.add(this.xi.lastElement() - (fx / derfx));
            this.Fx.add(fx);
            this.derFx.add(derfx);
            this.e.add(Math.abs(this.xi.lastElement() - this.xi.get(this.xi.size()-2)));
        } while (this.e.lastElement() > e);
    }

    private double functionFx (double x) {
        double Fx;
        Fx = 2 * Math.exp(-3 * x) + 1 -x;
        return Fx;
    }
    
    private double functionDerFx (double x) {
        double derFx;
        derFx = -6 * Math.exp(-3 * x) -1;
        return derFx;
    }

    public void outputResult() {
        System.out.println("Kartojimų skaičius: " + this.xi.size());
        for (int i = 0; i < this.xi.size(); i++) {
            System.out.print((i+1) + ". ");
            System.out.printf("    Xi: %.17f", this.xi.get(i));
            System.out.printf("    e: %.17f", this.e.get(i));
            System.out.println();
        }
    }
}
