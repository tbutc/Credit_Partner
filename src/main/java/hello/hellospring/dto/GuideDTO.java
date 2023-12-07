package hello.hellospring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GuideDTO {
    private String major;
    private List<SubjectDataDTO> subjectData;

    public GuideDTO(String major, List<SubjectDataDTO> subjectData){
        this.major = major;
        this.subjectData = subjectData;
    }

    public GuideDTO() {

    }
}
