package teamproject.decorativka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import teamproject.decorativka.config.MapperConfig;
import teamproject.decorativka.dto.order.OrderCreateRequestDto;
import teamproject.decorativka.dto.order.OrderResponseDto;
import teamproject.decorativka.model.Order;

@Mapper(config = MapperConfig.class, uses = {OrderItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "orderItems", target = "orderItems")
    OrderResponseDto toDto(Order order);

    @Mapping(target = "orderItems", ignore = true)
    Order toModel(OrderCreateRequestDto requestDto);
}
