package models;

import java.math.BigInteger;

public class Fraction extends Number {
    private final BigInteger nominator;
    private final BigInteger denominator;

    public BigInteger getNominator() {
        return nominator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public Fraction(BigInteger nominator, BigInteger denominator) {
        if (nominator.toString().equals("NaN") || denominator.toString().equals("NaN")) throw new RuntimeException("NaN");

        if (nominator.equals(BigInteger.ZERO)) {
            denominator = BigInteger.ONE;
        }

        if (nominator.compareTo(BigInteger.ZERO) < 0 && denominator.compareTo(BigInteger.ZERO) < 0) {
            nominator = nominator.abs();
            denominator = denominator.abs();
        } else if (nominator.compareTo(BigInteger.ZERO) >= 0 && denominator.compareTo(BigInteger.ZERO) < 0) {
            nominator = nominator.negate();
            denominator = denominator.abs();
        }

        // fraction reduction
        if (!nominator.equals(BigInteger.ZERO)) {
            BigInteger nwd;
            do {
                nwd = nwd(nominator.abs(), denominator);
                nominator = nominator.divide(nwd);
                denominator = denominator.divide(nwd);
            } while (!nwd.equals(BigInteger.ONE));
        }

        this.nominator = nominator;
        this.denominator = denominator;
    }

    @Override
    public String toString() {
        return String.format("%s/%s", getNominator(), getDenominator());
    }

    @Override
    public int intValue() {
        return getNominator().divide(getDenominator()).intValue();
    }

    @Override
    public long longValue() {
        return getNominator().divide(getDenominator()).longValue();
    }

    @Override
    public float floatValue() {
        return getNominator().divide(getDenominator()).floatValue();
    }

    @Override
    public double doubleValue() {
        return getNominator().divide(getDenominator()).doubleValue();
    }

    private BigInteger nwd(BigInteger first, BigInteger second) {
        BigInteger newFirst = new BigInteger(first.toString());
        BigInteger newSecond = new BigInteger(second.toString());
        while (!newSecond.equals(BigInteger.ZERO)){
            BigInteger pom = new BigInteger((newSecond.toString()));
            newSecond = newFirst.mod(newSecond);
            newFirst = pom;
        }
        return newFirst;
    }

    public Fraction abs() {
        return new Fraction(nominator.abs(), denominator);
    }
}
