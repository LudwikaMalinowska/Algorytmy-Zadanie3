package tests;

import utils.StaticGauss;

import java.util.Arrays;

public class SparseTest {
    public static void main(String[] args) {
        //        Double[][] a = {
//                {2.0, 4.0, 2.0, 0.0},
//                {0.0, -2.0, -2.0, 1.0},
//                {0.0, 1.0, 3.0, -1.0},
//                {0.0, -3.0, 0.0, 1.0}
//
//        };
//
//        Double[][] vector = {
//                {4.0},
//                {0.0},
//                {0.0},
//                {2.0},
//        };

        Double[][] a = {
                {2.0, 4.0, 2.0, 0.0},
                {0.0, -3.0, 0.0, 1.0},
                {0.0, 0.0, 3.0, -0.6666666},
                {0.0, 0.0, 0.0, -0.1111111}

        };

        Double[][] vector = {
                {4.0},
                {2.0},
                {0.66666666},
                {-0.8888888},
        };

        Double[][] g = StaticGauss.solveGaussPG_sparse(a, vector, a[0][0]);

        for (Double[] row : g) {
            System.out.println(Arrays.toString(row));
        }

    }
}
