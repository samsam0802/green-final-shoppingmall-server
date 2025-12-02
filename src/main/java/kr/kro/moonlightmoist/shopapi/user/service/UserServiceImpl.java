package kr.kro.moonlightmoist.shopapi.user.service;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.*;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User registerUser(UserSignUpRequest userSignUpRequest) {

        return User.builder()
                .loginId(userSignUpRequest.getLoginId())
                .password(userSignUpRequest.getPassword())
                .name(userSignUpRequest.getName())
                .phoneNumber(userSignUpRequest.getPhoneNumber())
                .email(userSignUpRequest.getEmail())
                .birthDate(userSignUpRequest.getBirthDate())
                .postalCode(userSignUpRequest.getPostalCode())
                .address(userSignUpRequest.getAddress())
                .addressDetail(userSignUpRequest.getAddressDetail())
                .emailAgreement(userSignUpRequest.isEmailAgreement())
                .smsAgreement(userSignUpRequest.isSmsAgreement())
                .build();
    }

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> findLoginId = userRepository.findByLoginId(userLoginRequest.getLoginId());
        if (findLoginId.isPresent()) {
            User user = findLoginId.get();

            if (!user.getPassword().equals(userLoginRequest.getPassword())) {
                throw new IllegalArgumentException("비밀번호가 일치 하지 않습니다.");
            }
            return UserLoginResponse.builder()
                    .id(user.getId())
                    .loginId(user.getLoginId())
                    .name(user.getName())
                    .build();
        }
        throw new IllegalArgumentException("없는 아이디 입니다.");
    }

    @Override
    public boolean checkLoginId(String loginId) {
        System.out.println("중복확인 Service");
        System.out.println("조회한 loginId :" + loginId);
        Optional<User> findUser = userRepository.findByLoginId(loginId);
        if (findUser.isPresent()) {
            System.out.println("DB에 해당 유저정보가 있습니다." + findUser.get().getLoginId());
            return true;
        } else {
            System.out.println("DB에 해당 유저정보가 없습니다");
            return false;
        }
    }

    @Override
    public UserProfileResponse getUserProfile(String loginId) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);
        if (findUser.isPresent()) {
            User user = findUser.get();
            return UserProfileResponse.builder()
                    .id(user.getId())
                    .loginId(user.getLoginId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .birthDate(user.getBirthDate())
                    .postalCode(user.getPostalCode())
                    .address(user.getAddress())
                    .addressDetail(user.getAddressDetail())
                    .smsAgreement(user.isSmsAgreement())
                    .emailAgreement(user.isEmailAgreement())
                    .build();
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다 :" + loginId);
        }
    }


//    @Override
//    public UserModifyResponse modifyUserProfile(UserModifyRequest userModifyRequest) {
//        Optional<User> findUser = userRepository.findByLoginId(userModifyRequest.getLoginId());
//
//        return null;
//    }


}
