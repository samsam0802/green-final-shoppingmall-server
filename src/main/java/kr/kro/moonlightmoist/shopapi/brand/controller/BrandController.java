package kr.kro.moonlightmoist.shopapi.brand.controller;

import kr.kro.moonlightmoist.shopapi.brand.dto.BrandDTO;
import kr.kro.moonlightmoist.shopapi.brand.service.BrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("")
    public ResponseEntity<String> register(@RequestBody BrandDTO dto) {
        System.out.println("dto = " + dto);
        brandService.register(dto);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("")
    public ResponseEntity<List<BrandDTO>> getBrandList() {
        List<BrandDTO> brands = brandService.getBrandList();

        return ResponseEntity.ok(brands);
    }

}
