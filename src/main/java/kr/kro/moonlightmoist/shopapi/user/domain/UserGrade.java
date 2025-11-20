package kr.kro.moonlightmoist.shopapi.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@Getter
@AllArgsConstructor // 전체필드 생성자 -> 빌더를 사용하려면 꼭 필요하다.
@ToString // 객체의 모든 필드를 포함하는 문자열 자동 생성, 디버깅이나 로그 기록 시 엔티티 객체의 상태를 쉽게 확인하기 위해
public enum UserGrade {
    BRONZE(0, 0, 0, "기본 등급"),
    SILVER(10000, 3, 50000, "5만원이상 무료배송"),
    GOLD(50000, 5, 30000, "3만원이상 무료배송"),
    DIAMOND(100000, 7, 10000, "1만원이상 무료배송"),
    VIP(300000, 10, 0, "10%할인, 무조건 무료배송");

    private final int minTotalPoint;
    private final int discountRate;
    private final int freeDeliveryMinAmount;
    private final String description;

}
