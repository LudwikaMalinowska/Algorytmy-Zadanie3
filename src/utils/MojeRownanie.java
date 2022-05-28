package utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;



public class MojeRownanie<T extends Number> {
    private final T[][] originalMatrix;
    private final T[][] originalB;
    private final T[][] values;
    private final T[][] b;
    private Class<T> clazz;

    public MojeRownanie(MojaMacierz<T> values, T[][] b, Class<T> clazz) {
        this.originalMatrix = deepCopy(values.getValues());
        this.originalB = deepCopy(b);
        this.values = deepCopy(values.getValues());
        this.b = deepCopy(b);
        this.clazz = clazz;
    }

    private T convertToType(Class<T> clazz,String str) {
        try {
            return clazz.getConstructor(String.class).newInstance(str);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    // https://stackoverflow.com/a/36804604/10476860

    private T[][] convertArrayToType(Class<T> clazz, Double[][] arr) {
        T[][] arr2 = (T[][]) Array.newInstance(clazz, arr.length, arr[0].length);
        try {
            for (int i = 0; i < arr2.length; i++){
                for (int j = 0; j < arr2[0].length; j++){
                    arr2[i][j] = convertToType(clazz, String.valueOf(arr[i][j]));
                }
            }

            return arr2;

//            return clazz.getConstructor(String.class).newInstance(str);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    static <T> T[][] deepCopy(T[][] matrix) {
        return Arrays.stream(matrix)
                .map(arr -> arr.clone())
                .toArray(s -> matrix.clone());
    }
    // https://stackoverflow.com/questions/1564832/how-do-i-do-a-deep-copy-of-a-2d-array-in-java

    public T[][] getOriginalMatrix() {
        return originalMatrix;
    }

    public T[][] getOriginalB() {
        return originalB;
    }

    public T[][] getValues() {
        return values;
    }

    public T[][] getB() {
        return b;
    }

    public T[][] solveGaussFG(){
        Integer[] Q = this.gaussFGkrok1();
//        T[] b1 = (T[]) r[0];
//        Integer[] Q = (Integer[]) r[1];

        T[][] result = this.gaussKrok2();
//        System.out.println("Q: " + Arrays.toString(Q));

        T[][] res1 = (T[][]) Array.newInstance(clazz, values.length, 1);
        List<Integer> Qlist = Arrays.asList(Q);
        for (int i = 0; i < values[0].length; i++){
            int idx = Qlist.indexOf(i);
            res1[i][0] = result[idx][0];
        }

//        System.out.println("res:" + Arrays.toString(result));
//        System.out.println("res1:" + Arrays.toString(res1));
        return res1;
    }


    private T[][] elGaussKrok1(){
        T[][] b1 = b;
        for (int k = 1; k <= values.length; k++){
            b1 = this.k1(k);
        }

        return b;
    }

    private T[][] gaussKrok2(){
//        Double[][] x = new Double[b.length][1];
        T[][] x = (T[][]) Array.newInstance(clazz, b.length, 1);
        int i = values.length;
        int j = values[0].length;
        double Bn = b[b.length-1][0].doubleValue();
        double Ann = values[i-1][j-1].doubleValue();
        //Xn
        x[x.length-1][0] = convertToType(clazz, String.valueOf(Bn / Ann));
        for (int k = x.length-2; k >= 0 ; k--){
//            System.out.println(k);
            double bk = b[k][0].doubleValue();
            double akk = values[k][k].doubleValue();
            double sum = 0;
            for (int n = k; n < values[0].length-1; n++){
                double ak = values[k][n+1].doubleValue();
                sum += ak * x[n+1][0].doubleValue();
            }

            double val = (bk - sum) / akk;
            x[k][0] = convertToType(clazz, String.valueOf(val));
        }

//        T[][] x_t = convertArrayToType(clazz, x);
//        T[][] x_t = (T[][]) x;
        return x;
    }

//
//    private T[][] gaussKrok2(){
//        T[][] x = (T[][]) new Object[b.length][1];
//        int i = values.length;
//        int j = values[0].length;
//        double Bn = b[b.length-1][0].doubleValue();
//        double Ann = values[i-1][j-1].doubleValue();
//        //Xn
//        x[x.length-1][0] = convertToType(clazz, String.valueOf(Bn / Ann));
//        for (int k = x.length-2; k >= 0 ; k--){
//            System.out.println(k);
//            double bk = b[k][0].doubleValue();
//            double akk = values[k][k].doubleValue();
//            double sum = 0;
//            for (int n = k; n < values[0].length-1; n++){
//                double ak = values[k][n+1].doubleValue();
//                sum += ak * x[n+1][0].doubleValue();
//            }
//
//            double val = (bk - sum) / akk;
//            x[k][0] = convertToType(clazz, String.valueOf(val));
//        }
//
////        T[][] x_t = convertArrayToType(clazz, x);
////        T[][] x_t = x;
//        return x;
//    }

    public T[][] solveGaussG(){

        T[][] b2 = this.elGaussKrok1();
        T[][] result = this.gaussKrok2();

        return result;
    }

    private T[][] k1(int k){
        Double[] m = new Double[values.length -k];

        for (int i = 0; i < m.length; i++){
//            System.out.println("-----");
//            System.out.println(i-1);
            m[i] = values[i+k][k-1].doubleValue() / values[k-1][k-1].doubleValue();
        }

//        System.out.println("m:" + Arrays.toString(m)); //ok


        int mi = 0;
        for (int i = k; i < values.length; i++){
            for (int j = k-1; j < values[0].length; j++){
//                System.out.printf("[%d][%d]", i, j);
//                    System.out.println(mi);
                Double val = values[i][j].doubleValue() -
                        (values[k-1][j].doubleValue() * m[mi]);
                T val_t = convertToType(clazz, String.valueOf(val));
                values[i][j] = val_t;
            }

            Double valB = b[i][0].doubleValue() - (m[mi] * b[k-1][0].doubleValue());
            b[i][0] = convertToType(clazz, String.valueOf(valB));

            mi++;
        }

        return b;
    }


    private T[][] gaussPGkrok1(){

        T[][] b1 = b;
        for (int k = 0; k < values.length-1; k++){
            int p = maxCol(k);
            if (k != p){
                //zamień miejscami
                T[] rowK = values[k];
                T[] rowP = values[p];
                values[k] = rowP;
                values[p] = rowK;
                T temp_bk = b1[k][0];
                b1[k][0] = b1[p][0];
                b1[p][0] = temp_bk;
            }
            b1 = this.k1(k+1);
        }

        return b1;
    }

//    public T[][] solveGaussPG(){
//        T[][] b1 = this.gaussPGkrok1();
//        T[][] result = this.gaussKrok2();
//
//        return result;
//    }

    public T[][] solveGaussPG(){
        T[][] b1 = this.gaussPGkrok1();
        T[][] result = this.gaussKrok2();

        return result;
    }

    private int maxCol(int col){
        int maxCol = col;
        double max = values[0][0].doubleValue();
        for (int i = col; i < values[0].length; i++){
            if (Math.abs(values[i][col].doubleValue()) > max){
                maxCol = i;
            }
        }

        return maxCol;
    }

    private Integer[] gaussFGkrok1(){
        T[][] b1 = b;
        int[] Q = IntStream.rangeClosed(0, values.length-1).toArray(); //values.len

        for (int k = 0; k < values.length; k++){
            int[] t = maxInRange(values, k);
            int p = t[0];
            int kol = t[1];

            b1 = switchRow(b1, k, p);

            if (!Arrays.equals(t, new int[]{k, k})){
                switchCol(k, kol);

                int temp = Q[k];
                Q[k] = Q[kol];
                Q[kol] = temp;
            }


            b1 = k1(k+1);
        }

        Integer[] q = IntStream.of( Q ).boxed().toArray( Integer[]::new );
//        Number[][] d = new Number[][]{b1, q};

        return q;
    }



    private T[][] switchRow(T[][] B, int k, int p){
        T[] rowK = values[k];
        T[] rowP = values[p];
        values[k] = rowP;
        values[p] = rowK;
        T temp_bk = B[k][0];
        B[k][0] = B[p][0];
        B[p][0] = temp_bk;

        return B;
    }

    private void switchCol(int k, int kol){
        Double[] col1 = new Double[values.length];
        Double[] col2 = new Double[values.length];

        for (int i = k; i < values.length; i++){
            col1[i] = values[i][k].doubleValue();
            col2[i] = values[i][kol].doubleValue();
        }
        for (int i = k; i < values.length; i++){
            values[i][k] = convertToType(clazz, String.valueOf(col2[i]));
            values[i][kol] = convertToType(clazz, String.valueOf(col1[i]));
        }
    }

    private int[] maxInRange(T[][] vals, int k){
        Double max = vals[k][k].doubleValue();
        int[] maxCords = new int[]{k,k};

        for (int i = k; i < vals.length; i++){
            for (int j = k; j < vals[0].length; j++){
                if (vals[i][j].doubleValue() > max){
                    max = vals[i][j].doubleValue();
                    maxCords = new int[]{i, j};
                }
            }
        }

        return maxCords;
    }



}
