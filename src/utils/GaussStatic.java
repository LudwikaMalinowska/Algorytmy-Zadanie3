//package utils;
//
//import java.lang.reflect.Array;
//
//public class GaussStatic<T extends Number> {
//
//    public T[][] solveGaussPG(MojaMacierz<T> mm, T[][] b, Class<T> clazz){
//        T[][] values = mm.getValues();
//
//        T[][] b1 = this.gaussPGkrok1(values, b, clazz);
//        T[][] result = this.gaussKrok2(values, b, clazz);
//
//        return result;
//    }
//
//    private T[][] gaussPGkrok1(T[][] values, T[][] b, Class<T> clazz){
//
//        T[][] b1 = b;
//        for (int k = 0; k < values.length-1; k++){
//            int p = maxCol(values, k);
//            if (k != p){
//                //zamień miejscami
//                T[] rowK = values[k];
//                T[] rowP = values[p];
//                values[k] = rowP;
//                values[p] = rowK;
//                T temp_bk = b1[k][0];
//                b1[k][0] = b1[p][0];
//                b1[p][0] = temp_bk;
//            }
//            b1 = this.k1(values, b,clazz, k+1);
//        }
//
//        return b1;
//    }
//
//    private T[][] gaussKrok2(T[][] values, T[][] b, Class<T> clazz){
//        T[][] x = (T[][]) Array.newInstance(clazz, b.length, 1);
//        int i = values.length;
//        int j = values[0].length;
//        double Bn = b[b.length-1][0].doubleValue();
//        double Ann = values[i-1][j-1].doubleValue();
//        //Xn
//        x[x.length-1][0] = convertToType(clazz, String.valueOf(Bn / Ann));
//        for (int k = x.length-2; k >= 0 ; k--){
//
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
//        return x;
//    }
//
//    private int maxCol(T[][] values, int col){
//        int maxCol = col;
//        double max = values[0][0].doubleValue();
//        for (int i = col; i < values[0].length; i++){
//            if (Math.abs(values[i][col].doubleValue()) > max){
//                maxCol = i;
//            }
//        }
//
//        return maxCol;
//    }
//
//
//
//    private T[][] k1(T[][] values, T[][] b, Class<T> clazz, int k){
//        Double[] m = new Double[values.length -k];
//
//        for (int i = 0; i < m.length; i++){
//            m[i] = values[i+k][k-1].doubleValue() / values[k-1][k-1].doubleValue();
//        }
//
//        int mi = 0;
//        for (int i = k; i < values.length; i++){
//            for (int j = k-1; j < values[0].length; j++){
//                Double val = values[i][j].doubleValue() -
//                        (values[k-1][j].doubleValue() * m[mi]);
//                T val_t = convertToType(clazz, String.valueOf(val));
//                values[i][j] = val_t;
//            }
//
//            Double valB = b[i][0].doubleValue() - (m[mi] * b[k-1][0].doubleValue());
//            b[i][0] = convertToType(clazz, String.valueOf(valB));
//
//            mi++;
//        }
//
//        return b;
//    }
//
//    private T convertToType(Class<T> clazz,String str) {
//        try {
//            return clazz.getConstructor(String.class).newInstance(str);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//    // https://stackoverflow.com/a/36804604/10476860
//}
