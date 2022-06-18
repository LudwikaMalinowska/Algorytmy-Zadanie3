package utils;

import java.util.Arrays;

import static utils.GenericMatrixUtils.getZerosMatrix;
import static utils.GenericMatrixUtils.testError;
import static utils.GenericNumberUtils.*;

public class Seidel<T extends Number> {

    public static <T extends Number> T[][] countSeidel(T[][] values, T[][] vector, T dokladnosc) {
        T[][] x_arr = getZerosMatrix(values[0][0], values.length, 1);

        for (int i = 0; i <= 1000; i++) {
            T[][] x_arr_prev = GenericMatrixUtils.cloneMatrix(x_arr);
            if (i > 1) {
                T norma = testError(x_arr, x_arr_prev);

                if (isGreater(dokladnosc, norma)) {
                    break;
                }
            }
            for (int j = 0; j < values.length; j++) {
                T roznica = vector[j][0];
                for (int k = 0; k < values[0].length; k++) {
                    if (k != j)
                        roznica = substract(roznica, multiply(values[j][k], x_arr[k][0]));

                }
                x_arr[j][0] = multiply(divide(getOne(values[0][0]), values[j][j]), roznica);
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
        Double[][] result = Seidel.countSeidel(arr_a, arr_y, 1e-6);
//        System.out.println(Arrays.toString(result));
        for (Double[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}
