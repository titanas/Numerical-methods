package skaitiniaimetodai;

import java.util.Vector;

public class Integral {

    public int line = 3;
    public Double[] c = new Double[line];
    public Double[] x = new Double[line];

    public Integral() {
        c[0] = 0.5555556;
        c[1] = 0.8888889;
        c[2] = 0.5555556;
        x[0] = -0.774596669;
        x[1] = 0.0;
        x[2] = 0.774596669;
    }

    public Vector<Integer> makeN (int n) {
        Vector<Integer> N = new Vector<Integer>();
        for (int i = 0; i < n; i++) {
            if (i == 0) N.add(2);
            else N.add(N.lastElement() * 2);
        }
        return N;
    }

    public Vector<Double> makeH (Vector<Integer> N, Double a, Double b) {
        Vector<Double> h = new Vector<Double>();
        for (int i = 0; i < N.size(); i++) {
            h.add((b - a) / N.get(i));
        }
        return h;
    }

    public Double calculateIntegral (Integer N, Double a, Double b) {
        Double sum = 0.0;
        Double h = (b - a) / N;
        for (int i = 1; i <= N; i++) {
            //Double hi = a + i * h;
            Double xi = a + (i - 1) * h;
            Double xiii = a + i * h;
            Double xii = (xi + xiii) / 2;
            sum += (func(xi) + 4 * func(xii) + func(xiii)) * h / 6;
        }
        return sum;
    }

    public Vector<Double> calculateAllIntegral(Vector<Integer> N, Double a, Double b) {
        Vector<Double> Sn = new Vector<Double>();
        for (int i = 0; i < N.size(); i++) {
            Sn.add(calculateIntegral(N.get(i), a, b));
        }
        return Sn;
    }

    public Vector<Double> calculateAllGaussian(Vector<Integer> N, Double a, Double b) {
        Vector<Double> Sn = new Vector<Double>();
        for (int i = 0; i < N.size(); i++) {
            Sn.add(calculateGaussian(N.get(i), a, b));
        }
        return Sn;
    }

    public Vector<Double> calculateDiff(Vector<Integer> N, Double a, Double b) {
        Vector<Double> rezY = new Vector<Double>();
        for (int i = 0; i < N.size(); i++) {
            Double h = (b - a) / N.get(i);
            Vector<Double> y = calculateDiffY(h, N.get(i), a, b);
            rezY.add(y.lastElement());
        }
        return rezY;
    }

    public Vector<Double> calculateDiffY(Double h, Integer N, Double a, Double b) {
        Vector<Double> y = new Vector<Double>();
        Vector<Double> t = new Vector<Double>();
        y.add(1.0);
        for (int j = 0; j <= N; j++) {
            t.add(a + j * h);
        }
               
        Double k1 = diffFunc(a, y.lastElement());
        Double k2 = diffFunc(a + h / 2, y.lastElement() + k1 / 2 * h);
        y.add(y.lastElement() + k2 * h);

        for (int i = 1; i <= N-1; i++) {
            k1 = diffFunc(t.get(i), y.get(i));
            k2 = diffFunc(t.get(i) + h / 2, y.get(i) + k1 / 2 * h);
            y.add(y.lastElement() + k2 * h);
        }
        return y;
    }

    public Double diffFunc(Double x, Double u) {
        return (Math.cos(u) / (1.25 + x) - 0.1 * u * u);
    }

    //public Double diffFunc(Double x, Double u) {
    //    return (-u + Math.sin(x));
    //}

    

    public Double calculateGaussian(Integer m, Double a, Double b) {
        Double h = (b - a) / m;
        Double rez = 0.0;
        for (int j = 0; j < m; j++ ) {
            Double sum = 0.0;
            Double aa = a + j * h;
            Double bb = a + (j+1) * h;
            for (int i = 0; i < this.line; i++) {
                sum += (bb - aa) / 2 * this.c[i] * func((bb - aa) / 2 * this.x[i] + (aa + bb) / 2);
            }
            rez += sum;
        }
        return rez;
    }

    public Vector<Double> calculateGaussianE(Vector<Double> S, int p) {
        Vector<Double> e = new Vector<Double>();
        for (int i = 0; i < S.size(); i++) {
            if (i == 0) e.add(Double.POSITIVE_INFINITY);
            else {
               e.add(Math.abs(S.get(i) - S.get(i-1)) / (Math.pow(2, p) - 1));
            }
        }
        return e;
    }

    public Vector<Double> calculateE(Vector<Double> S, Double lastSn, int p) {
        Vector<Double> e = new Vector<Double>();
        for (int i = 0; i < S.size(); i++) {
            if (i == S.size()-1) e.add(Math.abs(S.get(i) - lastSn) / (Math.pow(2, p) - 1));
            else {
               e.add(Math.abs(S.get(i) - S.get(i+1)) / (Math.pow(2, p) - 1));
            }
        }
        return e;
    }

    public Vector<Double> calculateProcE(Vector<Double> e) {
        Vector<Double> p = new Vector<Double>();
        p.add(0.0);
        for (int i = 1; i < e.size(); i++) {
            if (i == 1) {
                p.add(0.0);
            } else {
                p.add(e.get(i-1) / e.get(i));
            }
        }
        return p;
    }

    //public Double func(Double x) {
    //    return x * Math.pow(Math.E, 2*x);
    //}

    //public Double func(Double x) {
    //    return 3 * Math.sin(x/2);
    //}

    //Mano
    //public Double func(Double x) {
    //    Double a = Math.sin(2 * x) * Math.sin(2 * x);
    //   return 1.0 / a;
    //}

    public Double func(Double x) {
        Double a = x*x;
       return a;
    }
    
    //[8:06:48 PM] PS1 Tomas: x^2 * sin(2x)
    //public Double func(Double x) {
    //    Double a = x*x * Math.sin(2*x);
    //    return a;
    //}
}

