package MonteCarlo;

import javafx.util.Pair;
import utils.GenericNumberUtils;

import java.util.ArrayList;

import static utils.GenerateMatrix.transitionFunction;

public class MonteCarloMethod {
    private final Integer YesCount;
    private final Integer NoCount;
    private final Integer AllVotesCount;
    private final Integer IterationsNumber;
    private final ArrayList<String> simulationResult;

    public MonteCarloMethod(Integer YesVotes,Integer NoVotes,Integer AllVotes,Integer NumberOfIterations){
        this.YesCount=YesVotes;
        this.NoCount=NoVotes;
        this.AllVotesCount=AllVotes;
        this.IterationsNumber =NumberOfIterations;
        this.simulationResult= new ArrayList<>();
    }

    public ArrayList<Voter> generateVoters() {
        ArrayList<Voter> votersList = new ArrayList<>();
        for (int i=0; i<this.YesCount; i++){
            votersList.add(new Voter("Y"));
        }
        for (int i=0; i<this.NoCount; i++){
            votersList.add(new Voter("N"));
        }
        for (int i=0; i<this.AllVotesCount-this.YesCount-this.NoCount; i++){
            votersList.add(new Voter("U"));
        }
        return votersList;
    }

    public static boolean checkIfStateIsNotStable(ArrayList<Voter> voters){
        for (int i= 0; i< voters.size()-1;i++){
            if(!voters.get(i).getVote().equals(voters.get(i + 1).getVote())) return true;
        }
        return false;
    }

    public <T extends Number> T propability(ArrayList<String> votes,T classSample){
        T yesVotes = GenericNumberUtils.getZero(classSample);
        T allVotes= GenericNumberUtils.makeNumberOfClass(classSample, votes.size());
        for ( String vote: votes){
            if(vote.equals("Y")) {
               yesVotes= GenericNumberUtils.sum(yesVotes,GenericNumberUtils.getOne(classSample));
            }

        }
        return GenericNumberUtils.divide(yesVotes,allVotes);


    }

    public <T extends Number> T simulation(T classSample){
        for(int i = 0; i< IterationsNumber; i++){
            ArrayList<Voter> voters = generateVoters();
            while (checkIfStateIsNotStable(voters)){
                Voter first = voters.get((int) (Math.random() * voters.size()));
                Voter second = voters.stream()
                        .filter(e -> !e.equals(first))
                        .toList()
                        .get((int) (Math.random() * (voters.size() - 1)));
                Pair<String, String> newPair = transitionFunction.get(new Pair<>(first.getVote(), second.getVote()));
                first.setVote(newPair.getKey());
                second.setVote(newPair.getValue());
            }
        this.simulationResult.add(voters.get(0).getVote());
        }
        return propability(simulationResult,classSample);
    }
}
