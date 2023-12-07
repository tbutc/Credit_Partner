package hello.hellospring.dto;

import lombok.Getter;
import lombok.Setter;

public class MajorRequestDTO {

    public MajorRequestDTO(){}

    public MajorRequestDTO(String major_name){
        this.major_name = major_name;
    }

    public String getMajor_name() {
        return major_name;
    }

    public void setMajor_name(String major_name) {
        this.major_name = major_name;
    }

    private String major_name;

}
