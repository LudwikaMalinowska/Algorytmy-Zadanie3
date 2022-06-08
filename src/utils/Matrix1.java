package utils;

import java.util.Arrays;
import java.util.HashMap;

public class Matrix1 {

    HashMap<Integer, String> p_map3 = new HashMap<>();
    {
        //dla 3
        p_map3.put(0, "P00");
        p_map3.put(1, "P01");
        p_map3.put(2, "P02");
        p_map3.put(3, "P03");
        p_map3.put(4, "P10");
        p_map3.put(5, "P20");
        p_map3.put(6, "P30");
        p_map3.put(7, "P11");
        p_map3.put(8, "P12");
        p_map3.put(9, "P21");
        p_map3.put(10, "WW"); //wyrazy wolne ostatni -1
    }

    double[][] arr3 = new double[10][10];
    {
        for (double[] row : arr3)
            Arrays.fill(row, 0.0);
    }

}
