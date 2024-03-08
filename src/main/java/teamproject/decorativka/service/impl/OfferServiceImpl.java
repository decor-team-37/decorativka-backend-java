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

    @Override
    public OfferResponseDto createOffer(OfferCreateRequestDto requestDto) {
        return offerMapper.toDto(offerRepository.save(offerMapper.toModel(requestDto)));
    }

    @Override
    public OfferResponseDto getOfferById(Long id) {
        return offerMapper.toDto(getOffer(id));
    }

    @Override
    public List<OfferResponseDto> getAllOffers(Pageable pageable) {
        return offerRepository.getAllByDeletedFalse(pageable).stream()
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
        Offer offer = getOffer(id);
        offer.setDeleted(true);
        offerRepository.save(offer);
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

    private Offer getOffer(Long id) {
        return offerRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find offer with id: " + id)
        );
    }
}
