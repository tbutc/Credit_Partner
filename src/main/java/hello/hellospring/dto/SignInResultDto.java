package hello.hellospring.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResultDto {

    private boolean IsSuccess;

    private String message;

    private int code;

    private String token;

    @Builder
    public SignInResultDto(boolean success, int code, String msg, String token) {
        this.IsSuccess = success;
        this.message = msg;
        this.code = code;
        this.token = token;
    }
}

