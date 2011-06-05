package skaitiniaimetodai;

import java.util.Vector;

public class SeidelMethod {

    public ArrayOfVectors x;
    public double[][] A;
    public double[] B;
    public Vector<Double> z = new Vector<Double>();
    public Vector<Double> netiktis = new Vector<Double>();

    public SeidelMethod(double[][] A, double[] B){
        this.A = A;
        this.B = B;
    }

    public void calculateZ() {
        Double max = 0.0;
        Double temp = 0.0;
        for (int i = 0; i < this.x.array.length; i++) {
            int len = this.x.array[i].size() - 1;  
            temp = temp + Math.pow((this.x.array[i].lastElement() - this.x.array[i].get(len - 1)), 2.0);
        }
        temp = Math.sqrt(temp);
        this.z.add(temp);
    }

    public void calculateNetiktis() {
        Double allSum = 0.0;
        for (int row = 0; row < A.length; row++) {
            Double sum = 0.0;
            for (int col = 0; col < A[0].length; col++) {
                sum += A[row][col] * x.array[col].lastElement();
            }
            allSum += Math.pow((sum - B[row]), 2.0);
        }
        this.netiktis.add(Math.sqrt(allSum));
    }
    
    public void calculate(Double e) {
        this.x = new ArrayOfVectors(B.length);
        setXZeros();
        do {
            for (int x = 0; x < this.x.array.length; x++) {
                double sum = 0.0;
                for (int col = 0; col < this.x.array.length; col++) {
                    if (x != col) {
                        sum += (-1) * A[x][col] * this.x.array[col].lastElement();
                    }
                }
                double res = (sum + B[x]) / A[x][x];
                
                this.x.array[x].add(res);
                
            }
            calculateZ();
            calculateNetiktis();
          
        } while((z.lastElement().compareTo(e) > 0) || netiktis.lastElement().compareTo(e) > 0);
    }

    public boolean check2Rule(double[][] a) {
        //Pagrindinės įstrižainės elementų vyravimo sąlyga stulpeliuose.
    for (int i = 0; i < a.length; i++) {
        double sum = 0.0;
        for (int j = 0; j < a[0].length; j++) {
            if (i != j) sum += a[i][j];
        }
        
        if ((Math.abs(sum) / Math.abs(a[i][i])) > 1) {
            System.out.println("Netenkinama salyga: nera istrizaines vyravimo eiluteje: " + (i+1));
            return false;
        }
    }

    for (int i = 0; i < a.length; i++) {
        double sum = 0.0;
        for (int j = 0; j < a[0].length; j++) {
            if (i != j) sum += a[j][i];
        }
        if ((Math.abs(sum) / Math.abs(a[i][i])) > 1) {
            System.out.println("Netenkinama salyga: nera istrizaines vyravimo stulpelyje: " + (i+1));
            return false;
        }
    }
    return true;
    }

    public void setXZeros() {
        for (int i = 0; i < this.x.array.length; i++) {
            this.x.array[i].add(0.0);
        }
    }

    public void printXWZ() {
        int countIters = this.x.array[0].size();
        int countVectors = this.x.array.length;

        System.out.print("iter. ");
        for (int i = 0; i < countVectors; i++) {
            System.out.print("X" + (i+1) + "    ");
        }
        System.out.print("||X_k+1 - X_k||   ");
        System.out.print("||Ax - b||");
        System.out.println();

        for (int j = 1; j < countIters; j++) {
            System.out.print((j) + ".   ");
                for (int i = 0; i < countVectors; i++) {
                System.out.print(this.x.array[i].get(j) + " ");
            }
            System.out.print(this.z.get(j-1) + " ");
            System.out.print(this.netiktis.get(j-1) + " ");
            System.out.println();
        }
    }
}
