package teamproject.decorativka.mapper;

import org.mapstruct.Mapper;
import teamproject.decorativka.config.MapperConfig;
import teamproject.decorativka.dto.type.TypeCreateRequestDto;
import teamproject.decorativka.dto.type.TypeResponseDto;
import teamproject.decorativka.model.Type;

@Mapper(config = MapperConfig.class)
public interface TypeMapper {
    Type toModel(TypeCreateRequestDto requestDto);

    TypeResponseDto toDto(Type type);
}
