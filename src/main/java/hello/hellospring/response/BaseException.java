package hello.hellospring.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
public class BaseException extends Exception {
    private ResponseStatus responseStatus;

    public BaseException(hello.hellospring.response.ResponseStatus responseStatus) {
    }
}