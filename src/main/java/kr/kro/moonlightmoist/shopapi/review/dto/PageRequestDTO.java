package kr.kro.moonlightmoist.shopapi.review.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PageRequestDTO {
    private Integer page;
    private Integer size;
    private String sort;
}
