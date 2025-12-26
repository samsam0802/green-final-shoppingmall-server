package kr.kro.moonlightmoist.shopapi.user.service;

import com.sun.jdi.request.DuplicateRequestException;
import kr.kro.moonlightmoist.shopapi.common.exception.BusinessException;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.dto.*;
import kr.kro.moonlightmoist.shopapi.user.exception.*;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserSignUpRequest userSignUpRequest) {
        if(userRepository.existsByLoginId(userSignUpRequest.getLoginId())){
            throw new DuplicateLoginIdException();
        }

        String encodePassword = passwordEncoder.encode(userSignUpRequest.getPassword());

        log.info("PasswordEncoding 출력 : {} ", encodePassword);

        return User.builder()
                .loginId(userSignUpRequest.getLoginId())
                .password(encodePassword)
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
    public boolean checkLoginId(String loginId) {
        log.info("중복확인 Service 로그인아이디 : {}", loginId);
        return userRepository.existsByLoginId(loginId);
    }

    @Override
    public UserProfileResponse getUserProfile(String loginId) {
        User findUser = userRepository.findByLoginId(loginId)
                .orElseThrow(UserNotFoundException::new);

            return UserProfileResponse.builder()
                    .loginId(findUser.getLoginId())
                    .name(findUser.getName())
                    .email(findUser.getEmail())
                    .phoneNumber(findUser.getPhoneNumber())
                    .birthDate(findUser.getBirthDate())
                    .postalCode(findUser.getPostalCode())
                    .address(findUser.getAddress())
                    .addressDetail(findUser.getAddressDetail())
                    .smsAgreement(findUser.isSmsAgreement())
                    .emailAgreement(findUser.isEmailAgreement())
                    .build();
    }


    @Override
    public UserModifyResponse modifyUserProfile(UserModifyRequest userModifyRequest) {
        try {
            User user = userRepository.findByLoginId(userModifyRequest.getLoginId())
                    .orElseThrow(() -> new UserNotFoundException());
            // 전형적인 예외처리.
            if (!passwordEncoder.matches(userModifyRequest.getPassword(), user.getPassword())) {
                throw new InvalidPasswordException();
                // 해당 유저의 비밀번호를 equals으로 Request form에 있는 비밀번호와 대조
                // 틀릴 경우 UserModifyResponse 객체를 새로 생성해서 기본생성자로 필드 값을 저장
            }
            // 맞을 경우 아래의 코드로 넘어감.
            user.updateProfile(userModifyRequest);
            // user안에 정적메서드 updateProfile을 호출 userModifyRequest를 전달
            // 해당 메서드 안에서 user의 필드값들을 저장
            User updateUser = userRepository.save(user);
            // DB에 변경된 user를 저장 후 꺼내옴.

            UserProfileResponse profileResponse = UserProfileResponse.toUserProfileResponse(updateUser);
            // UserProfileResponse의 from 메서드를 호출함과 동시에 업데이트 된 유저를 전달
            // 반환된 UserProfileResponse를 profileResponse에 할당
            return new UserModifyResponse(true, "개인정보 수정이 완료 되었습니다.", profileResponse);
            // 반환값을 UserModifyResponse 객체를 생성하여 필드값에 할당
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new UserModificationException();
        }
    }

    @Override
    public PasswordChangeResponse changeUserPassword(PasswordChangeRequest passwordChangeRequest) {
        try {
            User user = userRepository.findByLoginId(passwordChangeRequest.getLoginId())
                    .orElseThrow(() -> new UserNotFoundException());

            if (!passwordEncoder.matches(passwordChangeRequest.getPassword(), user.getPassword())) {
                throw new InvalidPasswordException();
            }

            String encodeNewPassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
            user.changePassword(encodeNewPassword);

            userRepository.save(user);
            return new PasswordChangeResponse(true, "비밀번호 변경 완료");
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new PasswordChangeException();
        }
    }


}
