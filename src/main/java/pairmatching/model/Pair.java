package pairmatching.model;

import java.util.ArrayList;

public class Pair {
    private ArrayList<String> pair = new ArrayList<>();

    public Pair(String name1, String  name2){
        pair.add(name1);
        pair.add(name2);
    }
    public void addPair(String crew){
        pair.add(crew);
    }

    public ArrayList<String> getPair(){
        return pair;
    }

    public boolean comparePair(Pair firstPair, Pair secondPair){
        int count = 0;
        System.out.println(firstPair.getPair());
        System.out.println(secondPair.getPair());
        for(String name : firstPair.getPair()){
            if(secondPair.getPair().contains(name)){
                System.out.println(name);
                count++;
            }
        }
        if(count >= 2){
            return false;
        }
        return true;
    }
}
