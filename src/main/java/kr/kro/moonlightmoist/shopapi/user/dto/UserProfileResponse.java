package kr.kro.moonlightmoist.shopapi.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponse {

    private Long id;
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

}
