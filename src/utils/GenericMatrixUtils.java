package utils;

import models.Fraction;

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

    public static <T extends Number> T[][] cloneMatrix(final T[][] values) {
        final int length = values.length;
        T[][] clonedValues = createMatrix(values[0][0], length);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
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
}
