package teamproject.decorativka.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.category.CategoryCreateRequestDto;
import teamproject.decorativka.dto.category.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryCreateRequestDto requestDto);

    List<CategoryResponseDto> getAllCategories(Pageable pageable);

    CategoryResponseDto getCategory(Long id);

    CategoryResponseDto updateCategory(Long id, CategoryCreateRequestDto requestDto);

    void deleteCategory(Long id);
}
