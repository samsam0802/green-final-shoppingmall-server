package kr.kro.moonlightmoist.shopapi.user.repository;


import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class UserRepositoryUnitTest { // 생성, 삭제, 수정, 제약조건

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저생성 테스트")
    public void createUser() {

        User newUser = User.builder()
                .loginId("user")
                .passwordHash("123123")
                .name("유저")
                .email("user@naver.com")
                .phoneNumber("01012345678")
                .postalCode("11111")
                .address("성남")
                .addressDetail("분당")
                .birthDate(LocalDate.of(2025,11,11))
                .emailAgreement(true)
                .smsAgreement(false)
                .userGrade(UserGrade.BRONZE)
                .userRole(UserRole.USER)
                .build();

        User savedUser = userRepository.save(newUser);

        assertThat(savedUser.getLoginId()).isEqualTo("user");
        assertThat(savedUser.getPasswordHash()).isEqualTo("123123");
        assertThat(savedUser.getName()).isEqualTo("유저");
        assertThat(savedUser.getEmail()).isEqualTo("user@naver.com");
        assertThat(savedUser.getPhoneNumber()).isEqualTo("01012345678");
        assertThat(savedUser.getPostalCode()).isEqualTo("11111");
        assertThat(savedUser.getAddress()).isEqualTo("성남");
        assertThat(savedUser.getAddressDetail()).isEqualTo("분당");
        assertThat(savedUser.getBirthDate()).isEqualTo(LocalDate.of(2025,11,11));
        assertThat(savedUser.isEmailAgreement()).isTrue();
        assertThat(savedUser.isSmsAgreement()).isFalse();
        assertThat(savedUser.getUserGrade()).isEqualTo(UserGrade.BRONZE);
        assertThat(savedUser.getUserRole()).isEqualTo(UserRole.USER);
    }



    @Test
    @DisplayName("로그인 Id로 유저 조회하기")
    public void findByLoginId() {
        User user = EntityFactory.createUser();
        User savedUser = userRepository.save(user);


        Optional<User> testUser = userRepository.findByLoginId("user");

        assertThat(testUser).isPresent();
        assertThat(testUser.get().getLoginId()).isEqualTo("user");
    }

    @Test
    @DisplayName("중복 로그인Id 저장테스트")
    public void duplicateLoginId() {

        User user = EntityFactory.createUser();

        User savedUser = userRepository.save(user);

        User testUser = User.builder()
                .name("")
                .email("")
                .loginId("user")
                .userRole(UserRole.USER)
                .userGrade(UserGrade.BRONZE)
                .birthDate(LocalDate.of(2025,11,11))
                .smsAgreement(true)
                .emailAgreement(true)
                .addressDetail("")
                .address("")
                .phoneNumber("")
                .passwordHash("")
                .postalCode("")
                .deleted(true)
                .deletedAt(LocalDate.of(2025,11,11))
                .build();

//        예외처리 로직
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(testUser);
        }, " 중복된 LoginId 발생 시, DataIntegrityViolationException이 발생하여 예외 처리함");
    }


    @Test
    @DisplayName("유저 삭제테스트")
    public void deleteUser() {
        User user = EntityFactory.createUser();
        User savedUser = userRepository.save(user);

        userRepository.deleteById(user.getId());

        assertThat(userRepository.findById(savedUser.getId())).isEmpty(); // 삭제 했을때
//        assertThat(userRepository.findById(savedUser.getId())).isNotEmpty(); // 등록 했을때

    }

    @Test
    @DisplayName("유저 수정테스트")
    public void ModifyUserAddressAndAgreement() { // 유저를 수정

        User user = EntityFactory.createUser(); // 유저 권한을 가진 유저 생성
        User savedUser = userRepository.save(user);// 유저 권한을 가진 유저 DB에 저장 후 DB에서 꺼냄

//        User savedUser = userRepository.save(EntityFactory.createUser()); 한줄로 처리가능한 코드

        User updateUser = User.builder()
                .address("주소변경")
                .addressDetail("상세주소변경")
                .postalCode("12345")
                .emailAgreement(false)
                .smsAgreement(false)
                .build();

        // DB에서 저장된 유저를 가져와서, 주소와 동의 메서드를 호출 변경 할 인자를 전달
        savedUser.updateAddressAndAgreement(updateUser.getAddress(), updateUser.getAddressDetail(), updateUser.getPostalCode(), updateUser.isEmailAgreement(), updateUser.isSmsAgreement());

        userRepository.save(savedUser);

        assertThat(savedUser.getAddress()).isEqualTo("주소변경");
        assertThat(savedUser.getAddressDetail()).isEqualTo("상세주소변경");
        assertThat(savedUser.isEmailAgreement()).isFalse();
        assertThat(savedUser.isSmsAgreement()).isFalse();


    }

    @Test
    @DisplayName("유저 등급수정테스트")
    public void modifyUserGrade() { // 유저를 수정

        User savedUser = userRepository.save(EntityFactory.createUser());

        User updateUser = User.builder()
                .userGrade(UserGrade.SILVER)
                .build();

        savedUser.updateUserGrade(updateUser.getUserGrade());

        userRepository.save(savedUser);

        assertThat(savedUser.getUserGrade()).isEqualTo(UserGrade.SILVER);


    }

}
