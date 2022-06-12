package tests;

import utils.GenerateMatrix;
import utils.StaticGauss;

public class MatrixSolvers {
    public static <T extends Number> T[][] solveMatrixGaussPg(int NumberOfY,int NumberOfN,int numberOfAllVoters,T classSample){
        T[][] matrix = GenerateMatrix.createFinalMatrix(classSample, numberOfAllVoters);
        T[][] vector = GenerateMatrix.createFinalVector(classSample,numberOfAllVoters);
        T [][] gaussResult= StaticGauss.solveGaussPG(matrix, vector, classSample);
        return gaussResult;

    }
}
