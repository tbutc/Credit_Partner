package hello.hellospring.config;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Primary
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    // 사용자 정보를 조회하는 메서드
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 사용자 ID를 기반으로 데이터베이스에서 사용자 정보를 조회
        return memberRepository.findById(id)
                .map(this::createUserDetails) // 사용자 정보가 있으면 UserDetails 객체를 생성하여 반환
                .orElseThrow(() -> new UsernameNotFoundException(id + "을 DB에서 찾을 수 없습니다"));
    }

    // UserDetails 객체를 생성하여 반환하는 메서드
    private UserDetails createUserDetails(Member member) {
        // 사용자의 권한 정보를 Spring Security의 GrantedAuthority 형식으로 변환
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().toString());

        // UserDetails 객체를 생성하여 반환
        return new User(
                String.valueOf(member.getId()), // 사용자 ID
                member.getPwd(), // 사용자 비밀번호
                Collections.singleton(grantedAuthority) // 사용자 권한
        );
    }
}
