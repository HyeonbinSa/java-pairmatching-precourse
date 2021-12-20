package pairmatching.controller;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;
import pairmatching.model.*;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class PairMatchController {
    private static InputView inputView = new InputView();
    private static OutputView outputView = new OutputView();
    /**
     * 기능 1. 페어를 위한 랜덤 크루 목록 생성
     * 기능 1-1. 페어 매칭
     * 기능 1-2. 페어 리스트 생성
     * 기능 2. 해당 미션과 과정에서 페어가 존재 유무 확인
     *  2-1 존재 시 덮어쓰기
     *  2-2 존재 시 그냥 Skip
     * 기능 2-1. 해당 미션과 과정에 페어가 없을 경우
     *  2-1-1. 해당 레벨의 다른 미션에서 동일한 페어가 존재 할 경우
     *    - 페어 랜덤 크루 재매칭
     *    - 3번 다를 경우 에러 메세지 발생
     *    - 페어 등록
     *  2-1-2. 해당 레벨의 다른 미션에서 동일한 페어가 존재하지 않을 경우
     *    3.2-1. 페어 등록
     */
    public void match(Level level, Course course, Mission mission){
        int rematchCount = 0;
        if(existPairListInMissionAndCourse(course, mission)){
            if(!selectUpdatePairList()){
                return;
            }
            initialPairList(course, mission);
        }
        PairList pairList = matchPair(course);
        while(!existPairInSameLevel(level, pairList, course) && rematchCount < 3){
            pairList.getPairList().clear();
            pairList = matchPair(course);
            rematchCount++;
        }
        if(rematchCount >= 3){
            throw new IllegalArgumentException("재매칭 횟수 초과 에러  ");
        }
        addPairListInMission(mission,course,pairList);
    }


    // 기능 1. 랜덤 크루 목록 생성
    public List<String> createRandomCrewList(Course course) {
        return Randoms.shuffle(course.getStringCrewList());
    }

    // 기능 1-1. 패어 매칭 기능
    private PairList matchPair(Course course) {
        List<String> crewList = createRandomCrewList(course);
        System.out.println(crewList);
        System.out.println("crewList Size : "+ crewList.size());
        PairList pairList = new PairList();
        pairList.getPairList().clear();
        System.out.println("pairList Size 1 : "+ pairList.getPairList().size());
        for (int idx = 0; idx < crewList.size(); idx += 2) {
            Pair pair = new Pair(crewList.get(idx), crewList.get(idx + 1));
            if (crewList.size() % 2 == 1 && idx == crewList.size() - 2) {
                pair.addPair(crewList.get(crewList.size() - 1));
                pairList.addPair(pair);
                break;
            }
            pairList.addPair(pair);
        }
        System.out.println("pairList Size 2  : "+ pairList.getPairList().size());
        return pairList;
    }
    // 기능 2. 페어 해당 코스와 미션에 페어가 존재하는지 확인
    public boolean existPairListInMissionAndCourse(Course course, Mission mission){
        return mission.getPairList(course) != null;
    }

    // 기능 2-1, 2-2. 덮어쓸지 Skip할지 선택
    public boolean selectUpdatePairList(){
        inputView.selectUpdatePair();
        String select = Console.readLine();
        return select.equals("네");
    }


//    public void updatePairList(Course course, Mission mission){
//        mission.initPairList(course);
//        PairList pairList = matchPair(course);
//    }
    // 기능 2-1. 페어리스트 덮어쓰기위해 초기화
    public void initialPairList(Course course, Mission mission){
        mission.initPairList(course);
    }

    // 기능 .생성한 페어리스트가 동일 레벨 내 다른 미션에 존재하는지 확인
    public boolean existPairInSameLevel(Level level, PairList pairList, Course course){
        ArrayList<Mission> missions = level.getMissions();
        for(Mission mission : missions){
            PairList pairListInMission = mission.getPairList(course);
            if(pairListInMission!=null && !pairListInMission.existSamePair(pairList)){
                return false;
            }
        }
        return true;
    }



    public ArrayList<Mission> getMissionsInLevel(Level level){
        return level.getMissions();
    }


    // 기능. 페어 등록 기능
    public void addPairListInMission(Mission mission, Course course, PairList pairList){
        mission.addPairMap(course, pairList);
    }


}
