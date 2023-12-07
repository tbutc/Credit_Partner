package hello.hellospring.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class HopeMajorDTO {

    @Getter
    @Setter
    private String hopeMajor;

    @Builder
    public HopeMajorDTO(String hopeMajor){
        this.hopeMajor=hopeMajor;
    }
}
