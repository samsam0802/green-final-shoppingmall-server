package kr.kro.moonlightmoist.shopapi.category.service;

import kr.kro.moonlightmoist.shopapi.category.dto.CategoryRegisterReq;
import kr.kro.moonlightmoist.shopapi.category.dto.CategoryResForList;

import java.util.List;

public interface CategoryService {
    void register(CategoryRegisterReq dto);
    List<CategoryResForList> getCategoryList();
}
