package kr.kro.moonlightmoist.shopapi.user.repository;

import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.domain.UserWithdrawal;
import kr.kro.moonlightmoist.shopapi.user.domain.UserWithdrawalReason;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class UserWithdrawalRepositoryUnitTest {

    @Autowired
    private UserWithdrawalRepository userWithdrawalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGradeRepository userGradeRepository;

    @Test
    @DisplayName("회원탈퇴 생성테스트")
    public void createUserWithdrawal() {
        User user = EntityFactory.createUser(); // 유저 생성
        User savedUser = userRepository.save(user); // 유저 DB 저장


        UserWithdrawal userWithdrawal = UserWithdrawal.builder() // 회원 탈퇴 생성
                .userWithdrawalReason(UserWithdrawalReason.ETC) // 필드 ENUM Type
                .user(savedUser) // 유저 생성
                .build();

        UserWithdrawal savedUserWithdrawal = userWithdrawalRepository.save(userWithdrawal);

        assertThat(savedUserWithdrawal.getUser().getLoginId()).isEqualTo("user");
        assertThat(savedUserWithdrawal.getUserWithdrawalReason()).isEqualTo(UserWithdrawalReason.ETC);
    }


    @Test
    @DisplayName("회원탈퇴 삭제테스트")
    public void deleteUserWithdrawal() {
        User user = EntityFactory.createUser();
        User savedUser = userRepository.save(user);


        UserWithdrawal userWithdrawal = UserWithdrawal.builder()
                .userWithdrawalReason(UserWithdrawalReason.ETC)
                .user(savedUser)
                .build();
        UserWithdrawal savedUserWithdrawal = userWithdrawalRepository.save(userWithdrawal);

        userWithdrawalRepository.deleteById(savedUserWithdrawal.getId());
        assertThat(userWithdrawalRepository.findById(savedUserWithdrawal.getId())).isEmpty();

    }


//    @Test
//    @DisplayName("회원탈퇴 수정테스트")
//    public void updateUserWithdrawal() {
//
//    }







}