package utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static utils.GenericNumberUtils.*;


public class MojeRownanie<T extends Number> {
    private final T[][] originalMatrix;
    private final T[][] originalVector;
    private final T[][] values;
    private final T[][] b;
    private Class<T> clazz;

    public MojeRownanie(MojaMacierz<T> values, T[][] b, Class<T> clazz) {
        //clone values zamiast deepCopy
        this.originalMatrix = deepCopy(values.getValues());
        this.originalVector = deepCopy(b);
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

    public T[][] getOriginalVector() {
        return originalVector;
    }

    public T[][] getValues() {
        return values;
    }

    public T[][] getB() {
        return b;
    }

    //musi zostać
    private T[][] gaussKrok2(){
//        Double[][] x = new Double[b.length][1];
        T[][] x = (T[][]) Array.newInstance(clazz, b.length, 1);
        int i = values.length;
        int j = values[0].length;
        // zamienić na generyczny typ zamiast double
        // last x result
        T Bn = b[b.length-1][0];
        // last x number
        T Ann = values[i-1][j-1];
        //Xn
        // zamiast convert to type dać moje generyczne operacje
        x[x.length-1][0] =divide(Bn,Ann);
        // to jest ten krok gdzie przechodzi już mając wynik ostatniego x i wylicza
        for (int k = x.length-2; k >= 0 ; k--){
//            System.out.println(k);
            T bk = b[k][0];
            T akk = values[k][k];
            T sum = getZero(bk);
            for (int n = k; n < values[0].length-1; n++){
                T ak = values[k][n+1];
                sum =  GenericNumberUtils.sum(sum,multiply(ak,x[n+1][0]));
            }

           T val = divide((substract(bk,sum)),akk);
            x[k][0] = val;
        }

        return x;
    }





    private T[][] k1(int k){
        T[] m = GenericVectorUtils.getZerosVector(values[0][0],values.length -k);

        boolean zero = true;
//        System.out.println(values[k-1][k-1]);
        for (int i = k; i < values.length; i++){
//            System.out.println(values[i][k-1]);
            if (!values[i][k-1].equals(getZero(values[0][0]))){
                zero = false;
            }
        }

        if (!zero) {


            for (int i = 0; i < m.length; i++) {
                m[i] = divide(values[i + k][k - 1], values[k - 1][k - 1]);
            }

//        System.out.println("m:" + Arrays.toString(m)); //ok


            int mi = 0;
            for (int i = k; i < values.length; i++) {
                for (int j = k - 1; j < values[0].length; j++) {
//                System.out.printf("[%d][%d]", i, j);
//                    System.out.println(mi);
                    T val = substract(values[i][j], (multiply(values[k - 1][j], m[mi])));
                    values[i][j] = val;
                }

                T valB = substract(b[i][0], multiply(m[mi], b[k - 1][0]));
                b[i][0] = valB;

                mi++;
            }

        }
//        else {
//            System.out.println("skip");
//        }

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
//            System.out.println("bf");
//            for (T[] row : values) {
//                System.out.println(Arrays.toString(row));
//            }
            b1 = this.k1(k+1);
//            System.out.println("after");
//            for (T[] row : values) {
//                System.out.println(Arrays.toString(row));
//            }
        }

        return b1;
    }


    public T[][] solveGaussPG(){
        T[][] b1 = this.gaussPGkrok1();
        T[][] result = this.gaussKrok2();

        return result;
    }

    private int maxCol(int col){
        int maxCol = col;
        T max = values[0][0];
        for (int i = col; i < values[0].length; i++){
            if (isGreater(abs(values[i][col]), max) ){
                maxCol = i;
            }
        }

        return maxCol;
    }




}
