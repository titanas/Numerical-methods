/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package skaitiniaimetodai;
//44 uzdavinys
import java.io.*;
import java.lang.Math;

public class Uzd1 {

    /**
    public static void main(String[] args) {		//2 uzd.
        File file = new File("C:\\Users\\Lukas\\Desktop\\duom.txt");
        Spline spline = new Spline(file);       //spline1 spline2
        if (spline.gety().isEmpty()){           //indv1   indv2
            System.out.println("Faile nera pilna reiksmiu lentele, funkcijos reiksmes randamos...");
            spline.getFunctionY();
        }
        System.out.println("Vector x: ");       
        spline.printVector(spline.getx());
        System.out.println("Vector y (X0): ");
        spline.printVector(spline.gety());

        spline.calculateH();       
        spline.calculateF();
        spline.calculateG();
        
        System.out.println("Splaino koeficientai");
        spline.calculateSplineCoef();
        System.out.println("Vector e (X1): ");
        spline.printVector(spline.gete());
        System.out.println("Vector G (X2): ");
        spline.printVector(spline.getG());
        System.out.println("Vector H (X3): ");
        spline.printVector(spline.getH());

        // skaiciuojama splaino reiksme, pagal x
        double x = 5;
        System.out.println("Splaino reiksme taske x = " + x
                + " yra " + spline.calculateXValue(x));

    }
     **/
}







/**												//1 uzd
        File file = new File("C:\\Users\\Lukas\\Desktop\\indv2.txt");		// failo kiekviena eilute: a b c d
        Solver solver = new Solver(3, file);		//3 nurodo kiek lygciu sistemoje
        System.out.println("Triistrizainiu lygciu sistemu sprendimas.");
        solver.readFile();
        System.out.println("Duomenys: ");
        System.out.println("Vector a");
        solver.printVector(solver.geta());
        System.out.println("Vector b");
        solver.printVector(solver.getb());
        System.out.println("Vector c");
        solver.printVector(solver.getc());
        System.out.println("Vector d");
        solver.printVector(solver.getd());
        System.out.println();
        System.out.println("Perkelties metodo pakankama konvergavimo salyga:");
        System.out.println("1. |bi| >= |ai| + |ci|; i = 1..n. Tenkina: " +
                solver.checkDiagonalRule());
        System.out.println("2. |bi| > |ai| + |ci|; su bent vienu i. Tenkina: " +
                solver.checkDiagonalStrictRule());

        System.out.println();
        System.out.println("Tiesiogine eiga. Skaiciuojama C, D.");
        solver.calculateCD();
        System.out.println("Vector C");
        solver.printVector(solver.getC());
        System.out.println("Vector D");
        solver.printVector(solver.getD());

        System.out.println();
        System.out.println("Atbuline eiga. Skaiciuojamas X.");
        solver.calculateX();
        System.out.println("Vector X");
        solver.printVector(solver.getx());
 */
