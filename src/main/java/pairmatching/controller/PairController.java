package pairmatching.controller;

import pairmatching.InitialSetting;
import pairmatching.model.*;
import pairmatching.validator.Validator;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

import java.util.ArrayList;

import camp.nextstep.edu.missionutils.Console;

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
            pairMatchController.matchPair(course, level, mission);
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
        outputView.printInitPairMessage();
    }

    private void initialMissionList(ArrayList<Mission> missions) {
        for (Mission mission : missions) {
            mission.initPairMap();
        }
    }
}
