package kr.kro.moonlightmoist.shopapi.user.service;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.UserLoginRequest;
import kr.kro.moonlightmoist.shopapi.user.dto.UserLoginResponse;
import kr.kro.moonlightmoist.shopapi.user.dto.UserSignUpRequest;
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

            if(!user.getPassword().equals(userLoginRequest.getPassword())) {
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
        if(findUser.isPresent()){
            System.out.println("DB에 해당 유저정보가 있습니다." + findUser.get().getLoginId());
            return true;
        } else {
            System.out.println("DB에 해당 유저정보가 없습니다");
            return false;
        }
    }


}
