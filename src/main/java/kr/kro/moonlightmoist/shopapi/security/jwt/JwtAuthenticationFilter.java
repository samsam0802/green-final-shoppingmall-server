package kr.kro.moonlightmoist.shopapi.security.jwt;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.moonlightmoist.shopapi.security.CustomUserDetailsService;
import kr.kro.moonlightmoist.shopapi.user.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // 상속
    // Spring이 제공하는 필터 기반 클래스
    // 요청당 딱 한 번만 실행을 보장 (중복 실행 방지)
    // doFilterInternal() 메서드 오버라이드

    private final JwtTokenProvider jwtTokenProvider; // JWT 생성 유틸
    private final CustomUserDetailsService customUserDetailsService; // 사용자정보


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try { // 인가를 할때 문제가 발생할 수 있기때문에 try-catch 사용
            // 요청 헤더에서 토큰 추출
            String jwt = getJwtFromRequest(request); // 쿠키만 추출하는 함수

            // 검증
            if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                // jwt에
                // 토큰에서 로그인아이디 추출
                String loginId = jwtTokenProvider.getLoginIdFromToken(jwt); // 로그인아이디를 꺼내오는 함수 실행
                log.info("여기는 필터체인 토큰의 로그인 아이디 추출 : {}", loginId);

                // DB에서 사용자 정보 조회
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginId);
                // loginId로 DB에서 사용자를 찾아서 반환하는 타입을 UserDetails로 변환 *UserDetailService의 오버라이드 기능.
                log.info("여기는 필터체인 DB에서 사용자정보 추출 : {}", userDetails);

                // Authentication 객체 생성
                // 세션 방식과 동일하게 authentication에 담을 객체를 생성해준다.
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, // 유저정보
                                null, // 인증정보
                                userDetails.getAuthorities() // 권한정보
                        );

                // 요청정보를 Authentication에 추가해야한다.
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                        // 보안규정 준수를 위한 기록하기 위한 로직.
                        // 해당 로직은 비정상적인 접근에 대한 IP와 인증에 대한 로그를 저장하는기능과 같다.
                );

//                SecurityContextHolder에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("JWT 인증 성공 => 로그인아이디 : {}", loginId);

            }
        } catch(InvalidTokenException e) {
            log.error("JWT 인증 처리 중 오류 발생 :{} ", e.getMessage());
            request.setAttribute("exception",e);

        } catch ( Exception e) {
            log.error("JWT 인증 처리 중 예상치 못한 오류 발생:", e);
        }

        filterChain.doFilter(request,response);

    }

    // 요청에서 JWT 토큰을 꺼내오는 메서드
    private String getJwtFromRequest (HttpServletRequest request) {

        // HTTP ONLY 쿠키 방식 꺼내오기.
        Cookie[] cookies = request.getCookies(); // 요청에서 쿠키를 꺼내면 쿠키는 배열형태 여러가지 쿠키들이 있음
        if( cookies != null) { // 꺼낸 쿠키배열이 null이 아니라면
            for (Cookie cookie : cookies) { // 반복문으로 cookie들을 enhance 문으로 실행
                if("accessToken".equals(cookie.getName())){ // accessToken과 쿠키의 이름이 같으면
                    String jwtToken = cookie.getValue(); // 해당 쿠키를 꺼내서 jwtToken으로 저장
                    log.info("여기는 쿠키에 있는 토큰 추출 완료:{}", jwtToken); 
                    return jwtToken; // 해당 토큰을 반환
                }
            }
        }
        log.info("1) 여기는 JWT 토큰 추출 실패: 쿠키나 Authorization 헤더에서 토큰을 찾지 못함 ");
        log.debug("2) 여기는 JWT 토큰 추출 실패: 쿠키나 Authorization 헤더에서 토큰을 찾지 못함 ");
        return null;
    }

}
