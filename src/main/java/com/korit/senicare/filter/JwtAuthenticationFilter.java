package com.korit.senicare.filter;

import java.io.IOException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.korit.senicare.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// JWT 검증 및 Security Context에 접근 제어자 추가 필터
// - request의 header 에서 토큰 추출 검증
// - security context에 접근 제어자 정보 등록
// JwtAuthenticationFilter 빈으로 등록 = component
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 생성자로 지정 (= final)
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

                try {
                    
                    // Reqiest 객체에서 Bearer 토큰 추출
                    String token = parseBearerToken(request);
                    if (token == null ){
                        filterChain.doFilter(request, response);
                        return;
                    }

                    // 토큰 검증
                    String userId = jwtProvider.validate(token);
                    if (userId == null) {
                        filterChain.doFilter(request, response);
                        return;
                    }

                    // security context에 등록
                    setContext(request, userId);

                } catch (Exception exception) {
                    exception.printStackTrace();

                }
                filterChain.doFilter(request, response);

    }

    // request로 부터 토큰 추출
    // 토큰이니까 문자열
    // request에 정보 담겨있으니까 request를 매개변수로 받아오는 것임
    private String parseBearerToken(HttpServletRequest request) {

        // Request 객체의 Header에서 Auhorization 필드 값을 추출
        String authoziation = request.getHeader("Authorization");

        // 추출한 authorization 값이 실제로 존재하는 문자열 확인
        boolean hasAuthorization = StringUtils.hasText(authoziation);
        if (!hasAuthorization) return null;

        // Bear 인증 방식인지 확인
        boolean isBearer = authoziation.startsWith("Bearer ");
        if (!isBearer) return null;

        // Authorization 필드 값에서 토큰 추출
        String token = authoziation.substring(7);
        return token;
    } 

    // security context 생성 및 등록
    private void setContext(HttpServletRequest request, String userId) {

        // 접근 주체에 대한 인증 토큰 생성
        AbstractAuthenticationToken authenticationToken =
        // 접근 주체에 대한 정보 넣어줌= userId
        new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES);

        // 생성한 인증 토큰이 어떤 요청에 대한 내용인지 상세 정보를 추가
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 빈 security context 생성
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        // 생성한 빈 security context에 authentication Tokn 주입
        securityContext.setAuthentication(authenticationToken);

        // 생성한 security context 등록
        SecurityContextHolder.setContext(securityContext);

    }
    
}
