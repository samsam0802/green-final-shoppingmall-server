package kr.kro.moonlightmoist.shopapi.user.controller;


import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.*;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor // Final 생성
@RequestMapping("/api/user") // 해당 컨트롤러가 받을 경로
@Slf4j
@CrossOrigin(origins = "*", // 교차 출처 -> 기본적인 보안정책
        allowedHeaders = "*",   // 어떤해더를 받을것인지.
        methods = {RequestMethod.GET,   // 허용할 메서드
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.PATCH})

public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/signup") // RequestMapping + ??
    public ResponseEntity<String> userResister(@RequestBody UserSignUpRequest userSignUpRequest) {
        // @RequestBody JSON 데이터를 Java 객체로 자동 변환해주는 어노테이션
        User registeredUser = userRepository.save(userService.registerUser(userSignUpRequest));
        log.info("유저정보 Controller => {}"  ,userSignUpRequest);
        log.info("DB에서 꺼낸 정보 => {}"  ,registeredUser);
        return ResponseEntity.ok("성공");
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> Login (@RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResponse userLoginResponse = userService.login(userLoginRequest);
        log.info("유저정보 Response: {}", userLoginResponse);
        return ResponseEntity.ok(userLoginResponse);
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

    // Todo : REST API 원칙으로 @PathVariable 사용해서 변경하기.
    // @RequestParam 방식은 쿼리파라미터를 보내는 방식으로 REST API 원칙과는 다른방식
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile (@RequestParam String loginId) {
        UserProfileResponse profileResponse = userService.getUserProfile(loginId);
        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping("/profile-modify")
    public ResponseEntity<UserModifyResponse> modifyUserProfile (@RequestBody UserModifyRequest userModifyRequest) {
        UserModifyResponse response = userService.modifyUserProfile(userModifyRequest);
        if(!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
        }


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




}
