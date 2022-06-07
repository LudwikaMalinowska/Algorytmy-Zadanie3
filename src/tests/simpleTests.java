package tests;

import javafx.util.Pair;
import models.Fraction;
import org.ejml.data.DMatrixSparseCSC;
import utils.FractionUtils;
import utils.GenerateMatrix;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class simpleTests {
    public static void main(String[] args) {
        int n = 6;
        int hashN = 2;
        int hashY = 2;
        GenerateMatrix matrixUtils = new GenerateMatrix();
        ArrayList<String> listOfVotes = matrixUtils.generateArray(hashY, hashN, n);
        ArrayList<Pair<String, String>> votesPairs = createVotesPairs(listOfVotes);
        Pair<String, String> oldPair = votesPairs.stream().skip(1).findFirst().get();
        System.out.println(hashN);
        System.out.println(hashY);
        Pair<String, String> newPair = makeTransition(oldPair, hashN, hashY);
        System.out.println(oldPair);
        System.out.println(newPair);
        System.out.println(hashN);
        System.out.println(hashY);
    }

    public static Pair<String, String> makeTransition(Pair<String, String> votePair, Integer hashN, Integer hashY){
        GenerateMatrix matrixUtils = new GenerateMatrix();
        Pair<String, String> newVotePair = matrixUtils.transitionFunction.get(votePair);
        if (!votePair.equals(newVotePair)){
            if (Objects.equals(votePair.getKey(), "N") && Objects.equals(newVotePair.getKey(), "U")) hashN -=1;
            if (Objects.equals(votePair.getKey(), "U") && Objects.equals(newVotePair.getKey(), "N")) hashN +=1;
            if (Objects.equals(votePair.getKey(), "Y") && Objects.equals(newVotePair.getKey(), "U")) hashY -=1;
            if (Objects.equals(votePair.getKey(), "U") && Objects.equals(newVotePair.getKey(), "Y")) hashY +=1;
            if (Objects.equals(votePair.getValue(), "N") && Objects.equals(newVotePair.getValue(), "U")) hashN -=1;
            if (Objects.equals(votePair.getValue(), "U") && Objects.equals(newVotePair.getValue(), "N")) hashN +=1;
            if (Objects.equals(votePair.getValue(), "Y") && Objects.equals(newVotePair.getValue(), "U")) hashY -=1;
            if (Objects.equals(votePair.getValue(), "U") && Objects.equals(newVotePair.getValue(), "Y")) hashY +=1;
        }
        return newVotePair;
    }

    public static ArrayList<Pair<String, String>> createVotesPairs(final ArrayList<String> listOfVotes){
        ArrayList<Pair<String, String>> votePairs = new ArrayList<>();
        for (int i = 0; i < listOfVotes.size(); i++) {
            for (int j = 0; j < listOfVotes.size(); j++) {
                if (i == j) continue;
                votePairs.add(new Pair<>(listOfVotes.get(i), listOfVotes.get(j)));
            }
        }
        return votePairs;
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
