package models;

public class MatrixMax<T extends Number> {
    private final int i;
    private final int j;

    public MatrixMax(int i){
        this.i = i;
        this.j = -1;
    }

    public MatrixMax(int i, int j){
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
