package tests;

import com.opencsv.CSVWriter;
import javafx.util.Pair;
import models.TestResult;
import utils.GenerateMatrix;
import utils.GenericMatrixUtils;
import utils.Jacobi;
import utils.Seidel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SeidelTest {
    public static void main(String[] args) {

    }

    public static void make3csv() {
        csv_with_acc(1e-6, "SeidelResults_1e-6.csv");
        System.out.println("------------------Seidel csv1 Done ------------");
        csv_with_acc(1e-10, "SeidelResults_1e-10.csv");
        System.out.println("------------------Seidel csv2 Done ------------");
        csv_with_acc(1e-14, "SeidelResults_1e-14.csv");
        System.out.println("------------------Seidel csv3 Done ------------");
    }

    public static void csv_with_acc(Double accuracy, String filename){
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(filename, false),
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] naglowki = new String[]{
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
        T[][] resultFromMonteCarlo= MatrixSolvers.simulationResultsMatrix(classSample,numberOfAllVoters,numbersOfIterations);
//        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);
        T error = GenericMatrixUtils.testError(resultFromGauss,resultFromMonteCarlo);
        return new TestResult<T>(error,timeGaussPG);
    }
}
