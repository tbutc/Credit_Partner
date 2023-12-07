package hello.hellospring.service;

import hello.hellospring.domain.Classes;
import hello.hellospring.domain.Credit;
import hello.hellospring.domain.Subject;
import hello.hellospring.dto.AllClassDTO;
import hello.hellospring.dto.CompleteDTO;
import hello.hellospring.dto.GuideDTO;
import hello.hellospring.dto.SubjectDataDTO;
import hello.hellospring.repository.GuideRepository;
import org.springframework.context.annotation.Configuration;
import hello.hellospring.repository.GuideRepository;

import java.util.List;

//public List<String> getMajor(String major_name)
@Configuration
public class GuideService {


    private final GuideRepository guideRepository;

    public GuideService(hello.hellospring.repository.GuideRepository guideRepository){
        this.guideRepository = guideRepository;
    }

    public void deleteGuide(String id) {
        guideRepository.deleteGuide(id);
    }

    public List<AllClassDTO> getAllClass(String major) {
        return guideRepository.getAllClass(major);
    }


    public GuideDTO getAllTotalguide(String id, String major){
        return guideRepository.getAllTotalguide(id, major);
    }

    public List<String> showAll(String id){
        return guideRepository.getMajor(id);
    }

    public String getSID(String sid, String major){
        return guideRepository.getSID(sid, major);
    }

    public List<String> getSubjectList(String major){
        return guideRepository.getSubjectList(major);
    }
    public List<CompleteDTO> getCompleteClass(String id) { return guideRepository.getCompleteClass(id);
    }

    public void insertTemporaryGuide(String major, String id, List<SubjectDataDTO> subjectDataDTOList) {
        guideRepository.insertTemporaryGuide(major, id, subjectDataDTOList);
    }

    public void deleteTemporaryGuide(String major, String id) {
        guideRepository.deleteTemporaryGuide(major, id);
    }

    public void applyCompleteList(String major, String id, List<CompleteDTO> completeList) {
        //completeList 디비에 임시 생성
        guideRepository.complete_create(completeList);

        //임시 completeList와, 임시 total_guide 조인하여 complete 변경하기
        guideRepository.complete_check(major, id);

        //임시 생성한 completeList 삭제
        guideRepository.complete_delete();
    }

    //채윤
    public void applyRecommendList(String major, String id, List<String> recommendSubjects){
        //추천받은 과목 저장하는 tmp_RecommendList 임시 생성
        guideRepository.insertSubjects(recommendSubjects);

        //tmp_recommendList와 total_guide 조인해서 recommend=True, chosen=True로 변경하기
        guideRepository.recommend_check(major, id);

        //tmp_recommendList 삭제
        guideRepository.recommend_delete();

    }

    public void insertGuide(String id, List<GuideDTO> pastGuideList) {
        for(GuideDTO guideElement : pastGuideList){
            String major = guideElement.getMajor();
            List<SubjectDataDTO> subjects = guideElement.getSubjectData();

            guideRepository.insertGuide(major, id, subjects);
        }
    }
}
