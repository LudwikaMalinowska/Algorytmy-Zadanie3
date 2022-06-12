package utils;

import models.Fraction;

import java.math.BigInteger;

public class StaticGauss<T extends Number> {

    public static Double[][] solveGaussPG(Double[][] values,
                                          Double[][] b){
        MojaMacierz<Double> mm1 = new MojaMacierz<>(values, Double.class);
        MojeRownanie<Double> mr1 = new MojeRownanie<>(mm1, b, Double.class);
        Double[][] result = mr1.solveGaussPG();

        return result;
    }


    public static <T extends Number> T[][] solveGaussPG(T[][] values,
                                          T[][] b, T classSample){
        MojaMacierz<T> mm1 = new MojaMacierz<>(values, (Class<T>) classSample.getClass());
        MojeRownanie<T> mr1 = new MojeRownanie<>(mm1, b, (Class<T>) classSample.getClass());
       T[][] result = mr1.solveGaussPG();

        return result;
    }


    public static Float[][] solveGaussPG(Float[][] values,
                                          Float[][] b){
        MojaMacierz<Float> mm1 = new MojaMacierz<>(values, Float.class);
        MojeRownanie<Float> mr1 = new MojeRownanie<>(mm1, b, Float.class);
        Float[][] result = mr1.solveGaussPG();

        return result;
    }


    public static Fraction[][] solveGaussPG(Fraction[][] values,
                                            Fraction[][] b){
        MojaMacierz<Fraction> mm1 = new MojaMacierz<>(values, Fraction.class);
        MojeRownanie<Fraction> mr1 = new MojeRownanie<>(mm1, b, Fraction.class);
        Fraction[][] result = mr1.solveGaussPG();

        return result;
    }

//    public static T[][] solveGaussPG(MojaMacierz<T> mm, T[][] b, Class<T> clazz){
//        return b;
//    }
}
