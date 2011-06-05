package skaitiniaimetodai;
//nr 44 x=2exp(-3x)+1 (2 - Paprastųjų iteracijų metodas; 4 - Niutono metodas;)
/**
Nustatykite intervalą [a,b], kuriame yra tik vienas netiesinės lygties sprendinys ir išspręskite
netiesinę lygtį nurodytais metodais. Palyginkite gautą rezultatą su tiksliu sprendiniu.
Atsiskaitant reikia suformuluoti uždavinį, pateikti skaičiavimo rezultatų lentelę, nubrėžti grafikus,
padaryti išvadas. Taip pat reikia turėti veikiančią programą. Šį darbą reikia apginti iki 2011-04-07
d. Varianto numeris sutampa su numeriu grupės sąraše.
**/

public class Uzd2 {
/**
    public static void main(String[] args) {
        System.out.println("Funkcija: F(x) = 2 * exp(-3 * x) + 1 - x");
        int N = 25;         // į kiek intervalas skaidomas
        Double from = 0.0;  // pradinio intervalo pradžia
        Double to = 1.0;  // pradinio intervalo pradžia
        
        System.out.println("\n**************** Intervalo paieška ****************");
        System.out.printf("N = %d, nuo %f iki %f\n", N, from, to);
        IntervalFinder finder = new IntervalFinder(N, from, to);
        Interval interval = finder.getFirstInterval();
        if (interval != null)
            System.out.printf("Intervalas yra [%f %f]\n", interval.getFrom(), interval.getTo());

        System.out.println("\n**************** Iteracijų metodas ****************");
        IterationMethod iteration = new IterationMethod();
        Double e = 0.001;                // tikslumas
        //from = interval.getFrom();          // intervalo pradžia
        //to = interval.getTo();              // intervalo pabaiga
        from = 0.0;
        to = 1.0;
        System.out.printf("e = %f, nuo %f iki %f\n", e, from, to);
        iteration.solve(e, from, to);
        System.out.printf("q = %.17f, (1-q)/q = %.17f, ((1-q)/q)*e = %.17f\n", iteration.q, iteration.Q, iteration.Q * e);
        iteration.outputResult();

        System.out.println("\n**************** Niutono metodas ****************");
        NewtonMethod newton = new NewtonMethod();
        Double x0 = to;         // pradinė x reikšmė
        System.out.printf("e = %f, x0 = %f\n", e, x0);
        newton.solve(e, x0);
        newton.outputResult();
    }
 **/
}
