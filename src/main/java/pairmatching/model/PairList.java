package pairmatching.model;

import java.util.ArrayList;

public class PairList {
    private static final ArrayList<Pair> pairList = new ArrayList<>();

    public void addPair(Pair pair) {
        pairList.add(pair);
    }

    public ArrayList<Pair> getPairList() {
        return pairList;
    }

    public void clear() {
        pairList.clear();
    }

    public boolean existSamePair(PairList comparePairList){
        for(Pair pair :  pairList){
            if(!existSamePairInPairList(comparePairList, pair)){
                return false;
            }
        }
        return true;
    }
    private boolean existSamePairInPairList(PairList comparePairList, Pair pair){
        for(Pair comparePair: comparePairList.getPairList()){
            if(!comparePair.comparePair(comparePair, pair)){
                return false;
            }
        }
        return true;
    }
}
