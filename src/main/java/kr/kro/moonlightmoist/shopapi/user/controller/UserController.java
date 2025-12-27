package kr.kro.moonlightmoist.shopapi.user.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import kr.kro.moonlightmoist.shopapi.security.CustomUserDetails;
import kr.kro.moonlightmoist.shopapi.security.jwt.JwtTokenProvider;
import kr.kro.moonlightmoist.shopapi.security.jwt.RefreshToken;
import kr.kro.moonlightmoist.shopapi.security.jwt.RefreshTokenRepository;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.*;
import kr.kro.moonlightmoist.shopapi.user.exception.DuplicateLoginIdException;
import kr.kro.moonlightmoist.shopapi.user.exception.InvalidTokenException;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.user.service.UserService;
import kr.kro.moonlightmoist.shopapi.user.service.UserWithdrawalService;
import kr.kro.moonlightmoist.shopapi.usercoupon.service.UserCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor // Final 생성
@RequestMapping("/api/user") // 해당 컨트롤러가 받을 경로
@Slf4j
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserWithdrawalService userWithdrawalService;
    private final AuthenticationManager authenticationManager; // 12-10 추가
    private final JwtTokenProvider jwtTokenProvider; // 12-12 추가
    private final UserCouponService userCouponService;
    private final RefreshTokenRepository refreshTokenRepository;


    @PostMapping("/signup") // RequestMapping + ??
    public ResponseEntity<Map<String,Object>> userResister(@RequestBody UserSignUpRequest userSignUpRequest) {
        // @RequestBody JSON 데이터를 Java 객체로 자동 변환해주는 어노테이션
        try {
            User registeredUser = userRepository.save(userService.registerUser(userSignUpRequest));
            Long registeredCouponUser = userCouponService.issue(registeredUser.getId(), 1L);
            log.info("회원가입 컨트롤러 신규쿠폰 유저 등록완료 : {} ", registeredCouponUser);
            log.info("회원가입 컨트롤러 신규쿠폰 등록완료된 유저는 : {} ", registeredUser.getLoginId());
            System.out.println("======================================================================");
            log.info("유저정보 Controller => {}", userSignUpRequest);
            log.info("DB에서 꺼낸 저장된 정보 => {}", registeredUser);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "회원가입이 완료되었습니다.");
            response.put("coupon", "💕신규쿠폰이 발급되었습니다💕");
            return ResponseEntity.ok(response);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("회원가입 처리중 오류가 발생 하였습니다");
        }
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> Login (@RequestBody UserLoginRequest userLoginRequest,
                                                      HttpServletResponse httpServletResponse) {
        log.info("로그인 요청 : {}", userLoginRequest.getLoginId());

//        Authentication은 스프링 시큐리티에서 **'인증(Authentication)에 대한 모든 정보'**를 담는 최상위 개념의 인터페이스
//        authenticate( 인증을 시작하는 핵심 메서드 ) 인증요청 객체를 받아서 해당 요청이 유효한지 확인하고 인증된 객체를 반환해준다.
//        인터페이스는 두 가지 상태를 표현하기 위해 사용된다. 1. 인증요청 2. 인증완료

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getLoginId(),
                            userLoginRequest.getPassword()
                    )
            );

//        SecurityContext에 저장하기.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtTokenProvider.generateAccessToken(authentication); // 사용자정보로 엑세스토큰 생성
            String refreshToken = jwtTokenProvider.generateRefreshToken(authentication); // 사용자정보로 리프레시토큰 생성

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            // 해당 정보의 사용자정보를 다운캐스팅해서 꺼냄 *Principal의 경우 일반적으로 Object 타입

            log.info("로그인 성공 로그인아이디: {}, JWT 생성 및 발급 완료", userDetails.getUser().getLoginId());

            // 기존 코드 주석 처리함 by 병국
            //Cookie accesscookie = new Cookie("accessToken", accessToken); // 해당 정보를 가진 쿠키를 생성
            //accesscookie.setHttpOnly(true); // JavaScript 접근 불가
            //accesscookie.setSecure(false); // HTTPS true/false로 설정
            //accesscookie.setPath("/"); // 모든경로
            //accesscookie.setMaxAge(60 * 30); // 30분 설정 만료일 설정
            //httpServletResponse.addCookie(accesscookie); // 해당 repsonse에 쿠키를 추가

            //Cookie refreshcookie = new Cookie("refreshToken", refreshToken); // 해당 정보를 가진 쿠키 생성
            //refreshcookie.setHttpOnly(true); // JavaScript 접근 불가
            //refreshcookie.setSecure(false); // HTTPS true/false로 설정
            //refreshcookie.setPath("/"); // 모든경로
            //refreshcookie.setMaxAge(60 * 60 * 24); // 1일 설정 만료일 설정
            //httpServletResponse.addCookie(refreshcookie); // 해당 response에 쿠키를 추가

            // 쿠키 설정 (ResponseCookie 사용)
            setTokenCookies(httpServletResponse, accessToken, refreshToken);

            // 기존에 있던 Token 삭제. 
            refreshTokenRepository.deleteByUserId(userDetails.getUser().getId());

            // 방금 생성한 토큰 저장
            refreshTokenRepository.save(new RefreshToken(
                    userDetails.getUser().getId(),
                    refreshToken,
                    LocalDateTime.now()
            ));

//         응답 로직
            Map<String, Object> LoginResponse = new HashMap<>();
            LoginResponse.put("success", true);
            LoginResponse.put("message", "로그인 성공");
            LoginResponse.put("user", UserLoginResponse.builder()
                    .id(userDetails.getUser().getId())
                    .loginId(userDetails.getUsername())
                    .name(userDetails.getUser().getName())
                    .userRole(userDetails.getUser().getUserRole())
                    .build());
            return ResponseEntity.ok(LoginResponse);

        } catch (AuthenticationException e) {
            log.info("로그인 실패 여기는 catch LoginId : {}, Error 사유: {}", userLoginRequest.getLoginId(), e.getMessage());

            Map<String, Object> response = new HashMap<>();
            response.put("success",false);
            response.put("message","아이디 또는 비밀번호가 일치하지 않습니다.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    // 토큰 만료 시, 리프레시 토큰 재발급.
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refresh(
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            log.info("토큰 갱신 요청");


            // 리퀘스트에서 쿠키의 토큰을 추출
            String refreshToken = getRefreshTokenFromCookie(request);

            // 추출한 토큰이 없다면 예외
            if (refreshToken == null){
                log.warn("Refresh Token이 없습니다.");
                throw new InvalidTokenException(" 존재하지 않는 Token 입니다.");
            }
            // 토큰이 있을경우
            // 해당 토큰의 유효성을 검사
            if (!jwtTokenProvider.validateToken(refreshToken)) {
                throw new InvalidTokenException();
            }

            // 유효성 검사까지 완료되었다면 토큰을 찾아서 꺼내온다.
            RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken).orElse(null);

            // DB에서 꺼낸 토큰이 없다면
            if (storedToken == null) {
                log.info("DB에 존재하지않는 Refresh Token 입니다.");
                throw new InvalidTokenException("DB에 존재하지않는 Refresh Token 입니다.");
            }

            if ( storedToken.isExpired()) {
                log.warn("만료된 refresh Token 입니다.");
                refreshTokenRepository.delete(storedToken);
                throw new InvalidTokenException("만료된 Token 입니다");
            }

            //사용자정보 조회해서 DB에서 꺼내오기
            User findUser = userRepository.findById(storedToken.getUserId()).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
            CustomUserDetails userDetails = new CustomUserDetails(findUser);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            log.info("새 토큰 발급 완료 : userId = {}", findUser.getLoginId());

            refreshTokenRepository.delete(storedToken);

            refreshTokenRepository.save(new RefreshToken(
                    findUser.getId(),
                    newRefreshToken,
                    LocalDateTime.now()
            ));

            // 새 토큰을 쿠키에 설정
            setTokenCookies(response, newAccessToken, newRefreshToken);
//            Cookie newAccessCookie = new Cookie("accessToken", newAccessToken);
//            newAccessCookie.setHttpOnly(true);
//            newAccessCookie.setSecure(false);
//            newAccessCookie.setPath("/");
//            newAccessCookie.setMaxAge(60 * 30); // 30분
//            response.addCookie(newAccessCookie);
//
//            Cookie newRefreshCookie = new Cookie("refreshToken", newRefreshToken);
//            newRefreshCookie.setHttpOnly(true);
//            newRefreshCookie.setSecure(false);
//            newRefreshCookie.setPath("/");
//            newRefreshCookie.setMaxAge(60 * 60 * 24 ); // 1일
//            response.addCookie(newRefreshCookie);

            // 응답
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "토큰이 갱신되었습니다.");

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("토큰 갱신 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpServletResponse httpServletResponse) {

        log.info("로그아웃 요청 호출");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // authentication == null 로 되어있던 부분 != 로 수정 by 병국
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            refreshTokenRepository.deleteByUserId(userDetails.getUser().getId());
        }

        // 쿠키 삭제
        deleteTokenCookies(httpServletResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "로그아웃 되었습니다.");
        log.info("로그아웃 완료 및 쿠키 삭제 완료");

        return ResponseEntity.ok(response);
    }



    @GetMapping("/currentUser")
    public ResponseEntity<Map<String, Object>> currentUser () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if( authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println("여기는 currentUser 컨트롤러");

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("여기는 로그인된 사용자정보 불러오기 : {}", userDetails);
        log.info("여기는 로그인된 사용자정보 불러오기 : {}", userDetails.getUsername());
        log.info("여기는 로그인된 사용자정보 불러오기 : {}", userDetails.getUser().getId());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("user", UserLoginResponse.builder()
                .id(userDetails.getUser().getId())
                .loginId(userDetails.getUser().getLoginId())
                .name(userDetails.getUser().getName())
                .userRole(userDetails.getUser().getUserRole())
                .build());
        log.info("여기는 로그인된 사용자정보 반환: {}",response);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-loginId")
    public ResponseEntity<LoginIdCheckResponse> checkLoginId (@RequestParam String loginId) {
        // @requestParam = QueryParameter 값을 추출해서 해당 인자 String loginId로 변환해서 받는다.
        // 프론트 요청에서 Params로 보냈기때문에 즉, 쿼리파라미터로 보냈기 때문에 해당 어노테이션 사용
        boolean isDuplicate = userService.checkLoginId(loginId); // service의 existsByLoginId 메서드 사용
        String message = isDuplicate ? "이미 사용 중인 아이디입니다." : "사용 가능한 아이디 입니다.";

        LoginIdCheckResponse response = new LoginIdCheckResponse(isDuplicate, message);
        // true / false만 반환하는 대신에 중복여부확인과 프론트에 전달할 메세지까지 객체형태로 담아서 전달
        return ResponseEntity.ok(response);
    }


    // @RequestParam 방식은 쿼리파라미터를 보내는 방식으로 REST API 원칙과는 다른방식
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/profile/{loginId}")
    public ResponseEntity<UserProfileResponse> getUserProfile (@PathVariable String loginId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("여기는 프로필조회 인증/인가 확인결과 :{}", authentication);
        UserProfileResponse profileResponse = userService.getUserProfile(loginId);
        return ResponseEntity.ok(profileResponse);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/profile-modify")
    public ResponseEntity<UserModifyResponse> modifyUserProfile (@RequestBody UserModifyRequest userModifyRequest) {
        UserModifyResponse response = userService.modifyUserProfile(userModifyRequest);
        if(!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
        }


    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/password-change")
    public ResponseEntity<PasswordChangeResponse> changeUserPassword (@RequestBody PasswordChangeRequest request) {
        PasswordChangeResponse response = userService.changeUserPassword(request);
        if(!response.isSuccess()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/withdraw")
    public ResponseEntity<UserWithdrawalResponse> withdrawUser (@RequestBody UserWithdrawalRequest request) {
        UserWithdrawalResponse response = userWithdrawalService.withdrawUser(request);
        log.info("여기는 회원탈퇴 컨트롤러 : {} ", response);
        return ResponseEntity.ok(response);
    }


    private void setTokenCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        // 배포 환경에서는 secure(true), sameSite("None") 필수
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .path("/")
                .httpOnly(true)
                .secure(true) // HTTPS 필수
                .sameSite("None")
                .maxAge(60 * 30)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .path("/")
                .httpOnly(true)
                .secure(true) // HTTPS 필수
                .sameSite("None")
                .maxAge(60 * 60 * 24)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private void deleteTokenCookies(HttpServletResponse response) {
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

    private String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    log.info("Refresh Token 추출 완료");
                    return cookie.getValue();
                }
            }
        }
        log.warn("쿠키에서 Refresh Token을 찾을 수 없습니다.");
        return null;
    }


}
