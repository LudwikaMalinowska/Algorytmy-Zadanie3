package models;

public class OtherTestResult <T extends Number> {
    private final T error;
    private final long time;
    private final T[][] values;
    private final T[][] MonteCarloValues;


    public OtherTestResult(T error, long time,T[][] values,T[][] monteCarloValues ){
        this.error = error;
        this.time = time;
        this.values = values;
        this.MonteCarloValues= monteCarloValues;
    }

    public T[][] getValues() {return values;}
    public T[][] getMonteCarloValues() {return MonteCarloValues;}

    public T getError() {
        return error;
    }

    public long getTime() {
        return time;
    }
}
