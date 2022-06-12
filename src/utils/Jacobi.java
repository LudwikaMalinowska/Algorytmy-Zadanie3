package utils;

import java.util.Arrays;
import static utils.GenericNumberUtils.*;
import static utils.GenericMatrixUtils.getZerosMatrix;

public class Jacobi<T extends Number> {


    public static <T extends Number> T[][] countJacobi(T [][] values, T[][] vector){
       T[][] x_arr = getZerosMatrix(values[0][0],values.length, 1);
       T[][] x_arr_prev;

        for (int i = 0; i < 10; i++) {
            x_arr_prev = Arrays.copyOf(x_arr, x_arr.length);
            for (int j = 0; j < values.length; j++){
                T roznica = vector[j][0];
                for (int k = 0; k < values[0].length; k++){
                    if (k != j)
                        roznica = substract(roznica,multiply(values[j][k], x_arr_prev[k][0]));

                }

                x_arr[j][0] = multiply(divide(getOne(values[0][0]),values[j][j]),roznica);
            }
        }

        return x_arr;
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
        Double[][] result = Jacobi.countJacobi(arr_a, arr_y);
        System.out.println(Arrays.toString(result));
    }
}
