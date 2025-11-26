package kr.kro.moonlightmoist.shopapi.user.dto;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginRequest {

    private String loginId; // 로그인아이디
    private String password; // 비밀번호

}
