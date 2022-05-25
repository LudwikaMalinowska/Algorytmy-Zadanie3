package utils;

import models.Fraction;

import java.math.BigInteger;

public class GenericNumberUtils {
    public static <T extends Number> T sum(final T number1, final T number2) {
        if (number1 instanceof Double && number2 instanceof Double) {
            return (T) Double.valueOf(number1.doubleValue() + number2.doubleValue());
        } else if (number1 instanceof Float && number2 instanceof Float) {
            return (T) Float.valueOf(number1.floatValue() + number2.floatValue());
        } else if (number1 instanceof Fraction && number2 instanceof Fraction) {
            return (T) FractionUtils.add(((Fraction) number1), ((Fraction) number2));
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }

    public static <T extends Number> T subtract(final T number1, final T number2) {
        if (number1 instanceof Double && number2 instanceof Double) {
            return (T) Double.valueOf(number1.doubleValue() - number2.doubleValue());
        } else if (number1 instanceof Float && number2 instanceof Float) {
            return (T) Float.valueOf(number1.floatValue() - number2.floatValue());
        } else if (number1 instanceof Fraction && number2 instanceof Fraction) {
            return (T) FractionUtils.minus(((Fraction) number1), ((Fraction) number2));
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }

    public static <T extends Number> T multiply(final T number1, final T number2) {
        if (number1 instanceof Double && number2 instanceof Double) {
            return (T) Double.valueOf(number1.doubleValue() * number2.doubleValue());
        } else if (number1 instanceof Float && number2 instanceof Float) {
            return (T) Float.valueOf(number1.floatValue() * number2.floatValue());
        } else if (number1 instanceof Fraction && number2 instanceof Fraction) {
            return (T) FractionUtils.multiply(((Fraction) number1), ((Fraction) number2));
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }

    public static <T extends Number> T divide(final T number1, final T number2) {
        if (number1 instanceof Double && number2 instanceof Double) {
            return (T) Double.valueOf(number1.doubleValue() / number2.doubleValue());
        } else if (number1 instanceof Float && number2 instanceof Float) {
            return (T) Float.valueOf(number1.floatValue() / number2.floatValue());
        } else if (number1 instanceof Fraction && number2 instanceof Fraction) {
            return (T) FractionUtils.div(((Fraction) number1), ((Fraction) number2));
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }

    public static <T extends Number> boolean isGreater(final T number1, final T number2) {
        if (number1 instanceof Double && number2 instanceof Double) {
            return number1.doubleValue() > number2.doubleValue();
        } else if (number1 instanceof Float && number2 instanceof Float) {
            return number1.floatValue() > number2.floatValue();
        } else if (number1 instanceof Fraction && number2 instanceof Fraction) {
            return FractionUtils.isGreater(((Fraction) number1), ((Fraction) number2));
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }

    public static <T extends Number> T getZero(final T classSample) {
        if (classSample instanceof Double) {
            return (T) Double.valueOf(0);
        } else if (classSample instanceof Float) {
            return (T) Float.valueOf(0);
        } else if (classSample instanceof Fraction) {
            return (T) new Fraction(BigInteger.ZERO, BigInteger.ONE);
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }

    public static <T extends Number> T abs(final T number) {
        if (number instanceof Double) {
            return (T) new Double(Math.abs((double) number));
        } else if (number instanceof Float) {
            return (T) new Float(Math.abs((float) number));
        } else if (number instanceof Fraction) {
            return (T) ((Fraction) number).abs();
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }

    public static <T extends Number> T clone(final T value) {
        if (value instanceof Double) {
            return (T) new Double((Double) value);
        } else if (value instanceof Float) {
            return (T) new Float((Float) value);
        } else if (value instanceof Fraction) {
            Fraction fractionValue = (Fraction) value;
            return (T) new Fraction(fractionValue.getNominator(), fractionValue.getDenominator());
        } else {
            throw new IllegalArgumentException("Numbers must be exact type and float or double");
        }
    }
}
