package hello.hellospring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class SignInRequestDto {
    private String username;
    private String password;

    @Builder
    public SignInRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }

}