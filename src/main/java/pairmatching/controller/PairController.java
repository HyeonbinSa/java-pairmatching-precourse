package pairmatching.controller;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import pairmatching.InitialSetting;
import pairmatching.model.*;
import pairmatching.validator.Validator;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class PairController {
    private InputView inputView = new InputView();
    private InitialSetting initialSetting = new InitialSetting();
    private OutputView outputView = new OutputView();
    private Validator validator = new Validator();
    private PairMatchController pairMatchController = new PairMatchController();
    private static final String MENU_ONE = "1";
    private static final String MENU_TWO = "2";
    private static final String MENU_THREE = "3";
    private static final String MENU_QUIT = "Q";

    public void run() {
        initialSetting.initialInformation();
        initialSetting.getFileNameInDirectory();
        while (selectMenu()) {
        }
    }

    private boolean selectMenu() {
        try {
            inputView.printSelectMenu();
            String selectMenu = Console.readLine();
            validator.validateSelectMenu(selectMenu);
            if (selectMenu.equals(MENU_ONE)) {
                pairMatch();
            }
            if (selectMenu.equals(MENU_TWO)) {
                getPair();
            }
            if (selectMenu.equals(MENU_THREE)) {
                initialPair();
            }
            if (selectMenu.equals(MENU_QUIT)) {
                return false;
            }
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            selectMenu();
        }
        return true;
    }

    private void pairMatch() {
        try {
            outputView.printInformation();
            inputView.inputInformation();
            String inputInformation = Console.readLine();
            String[] splitInformation = validator.validateCountComma(inputInformation);
            Course course = validator.validateCourse(splitInformation[0]);
            Level level = validator.validateLevel(splitInformation[1]);
            Mission mission = validator.validateMission(level, splitInformation[2]);
            pairMatchController.match(level, course, mission);
            outputView.printPairList(course, mission);
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            pairMatch();
        }
    }

    private void getPair() {
        try {
            outputView.printInformation();
            inputView.inputInformation();
            String inputInformation = Console.readLine();
            String[] splitInformation = validator.validateCountComma(inputInformation);
            Course course = validator.validateCourse(splitInformation[0]);
            Level level = validator.validateLevel(splitInformation[1]);
            Mission mission = validator.validateMission(level, splitInformation[2]);
            validator.validateExistPairList(course, mission);
            outputView.printPairList(course, mission);
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
            getPair();
        }
    }

    private void initialPair() {
        for (Level level : Level.values()) {
            ArrayList<Mission> missions = level.getMissions();
            initialMissionList(missions);
        }
    }

    private void initialMissionList(ArrayList<Mission> missions) {
        for (Mission mission : missions) {
            mission.initPairMap();
        }
        outputView.printInitPairMessage();
    }
    // void -> PairList
//    private PairList inputPair(Course course, Mission mission) {
//        List<String> crewList = shuffleStringCrew(course);
//        PairList pairList = new PairList();
//        for (int idx = 0; idx < crewList.size(); idx += 2) {
//            Pair pair = new Pair(crewList.get(idx), crewList.get(idx + 1));
//            if (crewList.size() % 2 == 1 && idx == crewList.size() - 2) {
//                pair.addPair(crewList.get(crewList.size() - 1));
//                pairList.addPair(pair);
//                break;
//            }
//            pairList.addPair(pair);
//        }
////        mission.addPairMap(course, pairList);
//        return pairList;
//    }

    private ArrayList<Mission> checkSameLevel(Level level) {
        return level.getMissions();
        // 같은 레벨에서 페어 매칭은 최대 한번
    }

//    private List<String> shuffleStringCrew(Course course) {
//        return Randoms.shuffle(course.getStringCrewList());
//    }

//    private PairList checkExistPairListInMission(Course course, Mission mission) {
//        if (mission.getPairList(course) != null) {
//            return selectUpdate(course, mission);
//        }
//        return inputPair(course, mission);
//    }

//    private PairList selectUpdate(Course course, Mission mission) {
//        inputView.selectUpdatePair();
//        String select = Console.readLine();
//        if (select.equals("네")) {
//            mission.initPairList(course);
//            return inputPair(course, mission);
//        }
//        return null;
//    }
}
