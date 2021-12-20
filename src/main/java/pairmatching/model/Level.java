package pairmatching.model;

import java.util.ArrayList;

public enum Level {
    LEVEL1("레벨1"),
    LEVEL2("레벨2"),
    LEVEL3("레벨3"),
    LEVEL4("레벨4"),
    LEVEL5("레벨5");

    private String name;
    private ArrayList<Mission> missions = new ArrayList<>();

    Level(String name) {
        this.name = name;
    }

    public void addMission(String missionName) {
        missions.add(new Mission(missionName));
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Mission> getMissions() {
        return missions;
    }

    public Mission findMissionByName(String missionName) {
        for (Mission mission : missions) {
            if (mission.getName().equals(missionName)) {
                return mission;
            }
        }
        return null;
    }

    public boolean checkExistSamePair(Course course, PairList pairList) {
        for (Mission mission : missions) {
            PairList existPairList = mission.getPairList(course);
            if(!existPairList.existSamePair(pairList)){
                return false;
            }
        }
        return true;
    }
}
