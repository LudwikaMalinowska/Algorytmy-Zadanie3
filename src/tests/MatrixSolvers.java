package tests;

import MonteCarlo.MonteCarloMethod;
import javafx.util.Pair;
import utils.GenerateMatrix;
import utils.GenericMatrixUtils;
import utils.GenericNumberUtils;
import utils.StaticGauss;

import java.util.ArrayList;

import static utils.GenerateMatrix.createVotesOptions;

public class MatrixSolvers {
    public static <T extends Number> T[][] solveMatrixGaussPg(int NumberOfY,int NumberOfN,int numberOfAllVoters,T classSample){
        ArrayList<Pair<Integer, Integer>> votesOptions=GenerateMatrix.createVotesOptions(numberOfAllVoters);
        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
        T [][] gaussResult= StaticGauss.solveGaussPG(matrix, vector, classSample);
        return gaussResult;
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

    public static <T extends Number> T solveMatrixGaussPgAndCountErrors(int numberOfY,int numberOfN,int numberOfAllVoters,T classSample, int numbersOfIterations){
        T[][] resultFromGauss= solveMatrixGaussPg(numberOfY,numberOfN,numberOfAllVoters,classSample);
        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);
        T[][] resultFromMonteCarlo= simulationResultsMatrix(classSample,numberOfAllVoters,numbersOfIterations);
        return GenericMatrixUtils.testError(resultFromGauss,resultFromMonteCarlo);

    }


}
