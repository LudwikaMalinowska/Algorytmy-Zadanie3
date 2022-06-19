package tests;

import com.opencsv.CSVWriter;
import javafx.util.Pair;
import models.TestResult;
import utils.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SeidelTest {
    public static void main(String[] args) {
        make3csv();
        makecsv_seidel_montecarlo();
        System.out.println("------------------csv4 montecarlo Seidel Done ------------");
        JacobiTest.makecsv_jacobi_montecarlo();
        System.out.println("------------------csv4 montecarlo Jacobi Done ------------");
    }



    public static void make3csv() {
        csv_with_acc(1e-6, "SeidelResults_1e-6_final.csv");
        System.out.println("------------------Seidel csv1 Done ------------");
        csv_with_acc(1e-10, "SeidelResults_1e-10_final.csv");
        System.out.println("------------------Seidel csv2 Done ------------");
        csv_with_acc(1e-14, "SeidelResults_1e-14_final.csv");
        System.out.println("------------------Seidel csv3 Done ------------");
    }

    public static String[] monteCarloLine(int i, int iters, double accuracy){
        TestResult<Float> testResultGaussFloat =
                solveMatrixJacobiAndCountErrors_montecarlo(i,new Float(1),iters, (float) accuracy);
        TestResult<Double> testResultGaussDouble =
                solveMatrixJacobiAndCountErrors_montecarlo(i,new Double(1),iters, accuracy);

        long totalTime = testResultGaussFloat.getTime() + testResultGaussDouble.getTime();
        System.out.println(String.format("%s, %s", i, totalTime));
        String[] linia = new String[]{
                i + "",
                testResultGaussFloat.getError() + "",
                testResultGaussFloat.getTime() + "",
                testResultGaussDouble.getError() + "",
                testResultGaussDouble.getTime() + "",

        };

        return linia;
    }

    public static void makecsv_seidel_montecarlo() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("Seidel_montecarlo.csv", false),
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] naglowki = new String[]{
                    "i",
                    "MaxErrorSeidelFloat",
                    "TimeofSeidelFloat",
                    "MaxErrorSeidelDouble",
                    "TimeofSeidelDouble"

            };
            writer.writeNext(naglowki);

            String[] linia1 = monteCarloLine(10, 100, 1e-6);
            String[] linia2 = monteCarloLine(20, 500, 1e-10);
            String[] linia3 = monteCarloLine(30, 1000, 1e-14);
            writer.writeNext(linia1);
            writer.writeNext(linia2);
            writer.writeNext(linia3);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void csv_with_acc(Double accuracy, String filename){
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename, false),
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] naglowki = new String[]{
                    "i",
                    "MaxErrorSeidelFloat",
                    "TimeofSeidelFloat",
                    "MaxErrorSeidelDouble",
                    "TimeofSeidelDouble"

            };
            writer.writeNext(naglowki);
            for (int i = 25; i <=100; i += 25) {
                TestResult<Float> testResultGaussFloat =
                        solveMatrixJacobiAndCountErrors(i,new Float(1),1000, accuracy.floatValue());
                TestResult<Double> testResultGaussDouble =
                        solveMatrixJacobiAndCountErrors(i,new Double(1),1000, accuracy);

                long totalTime = testResultGaussFloat.getTime() + testResultGaussDouble.getTime();
                System.out.println(String.format("%s, %s", i, totalTime));
                String[] linia = new String[]{
                        i + "",
                        testResultGaussFloat.getError() + "",
                        testResultGaussFloat.getTime() + "",
                        testResultGaussDouble.getError() + "",
                        testResultGaussDouble.getTime() + "",

                };
                writer.writeNext(linia);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Number> T[][] solveMatrixSeidel(int numberOfAllVoters,T classSample, T dokladnosc){
        ArrayList<Pair<Integer, Integer>> votesOptions= GenerateMatrix.createVotesOptions(numberOfAllVoters);
        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
//        T [][] gaussResult= StaticGauss.solveGaussPG(matrix, vector, classSample);
        T[][] gaussResult= Seidel.countSeidel(matrix, vector, dokladnosc);
        return gaussResult;
    }

    public static <T extends Number> TestResult<T> solveMatrixJacobiAndCountErrors(int numberOfAllVoters,T classSample,
                                                                                   int numbersOfIterations, T dokladnosc){
        long start = System.currentTimeMillis();
        T[][] resultFromGauss= solveMatrixSeidel(numberOfAllVoters,classSample, dokladnosc);
        long end = System.currentTimeMillis();
        long timeGaussPG= end - start;
//        T[][] resultFromMonteCarlo= MatrixSolvers.simulationResultsMatrix(classSample,numberOfAllVoters,numbersOfIterations);
//        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);

        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
        MojaMacierz<T> mojaMacierz = new MojaMacierz<>(matrix, (Class<T>) classSample.getClass());
        MojaMacierz<T> mojResult = new MojaMacierz<>(resultFromGauss, (Class<T>) classSample.getClass());
        T[][] compare = mojaMacierz.multiply(mojResult).getValues();

//        T error = GenericMatrixUtils.testError(resultFromGauss,resultFromMonteCarlo);
        T error = GenericMatrixUtils.testError(vector,compare);
        return new TestResult<T>(error,timeGaussPG);
    }

    public static <T extends Number> TestResult<T> solveMatrixJacobiAndCountErrors_montecarlo(int numberOfAllVoters,T classSample,
                                                                                   int numbersOfIterations, T dokladnosc){
        long start = System.currentTimeMillis();
        T[][] resultFromGauss= solveMatrixSeidel(numberOfAllVoters,classSample, dokladnosc);
        long end = System.currentTimeMillis();
        long timeGaussPG= end - start;
        T[][] resultFromMonteCarlo= MatrixSolvers.simulationResultsMatrix(classSample,numberOfAllVoters,numbersOfIterations);
//        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);


        T error = GenericMatrixUtils.testError(resultFromGauss,resultFromMonteCarlo);
        return new TestResult<T>(error,timeGaussPG);
    }
}
