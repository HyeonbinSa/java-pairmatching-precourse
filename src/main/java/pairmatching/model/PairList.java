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
}
