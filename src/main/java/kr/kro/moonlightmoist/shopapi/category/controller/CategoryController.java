package kr.kro.moonlightmoist.shopapi.category.controller;

import kr.kro.moonlightmoist.shopapi.category.dto.CategoryRegisterReq;
import kr.kro.moonlightmoist.shopapi.category.dto.CategoryResForList;
import kr.kro.moonlightmoist.shopapi.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<String> register(@RequestBody CategoryRegisterReq dto) {
        System.out.println("dto = " + dto);
        categoryService.register(dto);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryResForList>> getCategoryList() {
        List<CategoryResForList> categories = categoryService.getCategoryList();

        return ResponseEntity.ok(categories);
    }

}
