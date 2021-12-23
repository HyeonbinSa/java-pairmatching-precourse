package pairmatching.model;

import java.util.ArrayList;

public class PairList {
    private ArrayList<Pair> pairList = new ArrayList<>();

    public void addPair(Pair pair) {
        pairList.add(pair);
    }

    public ArrayList<Pair> getPairList() {
        return pairList;
    }

    public void clear() {
        pairList.clear();
    }

    public boolean existSamePair(PairList createPairList){
        for(Pair pair : this.getPairList()){
            for(Pair createdPair :createPairList.getPairList()){
                if(!pair.comparePair(createdPair)){
                    return false;
                }
            }
        }
        return true;
    }
}
