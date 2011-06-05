package skaitiniaimetodai;

public class Interval {

    private Double from;
    private Double to;

    public Interval (Double from, Double to) {
        this.from = from;
        this.to = to;  
    }

    public Double getFrom () {
        return this.from;
    }

    public Double getTo () {
        return this.to;
    }
}
