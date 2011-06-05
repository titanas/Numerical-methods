package skaitiniaimetodai;

import java.util.Vector;

public class ConjugateGradientsMethod {

    public double[][] A;
    public double[] B;
    public ArrayOfVectors x;
    public ArrayOfVectors p;
    public ArrayOfVectors z;
    public ArrayOfVectors r;
    public Vector<Double> T = new Vector<Double>();
    public Vector<Double> beta = new Vector<Double>();

    public ConjugateGradientsMethod (double[][] A, double[] B) {
        this.A = A;
        this.B = B;
    }

    public void setXZeros(ArrayOfVectors x) {
        for (int i = 0; i < x.array.length; i++) {
            x.array[i].add(0.0);
        }
    }

    public void calculateP0() {
        //P = A*X-F
        for (int i = 0; i < this.B.length; i++) {
            Double temp = 0.0;
            for (int j = 0; j < this.A[i].length; j++) {
                temp += this.A[i][j] * this.x.array[j].lastElement();
            }
            temp -= this.B[i];
            this.p.array[i].add(temp);
        }
        
    }

    public void calculateR() {
        //P = A*P
        for (int i = 0; i < this.p.array.length; i++) {
            Double temp = 0.0;
            for (int j = 0; j < this.A[i].length; j++) {
                temp += this.A[i][j] * this.p.array[j].lastElement();
            }
            this.r.array[i].add(temp);
        }
    }

    public Double calculateZZ() {
        Double ZZ = 0.0;
        for (int i = 0; i < this.z.array.length; i++) {
            ZZ += this.z.array[i].lastElement() * this.z.array[i].lastElement();
        }
        return ZZ;
    }

    public Double calculateZZNotLast() {
        Double ZZ = 0.0;
        for (int i = 0; i < this.z.array.length; i++) {
            int last = this.z.array[i].size() - 1;
            ZZ += this.z.array[i].get(last - 1) * this.z.array[i].get(last - 1);
        }
        return ZZ;
    }

    public void calculateT() {
        Double rp = 0.0;

        for (int i = 0; i < this.r.array.length; i++) { 
            rp += this.r.array[i].lastElement() * this.p.array[i].lastElement();
        }
        Double zz= calculateZZ();
        Double t = zz / rp;
        this.T.add(t);
    }

    public void calculateX() {
        calculateT();
        Double temp = 0.0;
        for (int i = 0; i < this.x.array.length; i++) {
            temp = this.x.array[i].lastElement() - this.T.lastElement() *
                    this.p.array[i].lastElement();
            this.x.array[i].add(temp);
        }
    }

    public void calculateZ() {
        Double temp = 0.0;
        for (int i = 0; i < this.z.array.length; i++) {
            temp = this.z.array[i].lastElement() - this.T.lastElement() *
                    this.r.array[i].lastElement();
            this.z.array[i].add(temp);
        }
    }

    public void calculateBeta() {
        Double ZZ = calculateZZ();
        Double ZZNotLast = calculateZZNotLast();
        this.beta.add(ZZ / ZZNotLast);
    }

    public void calculateP() {
        calculateBeta();
        Double temp = 0.0;
        for (int i = 0; i < this.p.array.length; i++) {
            temp = this.z.array[i].lastElement() + (this.beta.lastElement() *
                    this.p.array[i].lastElement());
            this.p.array[i].add(temp);
        }
    }

    public void makeZFromPLast() {
        for (int i = 0; i < this.p.array.length; i++) {
            this.z.array[i].add(this.p.array[i].lastElement());
        }
    }

    public void calculate(Double e) {
        this.x = new ArrayOfVectors(this.B.length);
        this.p = new ArrayOfVectors(this.B.length);
        this.z = new ArrayOfVectors(this.B.length);
        this.r = new ArrayOfVectors(this.B.length);
        setXZeros(this.x);
        calculateP0();
        makeZFromPLast();

        while (calculateZZ().compareTo(e * e) > 0) {
            calculateR();   
            calculateX();       
            calculateZ();      
            calculateP();
        }
    }

    public void printXWZ() {
        this.x.printXWAnother(this.z);
    }

    public void printVector(Vector<Double> a) {
        System.out.println("Vektorius");
        for (int i = 0; i < a.size(); i++) {
            System.out.print(a.get(i) + " ");
        }
        System.out.println();
    }

}
