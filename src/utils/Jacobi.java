package utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Jacobi<T extends Number> {
    Class<T> clazz;

//    public T[] countJacobi(T[][] values, T[][] vector){
//        T[] x0 = this.zeroArray(values.length, values[0][0]);
////        T[] x_arr = this.zeroArray(values.length, values[0][0]);
//        T[] x_arr = this.zeroArray(values.length, values[0][0]);
//
//        for (int i = 0; i < 10; i++) {
//            for (int j = 0; j < values.length; j++){
//                x_arr[j] = 1/(values[j][j].doubleValue())
//            }
//        }
//
//        return x_arr;
//    }

    public static Double[] countJacobi(Double[][] values, Double[][] vector){
        Double[] x_arr = new Double[values.length];
        Arrays.fill(x_arr, 0.0);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < values.length; j++){
                Double roznica = vector[j][0];
                for (int k = 0; k < values[0].length; k++){
                    if (k != j)
                        roznica -= values[j][k] * x_arr[k];
                }

                x_arr[j] = (1/values[j][j]) * roznica;
            }
        }

        return x_arr;
    }

    private T[] zeroArray(int size, T sample){
        T[] arr = (T[]) Array.newInstance(clazz, size);
        for (int i = 0; i < arr.length; i++){
            arr[i] = GenericNumberUtils.getZero(sample);
        }

        return arr;
    }

    public static void main(String[] args) {
        Double[][] arr_a = new Double[][]{
                {10.0, -1.0, 2.0, 0.0},
                {-1.0, 11.0, -1.0, 3.0},
                {2.0, -1.0, 10.0, -1.0},
                {0.0, 3.0, -1.0, 8.0}
        };
        Double[][] arr_y = new Double[][]{
                {6.0},
                {25.0},
                {-11.0},
                {15.0},
        };
        Double[] result = Jacobi.countJacobi(arr_a, arr_y);
        System.out.println(Arrays.toString(result));
    }
}
