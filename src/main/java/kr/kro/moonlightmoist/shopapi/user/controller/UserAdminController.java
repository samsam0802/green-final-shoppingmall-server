package kr.kro.moonlightmoist.shopapi.user.controller;

import kr.kro.moonlightmoist.shopapi.review.dto.PageRequestDTO;
import kr.kro.moonlightmoist.shopapi.review.dto.PageResponseDTO;
import kr.kro.moonlightmoist.shopapi.user.domain.User;
import kr.kro.moonlightmoist.shopapi.user.domain.UserRole;
import kr.kro.moonlightmoist.shopapi.user.dto.UserSearchCondition;
import kr.kro.moonlightmoist.shopapi.user.service.UserAdminService;
import kr.kro.moonlightmoist.shopapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/admin/user") //관리자 전용 경로
//@CrossOrigin(origins = "http://localhost:5137") BY 병국 (주석처리함)
public class UserAdminController {

    private final UserAdminService userAdminService;

    @PostMapping("/search")
    public ResponseEntity<PageResponseDTO<User>> searchUser(
            @RequestBody UserSearchCondition condition,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "recent") String sort
    ) {
        log.info("검색 조건: {}", condition);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        PageResponseDTO<User> result = userAdminService.searchUsers(condition, pageRequestDTO);

        log.info("검색 결과: 총 {}명, 현재 페이지 {}개", result.getTotalDataCount(), result.getDtoList().size());
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{userId}/role")
    public void changeUserRole(
            @PathVariable Long userId,
            @RequestParam UserRole userRole
    ) {
        userAdminService.userRoleChange(userId, userRole);
    }
}
