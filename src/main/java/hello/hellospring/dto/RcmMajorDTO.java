package hello.hellospring.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class RcmMajorDTO {

    public String getResultMajor() {
        return resultMajor;
    }

    public void setResultMajor(String resultMajor) {
        this.resultMajor = resultMajor;
    }

    private String resultMajor;

    public RcmMajorDTO(){}

    public RcmMajorDTO(String resultMajor){
        this.resultMajor = resultMajor;
    }



}
