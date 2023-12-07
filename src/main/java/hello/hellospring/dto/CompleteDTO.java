package hello.hellospring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CompleteDTO {
    String class_name;
    int credit;
    String subject_name;
    int semester;

    public CompleteDTO() {
    }

    public CompleteDTO(String class_name, int credit, String subject_name, int semester) {
        this.class_name = class_name;
        this.credit = credit;
        this.subject_name = subject_name;
        this.semester = semester;
    }
}
