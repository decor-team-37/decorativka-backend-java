package teamproject.decorativka.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.offer.OfferCreateRequestDto;
import teamproject.decorativka.dto.offer.OfferResponseDto;
import teamproject.decorativka.mapper.OfferMapper;
import teamproject.decorativka.model.Offer;
import teamproject.decorativka.model.Type;
import teamproject.decorativka.repository.TypesRepository;
import teamproject.decorativka.repository.offer.OfferRepository;
import teamproject.decorativka.search.OfferSearchParameters;
import teamproject.decorativka.search.SearchService;
import teamproject.decorativka.service.OfferService;

@RequiredArgsConstructor
@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final SearchService searchService;
    private final TypesRepository typesRepository;

    @Override
    public OfferResponseDto createOffer(OfferCreateRequestDto requestDto) {
        Offer offer = offerMapper.toModel(requestDto);
        offer.setType(resolveType(requestDto.typeId()));
        return offerMapper.toDto(offerRepository.save(offer));
    }

    @Override
    public OfferResponseDto getOfferById(Long id) {
        return offerMapper.toDto(getOffer(id));
    }

    @Override
    public List<OfferResponseDto> getAllOffers(Pageable pageable) {
        return offerRepository.findAll(pageable).stream()
                .map(offerMapper::toDto)
                .toList();
    }

    @Override
    public OfferResponseDto updateOffer(Long id, OfferCreateRequestDto requestDto) {
        Offer offerToUpdate = getOffer(id);
        offerMapper.updateOfferFromDto(requestDto, offerToUpdate);
        return offerMapper.toDto(offerToUpdate);
    }

    @Override
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

    @Override
    public List<OfferResponseDto> search(OfferSearchParameters searchParameters,
                                         Pageable pageable) {
        Specification<Offer> offerSpecification
                = searchService.buildOfferSpecification(searchParameters);
        return offerRepository.findAll(offerSpecification, pageable).stream()
                .map(offerMapper::toDto)
                .toList();
    }

    @Override
    public List<OfferResponseDto> getAllOfferByTypeId(Long id) {
        resolveType(id);
        return offerRepository.getAllByTypeId(id).stream().map(offerMapper::toDto).toList();
    }

    private Offer getOffer(Long id) {
        return offerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find offer with id: " + id)
        );
    }

    private Type resolveType(Long id) {
        return typesRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find type with id: " + id)
        );
    }
}
