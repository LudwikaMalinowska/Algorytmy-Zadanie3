package tests;

import com.opencsv.CSVWriter;
import javafx.util.Pair;
import models.TestResult;
import utils.GenerateMatrix;
import utils.GenericMatrixUtils;
import utils.Seidel;
import utils.StaticGauss;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SparseTest {
    public static void main(String[] args) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("sparse_gauss.csv", false),
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] naglowki = new String[]{
                    "MaxErrorGaussSparseFloat",
                    "TimeofGaussSparseFloat",
                    "MaxErrorGaussSparseDouble",
                    "TimeofGaussSparseDouble"

            };
            writer.writeNext(naglowki);
            for (int i = 10; i <=40; i += 10) {
                TestResult<Float> testResultGaussFloat =
                        solveMatrixGaussSparseAndCountErrors(i,new Float(1),1000);
                TestResult<Double> testResultGaussDouble =
                        solveMatrixGaussSparseAndCountErrors(i,new Double(1),1000);

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

    public static <T extends Number> T[][] solveMatrixGaussSparse(int numberOfAllVoters,T classSample){
        ArrayList<Pair<Integer, Integer>> votesOptions= GenerateMatrix.createVotesOptions(numberOfAllVoters);
        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
        T [][] gaussResult= StaticGauss.solveGaussPG_sparse(matrix, vector, classSample);
        return gaussResult;
    }

    public static <T extends Number> TestResult<T> solveMatrixGaussSparseAndCountErrors(int numberOfAllVoters,T classSample, int numbersOfIterations){
        long start = System.currentTimeMillis();
        T[][] resultFromGauss= solveMatrixGaussSparse(numberOfAllVoters,classSample);
        long end = System.currentTimeMillis();
        long timeGaussPG= end - start;
        T[][] resultFromMonteCarlo= MatrixSolvers.simulationResultsMatrix(classSample,numberOfAllVoters,numbersOfIterations);
//        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);
        T error = GenericMatrixUtils.testError(resultFromGauss,resultFromMonteCarlo);
        return new TestResult<T>(error,timeGaussPG);
    }

    public static void test_example(){
        //        Double[][] a = {
//                {2.0, 4.0, 2.0, 0.0},
//                {0.0, -2.0, -2.0, 1.0},
//                {0.0, 1.0, 3.0, -1.0},
//                {0.0, -3.0, 0.0, 1.0}
//
//        };
//
//        Double[][] vector = {
//                {4.0},
//                {0.0},
//                {0.0},
//                {2.0},
//        };

        Double[][] a = {
                {2.0, 4.0, 2.0, 0.0},
                {0.0, -3.0, 0.0, 1.0},
                {0.0, 0.0, 3.0, -0.6666666},
                {0.0, 0.0, 0.0, -0.1111111}

        };

        Double[][] vector = {
                {4.0},
                {2.0},
                {0.66666666},
                {-0.8888888},
        };

        Double[][] g = StaticGauss.solveGaussPG_sparse(a, vector, a[0][0]);

        for (Double[] row : g) {
            System.out.println(Arrays.toString(row));
        }
    }
}
