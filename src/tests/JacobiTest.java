package tests;

import MonteCarlo.MonteCarloMethod;
import com.opencsv.CSVWriter;
import javafx.util.Pair;
import models.Fraction;
import models.TestResult;
import utils.*;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

import static utils.GenerateMatrix.createVotesOptions;

public class JacobiTest {
    public static void main(String[] args) {

    }
    public static void make_3csv() {

    csv_with_acc(1e-6, "JacobiResults_1e-6.csv");
        System.out.println("------------------Jacobi csv1 Done ------------");
    csv_with_acc(1e-10, "JacobiResults_1e-10.csv");
        System.out.println("------------------csv2 Done ------------");
    csv_with_acc(1e-14, "JacobiResults_1e-14.csv");
        System.out.println("------------------csv3 Done ------------");
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

    public static void makecsv_jacobi_montecarlo() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("Jacobi_montecarlo_final.csv", false),
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] naglowki = new String[]{
                    "i",
                    "MaxErrorJacobiFloat",
                    "TimeofJacobiFloat",
                    "MaxErrorJacobiDouble",
                    "TimeofJacobiDouble"

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
                    "MaxErrorJacobiFloat",
                    "TimeofJacobiFloat",
                    "MaxErrorJacobiDouble",
                    "TimeofJacobiDouble"

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

    public static <T extends Number> T[][] solveMatrixJacobi(int numberOfAllVoters,T classSample, T dokladnosc){
        ArrayList<Pair<Integer, Integer>> votesOptions=GenerateMatrix.createVotesOptions(numberOfAllVoters);
        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
//        T [][] gaussResult= StaticGauss.solveGaussPG(matrix, vector, classSample);
        T[][] gaussResult= Jacobi.countJacobi(matrix, vector, dokladnosc);
        return gaussResult;
    }

    public static <T extends Number> TestResult<T> solveMatrixJacobiAndCountErrors(int numberOfAllVoters,T classSample,
                                                                                   int numbersOfIterations, T dokladnosc){
        long start = System.currentTimeMillis();
        T[][] resultFromGauss= solveMatrixJacobi(numberOfAllVoters,classSample, dokladnosc);
        long end = System.currentTimeMillis();
        long timeGaussPG= end - start;
//        T[][] resultFromMonteCarlo= simulationResultsMatrix(classSample,numberOfAllVoters,numbersOfIterations);
//        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);

        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
        MojaMacierz<T> mojaMacierz = new MojaMacierz<>(matrix, (Class<T>) classSample.getClass());
        MojaMacierz<T> mojResult = new MojaMacierz<>(resultFromGauss, (Class<T>) classSample.getClass());
        T[][] compare = mojaMacierz.multiply(mojResult).getValues();

        T error = GenericMatrixUtils.testError(vector,compare);
        return new TestResult<T>(error,timeGaussPG);
    }

    public static <T extends Number> TestResult<T> solveMatrixJacobiAndCountErrors_montecarlo(int numberOfAllVoters,T classSample,
                                                                                   int numbersOfIterations, T dokladnosc){
        long start = System.currentTimeMillis();
        T[][] resultFromGauss= solveMatrixJacobi(numberOfAllVoters,classSample, dokladnosc);
        long end = System.currentTimeMillis();
        long timeGaussPG= end - start;
        T[][] resultFromMonteCarlo= simulationResultsMatrix(classSample,numberOfAllVoters,numbersOfIterations);
//        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);

        T error = GenericMatrixUtils.testError(resultFromGauss,resultFromMonteCarlo);
        return new TestResult<T>(error,timeGaussPG);
    }

    public static  <T extends Number> T[][] simulationResultsMatrix(T classSample, int numberOfVotes, int numberOfIterations){
        ArrayList<Pair<Integer, Integer>> listOfOptions = createVotesOptions(numberOfVotes);
        T[][] resultMatrix = GenericMatrixUtils.getZerosMatrix(classSample, listOfOptions.size(), 1);
        for ( int i=0; i< listOfOptions.size();i++){
            MonteCarloMethod monteCarlo = new MonteCarloMethod(listOfOptions.get(i).getKey(), listOfOptions.get(i).getValue(), numberOfVotes,numberOfIterations);
            T resultFromMonteCarlo= monteCarlo.simulation(classSample);
            resultMatrix[i][0]=resultFromMonteCarlo;
        }
        return resultMatrix;
    }
}
