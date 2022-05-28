package utils;

public class StaticGauss<T extends Number> {

    public static Double[][] solveGaussPG(Double[][] values,
                                          Double[][] b){
        MojaMacierz<Double> mm1 = new MojaMacierz<>(values, Double.class);
        MojeRownanie<Double> mr1 = new MojeRownanie<>(mm1, b, Double.class);
        Double[][] result = mr1.solveGaussPG();

        return result;
    }


    public static Float[][] solveGaussPG(Float[][] values,
                                          Float[][] b){
        MojaMacierz<Float> mm1 = new MojaMacierz<>(values, Float.class);
        MojeRownanie<Float> mr1 = new MojeRownanie<>(mm1, b, Float.class);
        Float[][] result = mr1.solveGaussPG();

        return result;
    }

//    public static T[][] solveGaussPG(MojaMacierz<T> mm, T[][] b, Class<T> clazz){
//        return b;
//    }
}
