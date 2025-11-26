package kr.kro.moonlightmoist.shopapi.user.controller;


import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.UserLoginRequest;
import kr.kro.moonlightmoist.shopapi.user.dto.UserLoginResponse;
import kr.kro.moonlightmoist.shopapi.user.dto.UserSignUpRequest;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // Final 생성
@RequestMapping("/api/user") // 해당 컨트롤러가 받을 경로
@Slf4j
@CrossOrigin(origins = "*", // 교차 출처 -> 기본적인 보안정책
        allowedHeaders = "*",   // 어떤해더를 받을것인지.
        methods = {RequestMethod.GET,   // 허용할 메서드
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE})

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




}
