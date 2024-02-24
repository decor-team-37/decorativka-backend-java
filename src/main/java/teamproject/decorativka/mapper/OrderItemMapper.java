package teamproject.decorativka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import teamproject.decorativka.config.MapperConfig;
import teamproject.decorativka.dto.order.OrderItemResponseDto;
import teamproject.decorativka.model.OrderItem;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "product.id", target = "productId")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
