package kr.kro.moonlightmoist.shopapi.helpcenter.repository;

import kr.kro.moonlightmoist.shopapi.helpcenter.domain.Faq;
import kr.kro.moonlightmoist.shopapi.helpcenter.domain.InquiryType;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserGrade;
import kr.kro.moonlightmoist.shopapi.user.repository.UserGradeRepository;
import kr.kro.moonlightmoist.shopapi.user.repository.UserRepository;
import kr.kro.moonlightmoist.shopapi.util.EntityFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.parser.Entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
class FaqRepositoryUnitTest {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private UserGradeRepository userGradeRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("자주묻는질문 생성테스트")
    public void createFaqData () {

        User user = EntityFactory.createUser();
        User savedUser = userRepository.save(user);

        Faq newFaq = Faq.builder()
                .user(savedUser)
                .inquiryType(InquiryType.ETC)
                .title("결제가 되지않습니다")
                .answer("재결제를 해보시길 권장드립니다")
                .build();
        faqRepository.save(newFaq);

        assertThat(newFaq.getCreatedAt());
        assertThat(newFaq.getInquiryType());
        assertThat(newFaq.getAnswer()).isNotBlank();
        assertThat(newFaq.getTitle()).isEqualTo("결제가 되지않습니다");
    }


        @Test
    @DisplayName("자주묻는질문 삭제테스트")
    public void deleteFaqData () {

        User user = EntityFactory.createUser();
        User savedUser = userRepository.save(user);

            Faq newFaq = Faq.builder()
                    .user(savedUser)
                    .inquiryType(InquiryType.ETC)
                    .title("결제가 되지않습니다")
                    .answer("재결제를 해보시길 권장드립니다")
                    .build();
            Faq savedFaq = faqRepository.save(newFaq);

            faqRepository.deleteById(newFaq.getId());
            assertThat(faqRepository.findById(savedFaq.getId())).isEmpty();

        }


//    @Test
//    @DisplayName("자주묻는질문 수정테스트")
//    public void updateFaqData () {
//
//    }


}