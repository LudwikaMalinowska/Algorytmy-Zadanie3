package models;

public class Matrix <T extends Number> {
    private final T[][] values;
    private final int size;

    public T[][] getValues() {
        return values;
    }

    public Matrix(T[][] values) {
        this.values = values;
        this.size = values.length;
    }


}
