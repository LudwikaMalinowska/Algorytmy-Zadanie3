package utils;

import models.Fraction;

public class GenericVectorUtils {
    public static <T extends Number> T[] getZerosVector(final T classSample, final int length) {
        final T[] clonedValues = createVector(classSample, length);
        for (int i = 0; i < length; i++) {
            clonedValues[i] = GenericNumberUtils.getZero(classSample);
        }
        return clonedValues;
    }

    public static <T extends Number> T[] cloneVector(final T[] values) {
        int length = values.length;
        T[] clonedValues = createVector(values[0], length);
        for (int i = 0; i < length; i++) {
            clonedValues[i] = GenericNumberUtils.clone(values[i]);
        }
        return clonedValues;
    }

    private static <T extends Number> T[] createVector(final T classSample, final int length) {
        if (classSample instanceof Double) {
            return (T[]) new Double[length];
        } else if (classSample instanceof Float) {
            return (T[]) new Float[length];
        } else if (classSample instanceof Fraction) {
            return (T[]) new Fraction[length];
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }
}
