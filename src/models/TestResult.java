package models;

public class TestResult  <T extends Number>{
    private final T error;
    private final long time;

    public TestResult(T error, long time){
        this.error = error;
        this.time = time;
    }

    public T getError() {
        return error;
    }

    public long getTime() {
        return time;
    }
}
