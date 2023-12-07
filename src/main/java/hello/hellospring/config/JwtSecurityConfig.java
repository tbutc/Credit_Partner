package hello.hellospring.config;

import hello.hellospring.jwt.JwtTokenFilter;
import hello.hellospring.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfigurerAdapter를 확장.
 * JwtTokenProvider를 주입받음.
 * JwtFilter를 통해 Security filterchain에 filter를 추가 등록
 */
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    // JwtTokenProvider를 주입받음
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // JwtTokenFilter 객체를 생성하고 JwtTokenProvide를 주입하여 초기화
        JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);
        // UsernamePasswordAuthenticationFilter 앞에 customFilter를 추가하여 filterchain에 등록
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);


    }

}