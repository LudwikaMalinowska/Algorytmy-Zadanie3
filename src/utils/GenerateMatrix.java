//package utils;
//import javafx.util.Pair;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//public class GenerateMatrix {
//
//    public static Map<Pair<String, String>, Pair<String, String>> transitionFunction = new HashMap<>(Map.of(
//            new Pair<>("Y", "Y"), new Pair<>("Y", "Y"),
//            new Pair<>("N", "Y"), new Pair<>("U", "U"),
//            new Pair<>("Y", "N"), new Pair<>("U", "U"),
//            new Pair<>("N", "N"), new Pair<>("N", "N"),
//            new Pair<>("U", "U"), new Pair<>("U", "U"),
//            new Pair<>("Y", "U"), new Pair<>("Y", "Y"),
//            new Pair<>("U", "Y"), new Pair<>("Y", "Y"),
//            new Pair<>("N", "U"), new Pair<>("N", "N"),
//            new Pair<>("U", "N"), new Pair<>("N", "N")
//    ));
//
//    public static ArrayList<String> generateArray(int yVotes, int nVotes, int size){
//        int uVotes= size-nVotes-yVotes;
//        ArrayList<String> arr = new ArrayList<>();
//        for ( int i=0; i<yVotes;i++){
//            arr.add("Y");
//        }
//        for (int i=0; i<nVotes; i++){
//            arr.add("N");
//        }
//        for (int i=0; i<uVotes; i++){
//            arr.add("U");
//        }
//        return arr;
//
//    }
//
//
//    public ArrayList<Integer> sumVotes(ArrayList<String> votes) {
//        int yVotes = 0, nVotes = 0, unknown = 0;
//
//        for (String vote : votes) {
//            if (vote.equals("Y")) yVotes++;
//            if (vote.equals("N")) nVotes++;
//            if (vote.equals("U")) unknown++;
//        }
//
//        return new ArrayList<>(Arrays.asList(yVotes, nVotes, unknown));
//    }
//
//    /*public ArrayList<Pair<String, String>> generatePairs(ArrayList<String> arr){
//
//
//    }*/
//}
