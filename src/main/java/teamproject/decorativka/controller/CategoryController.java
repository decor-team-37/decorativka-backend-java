package teamproject.decorativka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.dto.category.CategoryResponseDto;
import teamproject.decorativka.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get all categories",
            description = "Get list of all not deleted categories")
    @Parameter(name = "page", description = "open specified page, default value = 0",
            required = true, example = "0")
    @Parameter(name = "size", description = "describes count element per page",
            required = true, example = "10")
    @GetMapping
    public List<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }
}
