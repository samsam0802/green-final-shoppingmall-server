package kr.kro.moonlightmoist.shopapi.helpcenter.repository;

import kr.kro.moonlightmoist.shopapi.helpcenter.domain.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}