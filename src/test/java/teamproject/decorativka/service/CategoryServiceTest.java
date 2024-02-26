package teamproject.decorativka.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.category.CategoryCreateRequestDto;
import teamproject.decorativka.dto.category.CategoryResponseDto;
import teamproject.decorativka.mapper.CategoryMapper;
import teamproject.decorativka.model.Category;
import teamproject.decorativka.repository.CategoryRepository;
import teamproject.decorativka.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    private static final Long VALID_ID = 1L;
    private static final Long NOT_VALID_ID = -1L;
    private static final String VALID_NAME = "CategoryName";
    private static final String VALID_DESCRIPTION = "VALID category description";
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    private Category createValidCategory() {
        Category category = new Category();
        category.setId(VALID_ID);
        category.setName(VALID_NAME);
        category.setDescription(VALID_DESCRIPTION);
        return category;
    }

    private CategoryCreateRequestDto createValidCategoryCreateRequestDto() {
        return new CategoryCreateRequestDto(
                VALID_NAME, VALID_DESCRIPTION
        );
    }

    private CategoryResponseDto createValidCategoryResponseDto() {
        return new CategoryResponseDto(
                VALID_ID, VALID_NAME, VALID_DESCRIPTION
        );
    }

    @Test
    @DisplayName("Verify createCategory() method works")
    public void createCategory_ValidCategoryRequestDto_ValidCategoryResponseDto() {
        CategoryCreateRequestDto validCategoryCreateRequestDto
                = createValidCategoryCreateRequestDto();
        Category validCategory = createValidCategory();
        CategoryResponseDto expected = createValidCategoryResponseDto();
        when(categoryMapper.toModel(validCategoryCreateRequestDto)).thenReturn(validCategory);
        when(categoryRepository.save(validCategory)).thenReturn(validCategory);
        when(categoryMapper.toDto(validCategory)).thenReturn(expected);

        CategoryResponseDto actual = categoryService
                .createCategory(validCategoryCreateRequestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify getAllCategories() method works")
    public void getAllCategories_ValidPageable_ListCategoryResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Category validCategory = createValidCategory();
        CategoryResponseDto validCategoryResponseDto = createValidCategoryResponseDto();
        when(categoryRepository.getAllByDeletedFalse(pageable)).thenReturn(List.of(validCategory));
        when(categoryMapper.toDto(validCategory)).thenReturn(validCategoryResponseDto);
        List<CategoryResponseDto> expected = List.of(validCategoryResponseDto);

        List<CategoryResponseDto> actual = categoryService.getAllCategories(pageable);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify getCategory() method works")
    public void getCategory_ValidId_ValidCategoryResponseDto() {
        Category validCategory = createValidCategory();
        CategoryResponseDto expected = createValidCategoryResponseDto();
        when(categoryRepository.findByIdAndDeletedFalse(VALID_ID))
                .thenReturn(Optional.of(validCategory));
        when(categoryMapper.toDto(validCategory)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.getCategory(VALID_ID);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify getCategory() throw EntityNotFoundException with not valid id")
    public void getCategory_NotValidId_ThrowException() {
        when(categoryRepository.findByIdAndDeletedFalse(NOT_VALID_ID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> categoryService.getCategory(NOT_VALID_ID));
    }

    @Test
    @DisplayName("Verify updateCategory() method works")
    public void updateCategory_ValidIdAndRequestDto_UpdatedRequestDto() {
        CategoryCreateRequestDto validCategoryCreateRequestDto
                = createValidCategoryCreateRequestDto();
        Category validCategory = createValidCategory();
        CategoryResponseDto expected = createValidCategoryResponseDto();
        when(categoryRepository.findByIdAndDeletedFalse(VALID_ID))
                .thenReturn(Optional.of(validCategory));
        when(categoryRepository.save(validCategory)).thenReturn(validCategory);
        when(categoryMapper.toDto(validCategory)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.updateCategory(
                VALID_ID, validCategoryCreateRequestDto);

        verify(categoryMapper).updateCategoryFromDto(
                validCategoryCreateRequestDto, validCategory);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify deleteCategory() method works")
    public void deleteCategory_ValidId_CategoryDeleted() {
        Category expected = createValidCategory();
        expected.setDeleted(true);
        when(categoryRepository.findByIdAndDeletedFalse(VALID_ID))
                .thenReturn(Optional.of(createValidCategory()));

        categoryService.deleteCategory(VALID_ID);

        verify(categoryRepository).save(expected);
    }
}
