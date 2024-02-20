package teamproject.decorativka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import teamproject.decorativka.config.MapperConfig;
import teamproject.decorativka.dto.category.CategoryCreateRequestDto;
import teamproject.decorativka.dto.category.CategoryResponseDto;
import teamproject.decorativka.model.Category;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    Category toModel(CategoryCreateRequestDto requestDto);

    CategoryResponseDto toDto(Category category);

    void updateCategoryFromDto(CategoryCreateRequestDto requestDto,
                               @MappingTarget Category category);
}
