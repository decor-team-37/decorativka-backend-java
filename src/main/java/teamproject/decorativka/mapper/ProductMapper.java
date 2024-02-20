package teamproject.decorativka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import teamproject.decorativka.config.MapperConfig;
import teamproject.decorativka.dto.product.ProductCreateRequestDto;
import teamproject.decorativka.dto.product.ProductResponseDto;
import teamproject.decorativka.model.Product;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {

    Product toModel(ProductCreateRequestDto requestDto);

    @Mapping(target = "categoryId", source = "category.id")
    ProductResponseDto toDto(Product product);

    void updateProductFromDto(ProductCreateRequestDto requestDto, @MappingTarget Product product);
}
