package MonteCarlo;

import java.util.ArrayList;

public class MonteCarloMethod {
    private final Integer YesCount;
    private final Integer NoCount;
    private final Integer AllVotesCount;

    public MonteCarloMethod(Integer YesVotes,Integer NoVotes,Integer AllVotes){
        this.YesCount=YesVotes;
        this.NoCount=NoVotes;
        this.AllVotesCount=AllVotes;
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

    public boolean checkIfStateIsNotStable(ArrayList<Voter> voters){
        for (int i= 0; i< voters.size()-1;i++){
            if(!voters.get(i).getVote().equals(voters.get(i + 1).getVote())) return true;
        }
        return false;
    }
}
