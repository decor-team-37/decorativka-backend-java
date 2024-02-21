package teamproject.decorativka.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.category.CategoryCreateRequestDto;
import teamproject.decorativka.dto.category.CategoryResponseDto;
import teamproject.decorativka.mapper.CategoryMapper;
import teamproject.decorativka.model.Category;
import teamproject.decorativka.repository.CategoryRepository;
import teamproject.decorativka.service.CategoryService;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDto createCategory(CategoryCreateRequestDto requestDto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toModel(requestDto)));
    }

    @Override
    public List<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return categoryRepository.getAllByDeletedFalse(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getCategory(Long id) {
        return categoryMapper.toDto(getCategoryById(id));
    }

    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryCreateRequestDto requestDto) {
        Category categoryToUpdate = getCategoryById(id);
        categoryMapper.updateCategoryFromDto(requestDto, categoryToUpdate);
        return categoryMapper.toDto(categoryRepository.save(categoryToUpdate));
    }

    @Override
    public void deleteCategory(Long id) {
        Category categoryToDelete = getCategoryById(id);
        categoryToDelete.setDeleted(true);
        categoryRepository.save(categoryToDelete);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category with id " + id)
        );
    }
}
