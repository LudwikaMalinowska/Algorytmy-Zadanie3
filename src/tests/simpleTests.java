package tests;

import MonteCarlo.MonteCarloMethod;
import com.opencsv.CSVWriter;
import models.Fraction;
import models.TestResult;
import org.ejml.data.DMatrixSparseCSC;
import utils.AnotherGauss;
import utils.FractionUtils;
import utils.GenerateMatrix;
import utils.StaticGauss;

import java.io.FileWriter;
import java.io.IOException;
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

//        Double[] testVector = GenerateMatrix.createFinalVector(0d, 3);
       //Double[][] result = MatrixSolvers.solveMatrixGaussPg(3,2,6,0d);
//       Fraction result = MatrixSolvers.solveMatrixGaussPg(2,2,7,new Fraction(BigInteger.ONE,BigInteger.ONE));
        //TestResult<Fraction> result = MatrixSolvers.solveMatrixGaussPgAndCountErrors(7,new Fraction(BigInteger.ONE,BigInteger.ONE),100,100);

       // System.out.println(result);
//        System.out.println("RESULTS");
//        prettyPrint(result);

//        MonteCarloMethod monteCarlo = new MonteCarloMethod(2,12,20,10000);
//        System.out.println(monteCarlo.simulation());

//        try {
//            CSVWriter writer = new CSVWriter(new FileWriter("GaussPgResults.csv", false),
//                    ';',
//                    CSVWriter.NO_QUOTE_CHARACTER,
//                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
//                    CSVWriter.DEFAULT_LINE_END);
//
//            String[] naglowki = new String[]{
//                    "MaxErrorGaussFloat",
//                    "TimeofGaussFloat",
//                    "MaxErrorGaussDouble",
//                    "TimeofGaussDouble"
//
//            };
//            writer.writeNext(naglowki);
//            for (int i = 25; i <=100; i += 25) {
//                TestResult<Float> testResultGaussFloat = MatrixSolvers.solveMatrixGaussPgAndCountErrors(i,new Float(1),1000);
//                TestResult<Double> testResultGaussDouble = MatrixSolvers.solveMatrixGaussPgAndCountErrors(i,new Double(1),1000);
//
//                long totalTime = testResultGaussFloat.getTime() + testResultGaussDouble.getTime();
//                System.out.println(String.format("%s, %s", i, totalTime));
//                String[] linia = new String[]{
//                        testResultGaussFloat.getError() + "",
//                        testResultGaussFloat.getTime() + "",
//                        testResultGaussDouble.getError() + "",
//                        testResultGaussDouble.getTime() + "",
//
//                };
//                writer.writeNext(linia);
//            }
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            CSVWriter writer = new CSVWriter(new FileWriter("GaussPgResultsFraction.csv", false),
//                    ';',
//                    CSVWriter.NO_QUOTE_CHARACTER,
//                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
//                    CSVWriter.DEFAULT_LINE_END);
//
//            String[] naglowki = new String[]{
//                    "MaxErrorGaussFraction",
//                    "TimeofGaussFloat",
//
//
//            };
//            writer.writeNext(naglowki);
//            for (int i = 10; i <=40; i += 10) {
//                TestResult<Fraction> testResultGaussFraction = MatrixSolvers.solveMatrixGaussPgAndCountErrors(i,new Fraction(BigInteger.ONE,BigInteger.ONE),1000);
//
//
//                long totalTime = testResultGaussFraction.getTime();
//                System.out.println(String.format(" Fraction total time %s, %s", i, totalTime));
//                String[] linia = new String[]{
//                        testResultGaussFraction.getError() + "",
//                        testResultGaussFraction.getTime() + "",
//
//
//                };
//                writer.writeNext(linia);
//            }
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//





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
