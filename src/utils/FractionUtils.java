package utils;

import models.Fraction;

import java.math.BigInteger;

public class FractionUtils {
    public static Fraction multiply(final Fraction first, final Fraction second) {
        final BigInteger nominator = first.getNominator().multiply(second.getNominator());
        final BigInteger denominator = first.getDenominator().multiply(second.getDenominator());
        return new Fraction(nominator, denominator);
    }

    public static Fraction div(final Fraction first, final Fraction second) {
        final Fraction reversedSecond = new Fraction(second.getDenominator(), second.getNominator());
        return multiply(first, reversedSecond);
    }

    public static Fraction add(final Fraction first, final Fraction second) {
        // (a*d + b*c)
        final BigInteger ad = first.getNominator().multiply(second.getDenominator());
        final BigInteger bc = second.getNominator().multiply(first.getDenominator());
        final BigInteger new_nominator = ad.add(bc);
        // b*d
        final BigInteger new_denominator = first.getDenominator().multiply(second.getDenominator());
        return new Fraction(new_nominator, new_denominator);
    }

    public static Fraction minus(final Fraction first, final Fraction second) {
        final Fraction newFraction = new Fraction(second.getNominator().negate(), second.getDenominator());
        return add(first, newFraction);
    }

    public static boolean isGreater(final Fraction first, final Fraction second) {
        // first minus, second minus
        if (first.getNominator().compareTo(BigInteger.ZERO) < 0
                && second.getNominator().compareTo(BigInteger.ZERO) < 0) {
            final Fraction result = minus(first, second);
            return result.getNominator().compareTo(BigInteger.ZERO) < 0;
            // first plus, second minus
        } else if (first.getNominator().compareTo(BigInteger.ZERO) >= 0
                && second.getNominator().compareTo(BigInteger.ZERO) < 0) {
            return true;
            // first plus, second plus
        } else if (first.getNominator().compareTo(BigInteger.ZERO) >= 0
                && second.getNominator().compareTo(BigInteger.ZERO) >= 0) {
            final Fraction result = minus(first, second);
            return result.getNominator().compareTo(BigInteger.ZERO) > 0;
            // first minus, second plus
        } else {
            return false;
        }
    }

    public static boolean isEqual(final Fraction first, final Fraction second) {
        return first.getDenominator().equals(second.getDenominator())
                && first.getNominator().equals(second.getNominator());
    }
}
