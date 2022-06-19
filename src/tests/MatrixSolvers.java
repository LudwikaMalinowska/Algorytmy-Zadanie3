package tests;

import MonteCarlo.MonteCarloMethod;
import javafx.util.Pair;
import models.TestResult;
import utils.*;

import java.util.ArrayList;

import static utils.GenerateMatrix.createVotesOptions;

public class MatrixSolvers {
//    public static <T extends Number> T[][] solveMatrixGaussPg(int numberOfAllVoters,T classSample){
//        ArrayList<Pair<Integer, Integer>> votesOptions=GenerateMatrix.createVotesOptions(numberOfAllVoters);
//        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
//        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
//        T[][] gaussResult= StaticGauss.solveGaussPG(matrix, vector, classSample);
//        T[][] anotherResult= GenericVectorUtils.multiplyByVector(matrix,gaussResult,classSample);
//        return gaussResult;
//    }


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

    public static <T extends Number> TestResult<T> solveMatrixGaussPgAndCountErrors(int numberOfAllVoters,T classSample, int numbersOfIterations){
        ArrayList<Pair<Integer, Integer>> votesOptions=GenerateMatrix.createVotesOptions(numberOfAllVoters);
        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
        long start = System.currentTimeMillis();
        T[][] gaussResult= StaticGauss.solveGaussPG(matrix, vector, classSample);
            long end = System.currentTimeMillis();
            long timeGaussPG= end - start;
            T[][] anotherResult= GenericVectorUtils.multiplyByVector(matrix,gaussResult,classSample);
            T[][] resultFromMonteCarlo= simulationResultsMatrix(classSample,numberOfAllVoters,numbersOfIterations);
//        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);
//        T error = GenericMatrixUtils.testError(gaussResult,resultFromMonteCarlo);
        T error = GenericMatrixUtils.testError(vector,anotherResult);
        return new TestResult<T>(error,timeGaussPG);
    }

//    public static <T extends Number> TestResult<Double> solveMatrixGaussPgAndCountErrors_d(int numberOfAllVoters,T classSample, int numbersOfIterations){
//        long start = System.currentTimeMillis();
//        T[][] resultFromGauss= solveMatrixGaussPg(numberOfAllVoters,classSample);
//        long end = System.currentTimeMillis();
//        long timeGaussPG= end - start;
//        T[][] resultFromMonteCarlo= simulationResultsMatrix(classSample,numberOfAllVoters,numbersOfIterations);
////        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);
//        Double error = GenericMatrixUtils.testError_d(resultFromGauss,resultFromMonteCarlo);
//        return new TestResult<Double>(error,timeGaussPG);
//    }


}
