package tests;

import javafx.util.Pair;
import models.Fraction;
import org.ejml.data.DMatrixSparseCSC;
import utils.FractionUtils;
import utils.GenerateMatrix;
import utils.GenericMatrixUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static utils.GenericNumberUtils.*;

public class simpleTests {
    public static void main(String[] args) {
//        Double[][] finalMatrix = GenerateMatrix.createFinalMatrix(0d, 6);
        Fraction [][] finalMatrixFraction = GenerateMatrix.createFinalMatrix(new Fraction(BigInteger.ONE,BigInteger.ONE),6);
        prettyPrintFraction(finalMatrixFraction);
        Fraction [] finalVectorFraction = GenerateMatrix.createFinalVector(new Fraction(BigInteger.ONE,BigInteger.ONE),6);
        System.out.println(Arrays.toString(finalVectorFraction));
//        prettyPrint(finalMatrix);
//        Double[] finalVector= GenerateMatrix.createFinalVector(0d, 6);
//        System.out.println(Arrays.toString(finalVector));
    }

    private static void prettyPrint(Double[][] array){
        for (Double[] x : array)
        {
            for (Double y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }


    private static void prettyPrintFraction(Fraction[][] array){
        for (Fraction[] x : array)
        {
            for (Fraction y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }



    public static void SparseMatrix() {
        DMatrixSparseCSC sparseMatrix = new DMatrixSparseCSC(5, 5);
        sparseMatrix.set(2, 2, 5d);
        sparseMatrix.print();
    }

    public static void GaussTest() {


    }

    public static void FractionTest() {
        Fraction half = new Fraction(BigInteger.valueOf(-4), BigInteger.valueOf(8));
        System.out.println(half);

        Fraction different_half = new Fraction(BigInteger.valueOf(5), BigInteger.valueOf(10));
        System.out.println(different_half);
        System.out.println(FractionUtils.multiply(half, different_half));
        Fraction one = new Fraction(BigInteger.valueOf(10), BigInteger.valueOf(10));
        System.out.println(FractionUtils.add(different_half, one));
        System.out.println(FractionUtils.minus(different_half, one));
        System.out.println(FractionUtils.div(one, different_half));
        System.out.println(FractionUtils.isGreater(different_half, half));
    }

}
