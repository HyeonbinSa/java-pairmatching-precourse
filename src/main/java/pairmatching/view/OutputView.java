package pairmatching.view;

import pairmatching.model.*;

import java.util.ArrayList;
import java.util.List;

public class OutputView {
    private static final String DIVIDER_LINE = "#############################################";
    private static final String PAIR_MATCH_RESULT = "페어 매칭 결과입니다.";
    private static final String INIT_PAIR_RESULT = "초기화 되었습니다.";
    private static final String NEW_LINE = "\n";
    private static final String ERROR = "[ERROR] ";
    private static final String COURSE_PREFIX = "과정: ";
    private static final String MISSION_PREFIX = "미션: ";
    private static final String MISSION_SUFFIX = ": ";
    private static final String LEVEL_PREFIX = "- ";
    private static final String DIVIDER = " | ";
    private static final String PAIR_DIVIDER = " : ";

    public void printInformation() {
        System.out.println(NEW_LINE + DIVIDER_LINE);
        printCourse();
        printMissions();
        System.out.println(DIVIDER_LINE);
    }

    private void printCourse() {
        System.out.print(COURSE_PREFIX);
        List<String> stringList = new ArrayList<>();
        for (Course course : Course.values()) {
            stringList.add(course.getName());
        }
        System.out.println(String.join(DIVIDER, stringList));
    }

    private void printMissions() {
        System.out.println(MISSION_PREFIX);
        for (Level level : Level.values()) {
            System.out.print(LEVEL_PREFIX + level.getName() + MISSION_SUFFIX);
            printMission(level.getMissions());
        }
    }

    private void printMission(ArrayList<Mission> missions) {
        ArrayList<String> stringList = new ArrayList<>();
        for (Mission mission : missions) {
            stringList.add(mission.getName());
        }
        System.out.println(String.join(DIVIDER, stringList));
    }

    public void printPairList(Course course, Mission mission) {
        System.out.println(NEW_LINE + PAIR_MATCH_RESULT);
        PairList pairList = mission.getPairList(course);
        for (Pair pair : pairList.getPairList()) {
            printPair(pair);
        }
    }

    public void printPair(Pair pair) {
        System.out.println(String.join(PAIR_DIVIDER, pair.getPair()));
    }

    public void printError(String message) {
        System.out.println(ERROR + message);
    }

    public void printInitPairMessage() {
        System.out.println(INIT_PAIR_RESULT);
    }
}
