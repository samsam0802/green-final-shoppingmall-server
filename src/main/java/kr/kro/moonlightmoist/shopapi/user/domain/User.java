package kr.kro.moonlightmoist.shopapi.user.domain;

import jakarta.persistence.*;
import kr.kro.moonlightmoist.shopapi.common.domain.BaseTimeEntity;
import lombok.*;
import org.hibernate.type.EntityType;

import java.time.LocalDate;

@Entity // DB와 1:1 매핑되는 클래스  JPA가 관리한다.
@Table(name = "users") // 테이블 어노테이션
@Getter
@Setter
@Builder
@AllArgsConstructor // 빌더를 쓰려면 올아그스먼트컨트럭쳐 어노테이션이 필요하다.
@NoArgsConstructor // JPA Entity는 기본생성자가 필수
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // not null
    private String loginId;

    @Column(nullable = false) // not null
    private String passwordHash;

    @Column(nullable = false) // not null
    private String name;

    @Column(nullable = false, unique = true) // not null
    private String phoneNumber;

    @Column(nullable = false, unique = true) // not null
    private String email;

    @Column(nullable = false) // not null
    private LocalDate birthDate;

    @Column(nullable = false) // not null
    private String postalCode; // 우편번호

    @Column(nullable = false) // not null
    private String address; // 기본주소

    @Column(nullable = false) // not null
    private String addressDetail; // 상세주소

    @Column(nullable = false)
    private boolean emailAgreement; // 이메일 마케팅 동의 알림


    @Column(nullable = false)
    private boolean smsAgreement; // SMS 마케팅 동의 알림

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted; // 회원탈퇴 여부 컬럼

    private LocalDate deletedAt; // 회원탈퇴 일자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    // 여러명의 유저는 회원등급을 가진다.
    @Enumerated(EntityType.STRING)
    @Column(nullable = false)
    private UserGrade userGrade; // 회원 등급

    public void updateAddressAndAgreement (String address, String addressDetail, String postalCode, boolean emailAgreement, boolean smsAgreement) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.postalCode = postalCode;
        this.emailAgreement = emailAgreement;
        this.smsAgreement = smsAgreement;
    }



}
