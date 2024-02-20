package teamproject.decorativka.service;

import java.util.List;
import teamproject.decorativka.dto.category.CategoryCreateRequestDto;
import teamproject.decorativka.dto.category.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryCreateRequestDto requestDto);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto getCategory(Long id);

    CategoryResponseDto updateCategory(Long id, CategoryCreateRequestDto requestDto);

    void deleteCategory(Long id);
}
