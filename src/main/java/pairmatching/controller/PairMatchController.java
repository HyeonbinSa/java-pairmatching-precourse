package pairmatching.controller;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import pairmatching.model.*;
import pairmatching.view.InputView;

import java.util.ArrayList;
import java.util.List;

public class PairMatchController {
    private static InputView inputView = new InputView();

    public void matchPair(Course course, Level level, Mission mission) {
        if (mission.getPairList(course) != null) {
            if (!selectUpdate()) {
                return;
            }
            removePairListInMission(course, mission);
        }
        PairList pairList = recursiveCreateRandomPair(level, course);
        addPairListMission(mission, course, pairList);
    }

    public boolean selectUpdate() {
        inputView.selectUpdatePair();
        String selection = Console.readLine();
        return selection.equals("네");
    }

    private void removePairListInMission(Course course, Mission mission) {
        mission.initPairList(course);
    }

    private List<String> createRandomCrews(Course course) {
        return Randoms.shuffle(course.getStringCrewList());
    }

    private void createPairList(PairList pairList, List<String> randomList) {
        pairList.clear();
        for (int idx = 0; idx < randomList.size(); idx += 2) {
            Pair pair = new Pair(randomList.get(idx), randomList.get(idx + 1));
            if (randomList.size() % 2 == 1 && idx == randomList.size() - 2) {
                pair.addPair(randomList.get(randomList.size() - 1));
                pairList.addPair(pair);
                break;
            }
            pairList.addPair(pair);
        }
    }

    private boolean checkExistPairListInSameLevel(Level level, Course course, PairList pairList) {
        ArrayList<Mission> missions = level.getMissions();
        for (Mission mission : missions) {
            PairList pairListInLevel = mission.getPairList(course);
            if (pairListInLevel == null) {
                continue;
            }
            if (!comparePairLists(pairListInLevel, pairList)) {
                return false;
            }
        }
        return true;
    }

    public boolean comparePairLists(PairList pairListInLevel, PairList createdPairList) {
        return pairListInLevel.existSamePair(createdPairList);
    }

    public PairList recursiveCreateRandomPair(Level level, Course course) {
        int loopCount = 0;
        PairList pairList = new PairList();
        while (loopCount < 3) {
            loopCount++;
            List<String> randomList = createRandomCrews(course);
            createPairList(pairList, randomList);
            if (checkExistPairListInSameLevel(level, course, pairList)) {
                return pairList;
            }
        }
        if (loopCount >= 3) {
            throw new IllegalArgumentException("재생성 횟수 초과 ");
        }
        return pairList;
    }

    public void addPairListMission(Mission mission, Course course, PairList pairList) {
        mission.addPairMap(course, pairList);
    }
}
