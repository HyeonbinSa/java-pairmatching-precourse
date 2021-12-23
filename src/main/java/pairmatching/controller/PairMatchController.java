package pairmatching.controller;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import pairmatching.model.*;
import pairmatching.validator.Validator;
import pairmatching.view.InputView;

import java.util.ArrayList;
import java.util.List;

public class PairMatchController {
    private static final int MAXIMUM_LOOP_COUNT = 3;
    private static final int LAST_ODD_INDEX = 3;
    private static final int NEXT_INDEX = 1;
    private static final int ZERO = 0;
    private static final String YES = "네";
    private static InputView inputView = new InputView();
    private static Validator validator = new Validator();

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

    private boolean selectUpdate() {
        inputView.selectUpdatePair();
        String selection = Console.readLine();
        validator.validateSelectUpdateMenu(selection);
        return selection.equals(YES);
    }

    private void removePairListInMission(Course course, Mission mission) {
        mission.initPairList(course);
    }

    private List<String> createRandomCrews(Course course) {
        return Randoms.shuffle(course.getStringCrewList());
    }

    private void createPairList(PairList pairList, List<String> randomList) {
        pairList.clear();
        for (int idx = ZERO; idx < randomList.size(); idx += 2) {
            Pair pair = new Pair(randomList.get(idx), randomList.get(idx + NEXT_INDEX));
            if (randomList.size() % 2 == 1 && idx == randomList.size() - LAST_ODD_INDEX) {
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

    private boolean comparePairLists(PairList pairListInLevel, PairList createdPairList) {
        return pairListInLevel.existSamePair(createdPairList);
    }

    private PairList recursiveCreateRandomPair(Level level, Course course) {
        int loopCount = ZERO;
        PairList pairList = new PairList();
        while (loopCount < MAXIMUM_LOOP_COUNT) {
            loopCount++;
            List<String> randomList = createRandomCrews(course);
            createPairList(pairList, randomList);
            if (checkExistPairListInSameLevel(level, course, pairList)) {
                return pairList;
            }
        }
        validator.validateLoopCount(loopCount);
        return pairList;
    }

    private void addPairListMission(Mission mission, Course course, PairList pairList) {
        mission.addPairMap(course, pairList);
    }
}
