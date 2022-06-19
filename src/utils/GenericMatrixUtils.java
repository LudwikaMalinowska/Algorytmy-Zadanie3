package utils;

import models.Fraction;

import java.util.Arrays;

import static utils.GenericNumberUtils.isGreater;

public class GenericMatrixUtils {
    public static <T extends Number> T[][] getZerosMatrix(final T classSample,
                                                          final int length) {
        final T[][] clonedValues = createMatrix(classSample, length);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                clonedValues[i][j] = GenericNumberUtils.getZero(classSample);
            }
        }
        return clonedValues;
    }

    public static <T extends Number> T[][] getZerosMatrix(final T classSample,
                                                          final int width,
                                                          final int length) {
        final T[][] clonedValues = createMatrix(classSample, length, width);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                clonedValues[i][j] = GenericNumberUtils.getZero(classSample);
            }
        }
        return clonedValues;
    }

    public static <T extends Number> T[][] cloneMatrix(final T[][] values) {
        final int length = values.length;
        final int length2 = values[0].length;
        T[][] clonedValues = createMatrix(values[0][0], length2, length);

        for (int i = 0; i < clonedValues.length; i++) {
            for (int j = 0; j < clonedValues[0].length; j++) {
                clonedValues[i][j] = GenericNumberUtils.clone(values[i][j]);
            }
        }
        return clonedValues;
    }

    private static <T extends Number> T[][] createMatrix(final T classSample,
                                                         final int length) {
        if (classSample instanceof Double) {
            return (T[][]) new Double[length][length];
        } else if (classSample instanceof Float) {
            return (T[][]) new Float[length][length];
        } else if (classSample instanceof Fraction) {
            return (T[][]) new Fraction[length][length];
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }

    private static <T extends Number> T[][] createMatrix(final T classSample,
                                                         final int length,
                                                         final int width) {
        if (classSample instanceof Double) {
            return (T[][]) new Double[width][length];
        } else if (classSample instanceof Float) {
            return (T[][]) new Float[width][length];
        } else if (classSample instanceof Fraction) {
            return (T[][]) new Fraction[width][length];
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }

    private static <T extends Number> T[][] substract(T[][] matrix1, T[][] matrix2) {
        T[][] resultVector = getZerosMatrix(matrix1[0][0],matrix1.length,1);
        for (int i = 0; i < matrix1.length; i++) {
//            System.out.println("matrix1[i][0]: " + matrix1[i][0]);
//            System.out.println("matrix2[i][0]: " + matrix2[i][0]);
            resultVector[i][0] = GenericNumberUtils.substract(matrix1[i][0],matrix2[i][0]);
//            System.out.println(i);
            if (resultVector[i][0].equals(Float.NaN)){
                System.out.println("NaN");
                System.exit(0);
            }

        }
        return resultVector;
    }




    private static <T extends Number> T[][] substractAbs(T[][] matrix1, T[][] matrix2) {
        T[][] resultVector = getZerosMatrix(matrix1[0][0],matrix1.length,1);
        for (int i = 0; i < matrix1.length; i++) {
            System.out.println("matrix1[i][0]: " + matrix1[i][0]);
            System.out.println("matrix2[i][0]: " + matrix2[i][0]);
            resultVector[i][0] = GenericNumberUtils.substract(GenericNumberUtils.abs(matrix1[i][0]),GenericNumberUtils.abs(matrix2[i][0]));
            System.out.println(i);
            if (resultVector[i][0].equals(Float.NaN)){
                System.out.println("NaN");
                System.exit(0);
            }

        }
        return resultVector;
    }









    public static <T extends Number> T testError(T[][] matrix1, T[][] matrix2) {
        T[][] errors = substractAbs(matrix1,matrix2);
        for (int i = 0; i < errors.length; i++) {
            errors[i][0] = GenericNumberUtils.abs(errors[i][0]);
        }
        T maxElement = errors[0][0];
        for (int i = 0; i < errors.length; i++) {
            if (!errors[i][0].equals(Float.NaN)){
                maxElement = errors[i][0];
                break;
            }
        }

        for (T[] error : errors) {
            if (!error[0].equals(Float.NaN) && isGreater(error[0], maxElement)) {
                maxElement = error[0];
            }
        }
        return maxElement;
    }

    public static <T extends Number> Double testError_d(T[][] matrix1, T[][] matrix2) {
        Double[][] m1 = getZerosMatrix(1d,matrix1.length,1);
        Double[][] m2 = getZerosMatrix(1d,matrix1.length,1);
//        T[][] errors = substract(matrix1,matrix2);
        for (int i = 0; i < matrix1.length; i++){
            for (int j = 0; j < matrix1[0].length; j++){
                m1[i][j] = Jacobi.convertToType(Double.class,
                        String.valueOf(matrix1[i][j].doubleValue()));
            }
        }
        for (int i = 0; i < matrix2.length; i++){
            for (int j = 0; j < matrix2[0].length; j++){
                m2[i][j] = Jacobi.convertToType(Double.class,
                        String.valueOf(matrix2[i][j].doubleValue()));
            }
        }


        Double[][] errors = substract(m1,m2);
        for (int i = 0; i < errors.length; i++) {
            errors[i][0] = GenericNumberUtils.abs(errors[i][0]);
        }
        Double maxElement = errors[0][0];
        for (Double[] doubles : errors) {
            maxElement = doubles[0];
            break;
        }

        for (Double[] error : errors) {
            if (isGreater(error[0], maxElement)) {
                maxElement = error[0];
            }
        }
        return maxElement;
    }

}
