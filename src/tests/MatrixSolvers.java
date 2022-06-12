package tests;

import javafx.util.Pair;
import utils.GenerateMatrix;
import utils.StaticGauss;

import java.util.ArrayList;

public class MatrixSolvers {
    public static <T extends Number> T solveMatrixGaussPg(int NumberOfY,int NumberOfN,int numberOfAllVoters,T classSample){
        ArrayList<Pair<Integer, Integer>> votesOptions=GenerateMatrix.createVotesOptions(numberOfAllVoters);
        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
        T [][] gaussResult= StaticGauss.solveGaussPG(matrix, vector, classSample);
        int y = votesOptions.indexOf(new Pair<>((int) NumberOfY, (int) NumberOfN));
        return gaussResult[y][0];
    }

//    public static <T extends Number> T[][] solveMatrixGaussPgAndCountErrors(int NumberOfY,int NumberOfN,int numberOfAllVoters,T classSample){
//
//    }


}
