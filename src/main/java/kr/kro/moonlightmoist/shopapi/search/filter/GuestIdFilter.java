package kr.kro.moonlightmoist.shopapi.search.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class GuestIdFilter implements Filter {

    private static final String GUEST_COOKIE_NAME = "GUEST_ID";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        //ServletRequest = HTTP 요청 정보
        //ServletResponse = HTTP 응답 정보
        //FilterChain = 다음 필터/컨트롤러로 요청을 전달할 때 사용

        //ServletRequest, ServletResponsesms는 일반적인 인터페이스
        //HTTP 전용 메서드를 사용하려면 형변환 필요
        //ServletRequest (인터페이스), HttpServletRequest (인터페이스, ServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //쿠키에서 GUEST_ID 확인
        //getCookieValue 메서드를 호출해서 요청 쿠키 중 "GUEST_ID" 값을 가져옴
        //비회원 식별용 UUID가 이미 존재하는지 확인
        String guestId = getCookieValue(httpRequest, GUEST_COOKIE_NAME);

        //쿠키가 존재하지 않으면 새로운 UUID 생성 (무작위 고유 문자열로 생성)
        if (guestId == null) {
            guestId = UUID.randomUUID().toString();

            //쿠키 생성 후 응답에 추가하는 과정
            Cookie cookie = new Cookie(GUEST_COOKIE_NAME, guestId); //쿠키 이름, 값 설정
            cookie.setHttpOnly(true); //JavaScript에서 접근 불가, 서버만 사용 가능
            cookie.setPath("/"); //모든 경로에서 사용 가능
            cookie.setMaxAge(60 * 60 * 24); //유효기간 1일(24시간)
            httpResponse.addCookie(cookie); //브라우저에 쿠키 전달
        }

        //request attribute에 저장
        httpRequest.setAttribute("guestId", guestId);

        //이후 필터 체인 계속 진행
        //다음 필터 또는 Controller로 요청 전달
        filterChain.doFilter(request, response);
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        //요청 쿠키 중에서 특정 이름(name)을 가진 쿠키 값을 가져오는 메서드
        //쿠키 배열을 순회하면서 이름이 일치하면 값 반환하고, 없으면 null로 반환
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
