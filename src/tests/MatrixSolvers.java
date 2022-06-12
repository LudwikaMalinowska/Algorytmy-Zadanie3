package tests;

import MonteCarlo.MonteCarloMethod;
import javafx.util.Pair;
import utils.GenerateMatrix;
import utils.GenericNumberUtils;
import utils.StaticGauss;

import java.util.ArrayList;

public class MatrixSolvers {
    public static <T extends Number> T solveMatrixGaussPg(int NumberOfY,int NumberOfN,int numberOfAllVoters,T classSample){
        ArrayList<Pair<Integer, Integer>> votesOptions=GenerateMatrix.createVotesOptions(numberOfAllVoters);
        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
        T [][] gaussResult= StaticGauss.solveGaussPG(matrix, vector, classSample);
        int y = votesOptions.indexOf(new Pair<>( NumberOfY,  NumberOfN));
        return gaussResult[y][0];
    }

    public static <T extends Number> T solveMatrixGaussPgAndCountErrors(int numberOfY,int numberOfN,int numberOfAllVoters,T classSample, int numbersOfIterations){
        T resultFromGauss= solveMatrixGaussPg(numberOfY,numberOfN,numberOfAllVoters,classSample);
        MonteCarloMethod monteCarlo = new MonteCarloMethod(numberOfY,numberOfN,numberOfAllVoters,numbersOfIterations);
        T resultFromMonteCarlo= monteCarlo.simulation(classSample);
        return GenericNumberUtils.abs(GenericNumberUtils.substract(resultFromGauss,resultFromMonteCarlo));
    }


}
