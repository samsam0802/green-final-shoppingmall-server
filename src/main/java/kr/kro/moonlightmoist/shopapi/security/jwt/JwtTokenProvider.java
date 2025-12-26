package kr.kro.moonlightmoist.shopapi.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.kro.moonlightmoist.shopapi.security.CustomUserDetails;
import kr.kro.moonlightmoist.shopapi.user.exception.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    // application.properties 에서 값을 가져오는 어노테이션
    @Value("${jwt.secret}")
    private String secretKey; // JWT 서명에 사용할 비밀키

    @Value("${jwt.access-expiration}")
    private Long accessTokenValidityInMilliseconds; // 엑세스토큰 유효시간 ( 밀리초 )

    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenValidityInMilliseconds; // 리프레쉬토큰 유효시간 ( 밀리초 )

    // 사용자정보 Authentication(인증)을 파라미터로 받아야 해당 정보를 가지고 토큰을 생성 할 수 있다.
    // 현재 시간과 만료시간을 지정해야한다.
    // JWT의 구조 헤더,페이로드,시그니쳐를 주입해야한다.

    // 이 시점의 authentication 안에는:
    // - principal: CustomUserDetails 객체
    // - credentials: 비밀번호 (보안상 제거됨)
    // - authorities: [ROLE_USER]
    
    
    //엑세스 토큰 생성
    public String generateAccessToken (Authentication authentication) {

        // 인증 데이터에서CustomUserDetails를 꺼낸다. authentication은 Object Type이라서, 다운캐스팅 적용
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Date now = new Date(); // 지금 현재 시간
        // 현재시간 + 유효기간시간 = 남은시간 적용
        Date expiryDate = new Date(now.getTime() + accessTokenValidityInMilliseconds); // 30분

        // 토큰생성
        return Jwts.builder() // 해당 정보로 토큰을 생성해야하기 때문
                .subject(userDetails.getUsername()) // 토큰의 주인 (loginId) 정보 subject : 대상, 주체
                .claim("userId", userDetails.getUser().getId()) // 키,밸류 claim: 추가정보 (DB의 PK) 담고싶은 정보(이름,권한 등) ✅ DB 조회용
                .claim("name", userDetails.getUser().getName()) // 사용자 이름  ✅ 비밀번호는 절대 안됨. 디코딩하면 누구나 볼 수 있음. ✅ 화면표시용
                .claim("role", userDetails.getUser().getUserRole().name()) // 권한 ✅ 권한 검증용
                .issuedAt(now) // 토큰 발급시간 ✅ 해당 토큰이 언제 만들어졌는지 = 생성시간을 현재시간기준으로!
                .expiration(expiryDate) // 토큰 만료시간 ✅ 언제까지 유효한지 밀리초 적용
                .signWith(getSigningKey()) // 서명 (변조 방지?) ✅ 토큰이 변조되지 않았음을 증명하는 장치 서버만 가진 비밀키로 검증하기 위함
                .compact(); // 문자열로 반환하는 메서드
    }

    // 리프레시 토큰생성
    public String generateRefreshToken(Authentication authentication) {

        CustomUserDetails userDetails  = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidityInMilliseconds); // 1일

        return Jwts.builder()
                .subject(userDetails.getUser().getLoginId()) // 로그인한 사용자
                .claim("userId",userDetails.getUser().getId()) // 로그인한 사용자의 id (pk)
                .issuedAt(now) // 생성 시간
                .expiration(expiryDate) // 유효 시간
                .signWith(getSigningKey()) // 키 검증
                .compact();
    }

    // 로그인Id 추출로직? ✅ 어떤 사용자의 토큰인지
    // 토큰을 파라미터로 받는다.
    // 문자열을 반환한다.
    public String getLoginIdFromToken (String token){
        // JWT 파싱 → Claims (페이로드) 추출 → Subject반환
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey()) // 서명 검증 메서드
                .build() // ???
                .parseSignedClaims(token) // 토큰 파싱?
                .getPayload();// Payload 부부만 추출하는 메서드

        return claims.getSubject(); // loginId
    }

    // 유효성 검증
    // 검증할 토큰 파라미터
    // 검증로직이라 반환값은 true, false
    public boolean validateToken (String token) {
        try {
            Jwts.parser() // 파싱할 토큰
                    .verifyWith(getSigningKey()) // 서명 검증 메서드
                    .build() // 만들다?
                    .parseSignedClaims(token); // 토큰 파싱 메서드
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰");
        } catch (InvalidTokenException e) {
            log.error("JWT 토큰이 잘못되었습니다");
        }
        return false;
    }

    // 비밀키를 SecretKey 객체로 변환 로직
    private SecretKey getSigningKey () { // 시크릿키 사용
        // 문자열을 → 바이트배열로 변환 ✅ 왜 바이트로? → 암호화 알고리즘은 바이트 단위로 동작하기 때문
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8); // 필드의 시크릿키를 getByte()메서드로 어떤 문자열로 변환할것인지.
        // 그 시크릿키를 keyBytes 변수에 담음.
        // 바이트 배열을 SecretKey 객체로 변환↓
        return Keys.hmacShaKeyFor(keyBytes);
        // JJWT 라이브러리에서 제공하는 유틸리티 클래스 ✅ 암호화 키를 생성/변환하는 도구 HMAC-SHA 알고리즘용 SecretKey 객체를 생성하는 메서드
    }

}
