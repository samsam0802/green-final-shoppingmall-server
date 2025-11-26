package kr.kro.moonlightmoist.shopapi.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserSignUpRequest {

    @NotBlank(message = "로그인 아이디는 필수 입니다.")
    @Size(min = 4, max = 15, message = "로그인 아이디는 4 ~ 20자 이어야 합니다.")
    private String loginId; // 로그인아이디

    @NotBlank(message = "비밀번호는 필수 입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password; // 비밀번호

    @NotBlank(message = "이름은 필수 입니다.")
    private String name; // 사용자 이름

    @NotBlank(message = "연락처는 필수 입니다.")
    @Size(min = 11)
    @Pattern(regexp = "^01[0-9]\\d{7,8}$", message = "올바른 휴대전화 번호 방식이 아닙니다.")
    private String phoneNumber; // 연락처


    @NotBlank(message = "이메일은 필수 입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;  // 이메일

    @NotBlank(message = "생년월일은 필수 입니다.")
    @Past(message = "생년월일은 과거 날짜여야 합니다")
    private LocalDate birthDate;  // 생년월일

    @NotBlank(message = "우편번호는 필수 입니다.")
    private String postalCode; // 우편번호

    @NotBlank(message = "기본주소는 필수 입니다.")
    private String address; // 기본주소

    @NotBlank(message = "상세주소는 필수 입니다.")
    private String addressDetail; // 상세주소

    private boolean emailAgreement; // 이메일 마케팅 동의 알림

    private boolean smsAgreement; // SMS 마케팅 동의 알림


}

// ❌ 빼야 하는 것들:
// private Long id;              // 자동 생성됨
// private boolean deleted;      // 기본값 false
// private LocalDate deletedAt;  // null
// private UserRole userRole;    // 서버에서 지정
// private UserGrade userGrade;  // 서버에서 지정
