package utils;

import java.util.Arrays;
import static utils.GenericNumberUtils.*;
import static utils.GenericMatrixUtils.getZerosMatrix;
import static utils.GenericMatrixUtils.testError;

public class Jacobi<T extends Number> {


    public static <T extends Number> T[][] countJacobi(T [][] values, T[][] vector, T dokladnosc){
       T[][] x_arr = getZerosMatrix(values[0][0],values.length, 1);
       T[][] x_arr_prev = getZerosMatrix(values[0][0],values.length, 1);

        for (int i = 0; i <= 1000; i++) {
            if (i > 1) {
                T norma = testError(x_arr, x_arr_prev);

                if (isGreater(dokladnosc, norma)){
                    break;
                }


            }
            x_arr_prev = GenericMatrixUtils.cloneMatrix(x_arr);

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

    public static <T extends Number> T convertToType(Class<T> clazz,String str) {
        try {
            return clazz.getConstructor(String.class).newInstance(str);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
        Double[][] result = Jacobi.countJacobi(arr_a, arr_y, 1e-6);
//        System.out.println(Arrays.toString(result));

        for (Double[] row : result){
            System.out.println(Arrays.toString(row));
        }
    }
}
