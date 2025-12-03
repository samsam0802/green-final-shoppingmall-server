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


    @Override
    public UserModifyResponse modifyUserProfile(UserModifyRequest userModifyRequest) {
        User user = userRepository.findByLoginId(userModifyRequest.getLoginId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        // 전형적인 예외처리.
        if(!user.getPassword().equals(userModifyRequest.getPassword())) {
            return new UserModifyResponse(false, "비밀번호가 일치하지 않습니다.", null);
            // 해당 유저의 비밀번호를 equals으로 Request form에 있는 비밀번호와 대조
            // 틀릴 경우 UserModifyResponse 객체를 새로 생성해서 기본생성자로 필드 값을 저장
        }
        // 맞을 경우 아래의 코드로 넘어감.
        user.updateProfile(userModifyRequest);
        // user안에 정적메서드 updateProfile을 호출 userModifyRequest를 전달
        // 해당 메서드 안에서 user의 필드값들을 저장
        User updateUser = userRepository.save(user);
        // DB에 변경된 user를 저장 후 꺼내옴.

        UserProfileResponse profileResponse = UserProfileResponse.from(updateUser);
        // UserProfileResponse의 from 메서드를 호출함과 동시에 업데이트 된 유저를 전달
        // 반환된 UserProfileResponse를 profileResponse에 할당
        return new UserModifyResponse(true, "개인정보 수정이 완료 되었습니다.", profileResponse);
        // 반환값을 UserModifyResponse 객체를 생성하여 필드값에 할당
    }


}
