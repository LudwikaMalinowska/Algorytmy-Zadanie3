package utils;

import models.MatrixMax;

import static utils.GenericMatrixUtils.*;
import static utils.GenericNumberUtils.*;
import static utils.GenericVectorUtils.cloneVector;


public class AnotherGauss <T extends Number> {
    public static <T extends Number> T[] gaussElimination(final T[][] values, final T[] vector){
        T[] resultVector = cloneVector(vector);
        T[][] tempMatrix = cloneMatrix(values);


        for (int k = 0; k < tempMatrix.length; k++) {

                MatrixMax<T> max = getMaxFromColumn(tempMatrix, k);
                tempMatrix = swapRows(tempMatrix, max.getI(), k);
                resultVector = swap(resultVector, k, max.getI());


            for (int i = k + 1; i < tempMatrix.length; i++) {
                T m = divide(tempMatrix[i][k], tempMatrix[k][k]);
                tempMatrix[i][k] = getZero(vector[0]);
                resultVector[i] = GenericNumberUtils.substract(resultVector[i], GenericNumberUtils.multiply(m, resultVector[k]));
                for (int j = k + 1; j < tempMatrix.length; j++) {
                    tempMatrix[i][j] = GenericNumberUtils.substract(tempMatrix[i][j], GenericNumberUtils.multiply(m, tempMatrix[k][j]));
                }
            }
        }

        for (int i = tempMatrix.length - 1; i >= 0; i--) {
            T mySum = getZero(vector[0]);
            for (int j = tempMatrix.length - 1; j > i; j--) {
                mySum = GenericNumberUtils.sum(mySum, GenericNumberUtils.multiply(tempMatrix[i][j], resultVector[j]));
            }
            resultVector[i] = divide(GenericNumberUtils.substract(resultVector[i], mySum), tempMatrix[i][i]);
        }

        return resultVector;
    }
    public static <T extends Number> MatrixMax<T> getMaxFromColumn(final T[][] matrix,
                                                                   final int k) {
        T maxElement = matrix[k][k];
        int maxI = k;

        for (int i = k; i < matrix.length; i++) {
            if (isGreater(matrix[i][k], maxElement)) {
                maxElement = matrix[i][k];
                maxI = i;
            }
        }
        return new MatrixMax<>(maxI);
    }

    public static <T extends Number> T[] swap(final T[] vector,
                                              final int k,
                                              final int swapIndex) {
        T[] resultVector = cloneVector(vector);
        T temp = resultVector[swapIndex];
        resultVector[swapIndex] = resultVector[k];
        resultVector[k] = temp;
        return resultVector;
    }

    public static <T extends Number> T[][] swapRows(final T[][] matrix,
                                                    final int swapIndex1,
                                                    final int swapIndex2) {
        T[][] resultMatrix = cloneMatrix(matrix);
        T[] tmp = resultMatrix[swapIndex1];
        resultMatrix[swapIndex1] = resultMatrix[swapIndex2];
        resultMatrix[swapIndex2] = tmp;
        return resultMatrix;
    }
}
