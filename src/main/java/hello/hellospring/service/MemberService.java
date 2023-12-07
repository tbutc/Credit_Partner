package hello.hellospring.service;

import hello.hellospring.controller.MemberController;
import hello.hellospring.domain.Member;
import hello.hellospring.dto.SignInRequestDto;
import hello.hellospring.dto.SignInResultDto;
import hello.hellospring.jwt.JwtTokenProvider;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.response.BaseException;
import hello.hellospring.response.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    public String findUsername(String id){
        Member member = memberRepository.findById_not_null(id); return member.getName();}

    public SignInResultDto signIn(SignInRequestDto signInRequestDto) throws RuntimeException, BaseException {

        log.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        log.info("[getSignInResult] username : {}", signInRequestDto.getUsername());

        Member user = memberRepository.findById_not_null(signInRequestDto.getUsername());

        log.info("[getSignInResult] Email : {}", signInRequestDto.getUsername());
        log.info("[getSignInResult] 패스워드 비교 수행");
        if (!signInRequestDto.getPassword().equals(user.getPwd())) {
            System.out.println(signInRequestDto.getPassword());
            System.out.println(user.getPwd());
            throw new RuntimeException("패스워드 불일치");
        }
        log.info("[getSignInResult] 패스워드 일치");

        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()))
                .build();
        signInResultDto.setIsSuccess(true);
        log.info("[getSignInResult] SignInResultDto 객체에 값 주입 완료");

        return signInResultDto;
    }

//    /**
//     * 회원 가입
//     */
    @Transactional  //0714
    public String join(Member member) {
        validateDuplicateMember(member);    //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


//    public String login(Map<String, String> members) {
//
//        String memberId = members.get("id");
//        Optional<Member> memberOptional = memberRepository.findById(memberId);
//
//        if (memberOptional.isEmpty()) {
//            throw new IllegalArgumentException("가입되지 않은 ID 입니다. ID: " + memberId);
//        }
//        Member member = memberOptional.get(); // member 객체를 가져옴
//        String password = members.get("password");
//        if (!member.checkPassword(passwordEncoder, password)) {
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//
//        List<String> roles = new ArrayList<>();
//        roles.add(member.getRole().name());
//
//        return jwtTokenProvider.createToken(member.getId(), roles);
//    }
    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(String memberId) {
        return memberRepository.findById(memberId);
    }

}

