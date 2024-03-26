package teamproject.decorativka.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.type.TypeCreateRequestDto;
import teamproject.decorativka.dto.type.TypeResponseDto;
import teamproject.decorativka.mapper.TypeMapper;
import teamproject.decorativka.repository.TypesRepository;
import teamproject.decorativka.service.TypeService;

@RequiredArgsConstructor
@Service
public class TypeServiceImpl implements TypeService {
    private final TypesRepository typesRepository;
    private final TypeMapper typeMapper;

    @Override
    public TypeResponseDto createType(TypeCreateRequestDto requestDto) {
        return typeMapper.toDto(typesRepository.save(typeMapper.toModel(requestDto)));
    }

    @Override
    public List<TypeResponseDto> getAllTypes(Pageable pageable) {
        return typesRepository.findAll(pageable).stream().map(typeMapper::toDto).toList();
    }

    @Override
    public void deleteType(Long id) {
        typesRepository.deleteById(id);
    }
}
