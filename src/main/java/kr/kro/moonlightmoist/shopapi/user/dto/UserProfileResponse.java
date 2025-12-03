package kr.kro.moonlightmoist.shopapi.user.dto;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {

    private String loginId;
    private String name;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private String postalCode;
    private String address;
    private String addressDetail;
    private boolean emailAgreement;
    private boolean smsAgreement;

    public static UserProfileResponse from (User user) {
        return UserProfileResponse.builder()
                .loginId(user.getLoginId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .postalCode(user.getPostalCode())
                .address(user.getAddress())
                .addressDetail(user.getAddressDetail())
                .emailAgreement(user.isEmailAgreement())
                .smsAgreement(user.isSmsAgreement())
                .build();
    }

}
