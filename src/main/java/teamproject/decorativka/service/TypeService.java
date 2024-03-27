package teamproject.decorativka.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.type.TypeCreateRequestDto;
import teamproject.decorativka.dto.type.TypeResponseDto;

public interface TypeService {
    TypeResponseDto createType(TypeCreateRequestDto requestDto);

    List<TypeResponseDto> getAllTypes(Pageable pageable);

    void deleteType(Long id);
}
