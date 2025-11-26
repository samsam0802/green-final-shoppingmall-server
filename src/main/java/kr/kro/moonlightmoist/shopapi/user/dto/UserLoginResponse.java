package kr.kro.moonlightmoist.shopapi.user.dto;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginResponse {

    private Long id;
    private String loginId; // 로그인아이디
    private String name; // 사용자이름

}
