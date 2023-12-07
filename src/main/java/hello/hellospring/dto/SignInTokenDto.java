package hello.hellospring.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInTokenDto {
    private String token;
    private String name;

    @Builder
    public SignInTokenDto(String token, String name) {
        this.token = token;
        this.name = name;
    }
}
