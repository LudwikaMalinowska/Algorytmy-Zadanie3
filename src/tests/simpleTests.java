package tests;

import javafx.util.Pair;
import models.Fraction;
import org.ejml.data.DMatrixSparseCSC;
import utils.FractionUtils;
import utils.GenerateMatrix;
import utils.GenericMatrixUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static utils.GenericNumberUtils.*;

public class simpleTests {
    public static void main(String[] args) {
        Double[][] finalMatrix = createFinalMatrix(0d, 6);
        prettyPrint(finalMatrix);
    }

    private static void prettyPrint(Double[][] array){
        for (Double[] x : array)
        {
            for (Double y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    public static ArrayList<Pair<String, String>> createVotesPairs(final ArrayList<String> listOfVotes) {
        ArrayList<Pair<String, String>> votePairs = new ArrayList<>();
        for (int i = 0; i < listOfVotes.size(); i++) {
            for (int j = 0; j < listOfVotes.size(); j++) {
                if (i == j) continue;
                votePairs.add(new Pair<>(listOfVotes.get(i), listOfVotes.get(j)));
            }
        }
        return votePairs;
    }

    public static ArrayList<Pair<Integer, Integer>> createVotesOptions(int numberOfVoters) {
        ArrayList<Pair<Integer, Integer>> voteOptions = new ArrayList<>();
        for (Integer i = 0; i <= numberOfVoters; i++) {
            for (Integer j = 0; i + j <= numberOfVoters; j++) {
                voteOptions.add(new Pair<>(i, j));
            }
        }
        return voteOptions;
    }

    public static <T extends Number> T[][] createFinalMatrix(T classSample, Integer number) {
        ArrayList<Pair<Integer, Integer>> listOfOptions = createVotesOptions(number);
        T[][] resultMatrix = GenericMatrixUtils.getZerosMatrix(classSample, listOfOptions.size());
        for (int i = 0; i < listOfOptions.size(); i++) {
            ArrayList<String> listOfVotes = GenerateMatrix.generateArray(listOfOptions.get(i).getKey(), listOfOptions.get(i).getValue(), number);
            ArrayList<Pair<String, String>> votesPairs = createVotesPairs(listOfVotes);
            for (Pair<String, String> votePair : votesPairs) {
                Pair<String, String> newPair = GenerateMatrix.transitionFunction.get(votePair);
                ArrayList<String> newListOfVotes = new ArrayList<>(listOfVotes);
                newListOfVotes.remove(votePair.getKey());
                newListOfVotes.remove(votePair.getValue());
                newListOfVotes.add(newPair.getValue());
                newListOfVotes.add(newPair.getValue());
                long countN = newListOfVotes.stream().filter(e -> Objects.equals(e, "N")).count();
                long countY = newListOfVotes.stream().filter(e -> Objects.equals(e, "Y")).count();
                int y = listOfOptions.indexOf(new Pair<>((int) countY, (int) countN));
                resultMatrix[i][y] = sum(resultMatrix[i][y], getOne(classSample));
            }
        }
       Integer numberToDivide= number *(number-1);
        T numberOfClass= makeNumberOfClass(classSample,numberToDivide);
        for (int i = 0; i < listOfOptions.size(); i++) {
            for (int j = 0; j < listOfOptions.size(); j++){
                resultMatrix[i][j]=divide(resultMatrix[i][j],numberOfClass);
            }
        }

        for (int i = 0; i < listOfOptions.size(); i++) {
            resultMatrix[i][i]=substract(resultMatrix[i][i],getOne(classSample));
        }

        return resultMatrix;
    }

    public static void SparseMatrix() {
        DMatrixSparseCSC sparseMatrix = new DMatrixSparseCSC(5, 5);
        sparseMatrix.set(2, 2, 5d);
        sparseMatrix.print();
    }

    public static void GaussTest() {


    }

    public static void FractionTest() {
        Fraction half = new Fraction(BigInteger.valueOf(-4), BigInteger.valueOf(8));
        System.out.println(half);

        Fraction different_half = new Fraction(BigInteger.valueOf(5), BigInteger.valueOf(10));
        System.out.println(different_half);
        System.out.println(FractionUtils.multiply(half, different_half));
        Fraction one = new Fraction(BigInteger.valueOf(10), BigInteger.valueOf(10));
        System.out.println(FractionUtils.add(different_half, one));
        System.out.println(FractionUtils.minus(different_half, one));
        System.out.println(FractionUtils.div(one, different_half));
        System.out.println(FractionUtils.isGreater(different_half, half));
    }

}
