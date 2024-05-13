package teamproject.decorativka.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import teamproject.decorativka.config.MapperConfig;
import teamproject.decorativka.dto.offer.OfferCreateRequestDto;
import teamproject.decorativka.dto.offer.OfferResponseDto;
import teamproject.decorativka.model.Offer;

@Mapper(config = MapperConfig.class)
public interface OfferMapper {
    Offer toModel(OfferCreateRequestDto requestDto);

    @Mapping(target = "categoryId", source = "type.id")
    OfferResponseDto toDto(Offer offer);

    void updateOfferFromDto(OfferCreateRequestDto requestDto, @MappingTarget Offer offer);
}
