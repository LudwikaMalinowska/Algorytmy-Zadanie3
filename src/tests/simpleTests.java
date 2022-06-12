package tests;

import MonteCarlo.MonteCarloMethod;
import models.Fraction;
import org.ejml.data.DMatrixSparseCSC;
import utils.AnotherGauss;
import utils.FractionUtils;
import utils.GenerateMatrix;
import utils.StaticGauss;

import java.math.BigInteger;
import java.util.Arrays;

public class simpleTests {
    public static void main(String[] args) {
//        Double[][] finalMatrix = GenerateMatrix.createFinalMatrix(0d, 3);
//        prettyPrint(finalMatrix);
//        Fraction [][] finalMatrixFraction = GenerateMatrix.createFinalMatrix(new Fraction(BigInteger.ONE,BigInteger.ONE),6);
//        prettyPrintFraction(finalMatrixFraction);
        //    Fraction [][] finalVectorFraction = GenerateMatrix.createFinalVector(new Fraction(BigInteger.ONE,BigInteger.ONE),6);
//        System.out.println(Arrays.toString(finalVectorFraction));
//        Fraction [][] result=StaticGauss.solveGaussPG(finalMatrixFraction,finalVectorFraction);

//        Double[][] finalVector = GenerateMatrix.createFinalVector(0d, 3);

//        Double[][] test = {
//                {0d, 0d, 0d, 0d, 5d},
//                {0d, 0d, 0d, 0d, 0d},
//                {0d, 0d, 5d, 0d, 0d},
//                {0d, 2d, 0d, 5d, 0d},
//                {0d, 0d, 7d, 0d, 0d}
//        };
        Double[][] test2 = {
                {0d},
                {0d},
                {0d},
                {0d},
                {0d},
                {0d},
                {0d},
                {0d},{0d},{-1d}
        };
//        Double[] testVector = GenerateMatrix.createFinalVector(0d, 3);
       //Double[][] result = MatrixSolvers.solveMatrixGaussPg(3,2,6,0d);
       Fraction[][] result = MatrixSolvers.solveMatrixGaussPg(3,1,5,new Fraction(BigInteger.ONE,BigInteger.ONE));

//        System.out.println("RESULTS");
        prettyPrint(result);

//        MonteCarloMethod monteCarlo = new MonteCarloMethod(2,12,20,10000);
//        System.out.println(monteCarlo.simulation());
    }

    public static <T extends Number> void prettyPrint(T[][] array) {
        for (T[] x : array) {
            for (T y : x) {
                System.out.print(String.format("%.2f", y) + " ");
            }
            System.out.println();
        }
    }


    private static void prettyPrintFraction(Fraction[][] array) {
        for (Fraction[] x : array) {
            for (Fraction y : x) {
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
