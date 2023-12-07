package hello.hellospring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectDataDTO {

    private String category;
    private String subject;
    private String classes;
    private int credit;
    private String course;
    private int complete;
    private boolean recommend;
    private boolean chosen;

    public SubjectDataDTO(){}

    public SubjectDataDTO(String category, String subject, String className, int credit, String course,
                          int complete, boolean recommend, boolean chosen) {
        this.category = category;
        this.subject = subject;
        this.classes = className;
        this.credit = credit;
        this.course = course;
        this.complete = complete;
        this.recommend = recommend;
        this.chosen = chosen;
    }
}
