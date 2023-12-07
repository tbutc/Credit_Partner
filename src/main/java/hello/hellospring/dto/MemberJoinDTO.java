package hello.hellospring.dto;

import hello.hellospring.domain.Authority;
import hello.hellospring.domain.Member;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJoinDTO {
    private String name;
    private String id;
    private String pwd;
    // 다른 필요한 회원 정보들도 추가 가능
    private Authority role;

    // 기본 생성자
//    public MemberJoinDTO() {}

    // 파라미터가 있는 생성자
    public MemberJoinDTO(String name, String id, String pwd) {
        this.name = name;
        this.id = id;
        this.pwd = pwd;
    }

    @Builder
    public Member toEntity(){
        return Member.builder()
                .id(id)
                .name(name)
                .pwd(pwd)
                .role(Authority.ROLE_USER)
                .build();
    }
}
