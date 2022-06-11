package utils;

import javafx.util.Pair;

import java.util.*;

import static utils.GenericNumberUtils.*;
import static utils.GenericNumberUtils.getOne;

public class GenerateMatrix {

    public static Map<Pair<String, String>, Pair<String, String>> transitionFunction = new HashMap<>(Map.of(
            new Pair<>("Y", "Y"), new Pair<>("Y", "Y"),
            new Pair<>("N", "Y"), new Pair<>("U", "U"),
            new Pair<>("Y", "N"), new Pair<>("U", "U"),
            new Pair<>("N", "N"), new Pair<>("N", "N"),
            new Pair<>("U", "U"), new Pair<>("U", "U"),
            new Pair<>("Y", "U"), new Pair<>("Y", "Y"),
            new Pair<>("U", "Y"), new Pair<>("Y", "Y"),
            new Pair<>("N", "U"), new Pair<>("N", "N"),
            new Pair<>("U", "N"), new Pair<>("N", "N")
    ));

    public static ArrayList<String> generateArray(int yVotes, int nVotes, int size) {
        int uVotes = size - nVotes - yVotes;
        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < yVotes; i++) {
            arr.add("Y");
        }
        for (int i = 0; i < nVotes; i++) {
            arr.add("N");
        }
        for (int i = 0; i < uVotes; i++) {
            arr.add("U");
        }
        return arr;

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

    public static <T extends Number> T[][] createFinalVector(T classSample, Integer number) {
        ArrayList<Pair<Integer, Integer>> listOfOptions = createVotesOptions(number);
        T[][] resultMatrix = GenericMatrixUtils.getZerosMatrix(classSample, listOfOptions.size(), 1);
        resultMatrix[listOfOptions.size() - 1][0] = GenericNumberUtils.makeNumberOfClass(classSample, -1);
        return resultMatrix;
    }


//    public static <T extends Number> T[] createFinalVector(T classSample, Integer number) {
//        ArrayList<Pair<Integer, Integer>> listOfOptions = createVotesOptions(number);
//        T[] resultMatrix = GenericVectorUtils.getZerosVector(classSample, listOfOptions.size());
//        resultMatrix[listOfOptions.size() - 1] = GenericNumberUtils.makeNumberOfClass(classSample, -1);
//        return resultMatrix;
//    }

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
        Integer numberToDivide = number * (number - 1);
        T numberOfClass = makeNumberOfClass(classSample, numberToDivide);
        for (int i = 0; i < listOfOptions.size(); i++) {
            for (int j = 0; j < listOfOptions.size(); j++) {
                resultMatrix[i][j] = divide(resultMatrix[i][j], numberOfClass);
            }
        }

        for (int i = 0; i < listOfOptions.size(); i++) {
            if (resultMatrix[i][i].equals(GenericNumberUtils.getOne(resultMatrix[i][i]))) {
                resultMatrix[i][i] = GenericNumberUtils.getMinusOne(resultMatrix[i][i]);
            } else {
                resultMatrix[i][i] = substract(resultMatrix[i][i], getOne(classSample));
            }
        }
        //resultMatrix[resultMatrix.length-1][resultMatrix.length-1] = GenericNumberUtils.getOne(resultMatrix[0][0]);

        return resultMatrix;
    }

    public ArrayList<Integer> sumVotes(ArrayList<String> votes) {
        int yVotes = 0, nVotes = 0, unknown = 0;

        for (String vote : votes) {
            if (vote.equals("Y")) yVotes++;
            if (vote.equals("N")) nVotes++;
            if (vote.equals("U")) unknown++;
        }

        return new ArrayList<>(Arrays.asList(yVotes, nVotes, unknown));
    }

    /*public ArrayList<Pair<String, String>> generatePairs(ArrayList<String> arr){


    }*/
}
