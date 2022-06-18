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
//                Double[][] finalMatrix = GenerateMatrix.createFinalMatrix(0d, 3);
//        simpleTests.prettyPrint(finalMatrix);

//        System.out.println("\nJacobi\n");
//        Double[][] matrix = GenerateMatrix.createFinalMatrix(1d, 3);
//        Double[][] vector = GenerateMatrix.createFinalVector(1d,3);
//        Double[][] gaussResult= Jacobi.countJacobi(matrix, vector);
//
//        simpleTests.prettyPrint(gaussResult);
//
//        System.out.println("\nSeidel\n");
//        Double[][] matrix2 = GenerateMatrix.createFinalMatrix(1d, 3);
//        Double[][] vector2 = GenerateMatrix.createFinalVector(1d,3);
//        Double[][] gaussResult2= Seidel.countSeidel(matrix2, vector2);
//
//        simpleTests.prettyPrint(gaussResult2);

        try {
            CSVWriter writer = new CSVWriter(new FileWriter("JacobiResults.csv", false),
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] naglowki = new String[]{
                    "MaxErrorJacobiFloat",
                    "TimeofJacobiFloat",
                    "MaxErrorJacobiDouble",
                    "TimeofJacobiDouble"

            };
            writer.writeNext(naglowki);
            for (int i = 25; i <=100; i += 25) {
                TestResult<Float> testResultGaussFloat =
                        solveMatrixJacobiAndCountErrors(i,new Float(1),1000, 1E-6f);
                TestResult<Double> testResultGaussDouble =
                       solveMatrixJacobiAndCountErrors(i,new Double(1),1000, 1E-6d);

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
