package hello.hellospring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AllClassDTO {
    String class_name;
    int credit;
    String subject_name;

    public AllClassDTO() {
    }

    public AllClassDTO(String class_name, int credit, String subject_name) {
        this.class_name = class_name;
        this.credit = credit;
        this.subject_name = subject_name;
    }
}
