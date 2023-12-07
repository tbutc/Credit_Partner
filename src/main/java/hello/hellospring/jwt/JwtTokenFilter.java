package hello.hellospring.jwt;

import hello.hellospring.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Request 이전에 1회 작동할 필터
public class JwtTokenFilter extends OncePerRequestFilter {
    private JwtTokenProvider jwtTokenProvider;  // JwtTokenProvider을 주입받는 생성자

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);  // 요청으로부터 JWT 토큰 추출
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // 토큰을 사용하여 인증 객체를 생성
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                // 정상 토큰이면 토큰을 통해 생성한 Authentication 객체를 SecurityContext에 저장
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (CustomException e) {
            // 그렇지 않다면 SecurityContextHolder를 비우고 에러응답을 보내는 처리
            SecurityContextHolder.clearContext();
            response.sendError(e.getHttpStatus().value(), e.getMessage());
            return;
        }

        filterChain.doFilter(request, response); // 다음 필터 체인 실행
    }
}