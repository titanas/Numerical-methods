package skaitiniaimetodai;

import java.util.Vector;

public class Uzd4 {

    public static void main(String[] args) {

        System.out.println("Funkcija f(x) = 1 / sin(2x)^2");

        Integral integral = new Integral();
        //System.out.println("rr: " + integral.func(Math.PI));
        //System.out.println("rr: " + 1.0 / (Math.sin(2.0)) * (Math.sin(2.0)));
        //Double a = (Math.sin(2.0)) * (Math.sin(2.0));
        //System.out.println(a);
        //System.out.println(1.0 / a);

        //System.exit(0);
        Vector<Integer> N = integral.makeN(10);
        Double a = 0.0; Double b =  10.0;
        System.out.println("nuo a = " + a + " iki b = " + b);
        //Double a = 0.0; Double b = Math.PI;
        //Double a = 0.0; Double b = 4.0;
        Vector<Double> h = integral.makeH(N, a, b);
        
        // Simsono metodas
        Vector<Double> Sn = integral.calculateAllIntegral(N, a, b);
        Double lastSn = integral.calculateIntegral(N.lastElement()*2, a, b);
        Vector<Double> e = integral.calculateGaussianE(Sn, 2);
        Vector<Double> procEE = integral.calculateProcE(e);

        System.out.println("*********** Skaitinis integravimas Simsono metodu ***********");
        System.out.println("  N    h    Sn    paklaida (Rungės)  paklaida (k.)");
        for (int i = 0; i < N.size(); i++) {
            System.out.println(N.get(i) + "    " + h.get(i) + "    " + Sn.get(i) +
                    "    " + e.get(i) + "    " + procEE.get(i));
        }
        
        // Gauso 3 eiles metodas
        Vector<Double> Gaussian = integral.calculateAllGaussian(N, a, b);
        Vector<Double> gE = integral.calculateGaussianE(Gaussian, 6);
        Vector<Double> procGE = integral.calculateProcE(gE);
        System.out.println("*********** Skaitinis integravimas Gauso 3 eilės metodu ***********");
        System.out.println("  N    Tn    paklaida (Rungės)  paklaita (k.)");
        for (int i = 0; i < N.size(); i++) {
            System.out.println(N.get(i) + "    " + Gaussian.get(i) + "    " + gE.get(i) + "    " + procGE.get(i));
        }
     

        // 2 dalis
        Integral in = new Integral();
        Double aa = 0.0;
        Double bb = 1.0;
        //Integer N = 20;
        //Double h = (a + b) / N;
        Vector<Integer> NN = in.makeN(10);
        Vector<Double> y = in.calculateDiff(NN, aa, bb);
        Vector<Double> ee = in.calculateGaussianE(y, 1);    // P = 1
        Vector<Double> procE = in.calculateProcE(ee);

        System.out.println("***************** Koši uždavinio rezultatai *****************");
        System.out.println("N       h            y                 paklaida                 paklaida (k.)");
        for (int i = 0; i < NN.size(); i++) {
            System.out.println(NN.get(i) + "    " + (bb-aa)/NN.get(i) + "    " + y.get(i) +
                    "    " + ee.get(i) + "    " + procE.get(i));
        }
    }
}
