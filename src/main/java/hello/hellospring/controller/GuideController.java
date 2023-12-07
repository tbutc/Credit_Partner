package hello.hellospring.controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import hello.hellospring.dto.*;
import hello.hellospring.repository.GuideRepository;
import hello.hellospring.service.GuideService;
import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class GuideController {

    private final GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @ResponseBody
    @RequestMapping(value = "/api/guide")
    public List<GuideDTO> guideEdit(@RequestBody List<GuideDTO> pastGuideList, @RequestHeader("Authorization") String token) {

        String id = Jwts.parserBuilder()
                .setSigningKey("c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK" .getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();

        List<GuideDTO> guideList = new ArrayList<>();

        //해당 아이디로 저장되어있는 데이터를 total_guide 테이블에서 전부 삭제
        guideService.deleteGuide(id);

        //front에서 넘겨준 pastGuideList를 total_guide 테이블에 저장
        guideService.insertGuide(id, pastGuideList);

        return guideList;
    }


    @GetMapping(value = "/api/guide")
    public List<GuideDTO> guideShow(@RequestHeader("Authorization") String token) {

        String id = Jwts.parserBuilder()
                .setSigningKey("c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK" .getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();


        //로그인 된 유저의 추천 major 받아오는 코드
        List<String> major_list = guideService.showAll(id);

        //major가 1개인지 3개인지 검사(list 요소의 개수 확인하는 함수)
        int major_num = major_list.size();
        List<GuideDTO> GuideList = new ArrayList<>();
        List<GuideDTO> FinalGuideList = new ArrayList<>();

        //major 개수만큼 실행하는 반복문(1 or 3)
        for(int i=0 ; i<major_num; i++) {

            String major = major_list.get(i);

            //IF : 유저의 가이드 데이터가 디비에 있는 경우 그대로 리턴 //total_guide 테이블에 현재 사용자 id 있는지 check
            if(id.equals(guideService.getSID(id, major))) { //로그인한 사용자의 id와 total_guide의 sid가 같다면

                  GuideDTO Guide_element = guideService.getAllTotalguide(id, major);
                  FinalGuideList.add(Guide_element);

            }

            //ELSE : 그렇지 않은 경우 생성
            else {

                //모든 과목 가져오기 - (class, subject 조인)
                List<AllClassDTO> AllClassList = guideService.getAllClass(major);

                //진현 - 10/21 완료
                //이미 들은 과목 가져오기 - (class_list, class, subject 조인)
                List<CompleteDTO> CompleteList = guideService.getCompleteClass(id);

                //채윤 - 10/27 완료
                //해당 전공에 추천하는 과목 다 받아오기 (major_detail에서 받아온 다음, 파싱 작업 후 class, subject 조인해서 각 과목에 해당하는 학점, 계열 가져오기)
                List<String> subject_list = guideService.getSubjectList(major);
                List<String> currentMajorSubjects = new ArrayList<>();

                for(int j=0 ; j< subject_list.size() ; j++) {
                    String subject = subject_list.get(j);

                    String[] subjects = subject.split(";");
                    currentMajorSubjects.addAll(Arrays.asList(subjects));

                    // 다음 줄로 이동
                    j++;

                    if (j < subject_list.size()) {
                        String nextLine = subject_list.get(j);
                        // 세미콜론으로 과목 분리하고 추가
                        subjects = nextLine.split(";");
                        currentMajorSubjects.addAll(Arrays.asList(subjects));
                    }
                }

                //진현 - 10/21 완료
                //모든 과목 수만큼 반복
                //guideDTO에 과목 추가(default : recommend=false, complete=0, chosen=false)
                List<SubjectDataDTO> subjectDataDTOList = new ArrayList<>();

                for (int j = 0; j < AllClassList.size(); j++) {
                    SubjectDataDTO subjectDataDTO = new SubjectDataDTO();
                    AllClassDTO allClassDTO = AllClassList.get(j);

                    subjectDataDTO.setCategory(etc_processing(split_head(allClassDTO.getSubject_name())));
                    subjectDataDTO.setSubject(split_head(allClassDTO.getSubject_name()));
                    subjectDataDTO.setClasses(allClassDTO.getClass_name());
                    subjectDataDTO.setCredit(allClassDTO.getCredit());
                    subjectDataDTO.setCourse(getCourse(split_tail(allClassDTO.getSubject_name())));
                    subjectDataDTO.setComplete(0);

                    //공통과목인 경우
                    if(getCourse(split_tail(allClassDTO.getSubject_name()))=="공통"){
                        subjectDataDTO.setRecommend(true);
                        subjectDataDTO.setChosen(true);
                    }

                    //일반, 진로과목인 경우
                    else {
                        subjectDataDTO.setRecommend(false);
                        subjectDataDTO.setChosen(false);
                    }

                    subjectDataDTOList.add(subjectDataDTO);
                }

                GuideDTO guideDTO = new GuideDTO(major, subjectDataDTOList);
                GuideList.add(guideDTO);

                //진현 - 10/27 완료
                //total guide list를 임시로 디비에 넣어놓기
                guideService.insertTemporaryGuide(major, id, subjectDataDTOList);

//                for(String  sub : subject_list){
//                    System.out.println(sub);
//                }

                //채윤
                //추천 과목 수만큼 반복
                //DTO에 추천 과목은 recommend를 true로 변경, chosen을 true로 변경
                guideService.applyRecommendList(major, id, currentMajorSubjects);


                //진현 - 10/27 완료
                //이미 들은 과목 수만큼 반복
                //DTO에 이미 들은 과목은 complete를 들은 학기로 숫자 변경, chosen을 true로 변경
                guideService.applyCompleteList(major, id, CompleteList);

                //진현 - 10/27
                //디비에 저장한 코드를 FinalGuideList에 받아오는 코드
                GuideDTO Guide_element = guideService.getAllTotalguide(id, major);
                FinalGuideList.add(Guide_element);

                //진현 - 10/27 완료
                //테스트 실행할 때마다 total_guide에 데이터가 쌓이는 것을 방지하기 위한 코드. 최종 완료 시에는 삭제할 예정
                //임시 total_guide 삭제
//                guideService.deleteTemporaryGuide(major, id);
            }
        }

        //반복문 종료
        return FinalGuideList;

    }


    //post mapping method implement


    public static String split_head(String input) {
        if (input == null || input.isEmpty()) {
            return ""; // 빈 문자열이나 null일 경우 빈 문자열을 반환
        } else {
            return input.substring(0, input.length() - 1);
        }
    }

    public static char split_tail(String input) {
            int len = input.length();
            char last = input.charAt(len - 1); // 마지막 문자 추출
            return last; // 마지막 문자를 문자열로 변환하여 반환
    }

    public static String etc_processing(String input){
            if(input.equals("한문")||input.equals("제2외국어")||input.equals("교양")||input.equals("기술·가정")){
                return "기타";
            }
            else{
                return input;
            }
    }

    public static String getCourse(char num){
        char zero = '0';
        char one = '1';
        char two = '2';

        if(num == zero){return "공통";}
        else if (num == one) {return "일반";}
        else{return "진로";}
    }

}

