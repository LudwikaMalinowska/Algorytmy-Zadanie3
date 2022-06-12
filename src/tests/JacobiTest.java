package tests;

import models.Fraction;
import utils.GenerateMatrix;
import utils.Jacobi;
import utils.Seidel;
import utils.StaticGauss;

import java.math.BigInteger;
import java.util.Arrays;

public class JacobiTest {
    public static void main(String[] args) {
                Double[][] finalMatrix = GenerateMatrix.createFinalMatrix(0d, 3);
        simpleTests.prettyPrint(finalMatrix);

        System.out.println("\nJacobi\n");
        Double[][] matrix = GenerateMatrix.createFinalMatrix(1d, 3);
        Double[][] vector = GenerateMatrix.createFinalVector(1d,3);
        Double[][] gaussResult= Jacobi.countJacobi(matrix, vector);

        simpleTests.prettyPrint(gaussResult);

        System.out.println("\nSeidel\n");
        Double[][] matrix2 = GenerateMatrix.createFinalMatrix(1d, 3);
        Double[][] vector2 = GenerateMatrix.createFinalVector(1d,3);
        Double[][] gaussResult2= Seidel.countSeidel(matrix2, vector2);

        simpleTests.prettyPrint(gaussResult2);

    }
}
