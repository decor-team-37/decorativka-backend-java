package teamproject.decorativka.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.offer.OfferCreateRequestDto;
import teamproject.decorativka.dto.offer.OfferResponseDto;
import teamproject.decorativka.search.OfferSearchParameters;

public interface OfferService {
    OfferResponseDto createOffer(OfferCreateRequestDto requestDto);

    OfferResponseDto getOfferById(Long id);

    Page<OfferResponseDto> getAllOffers(Pageable pageable);

    OfferResponseDto updateOffer(Long id, OfferCreateRequestDto requestDto);

    void deleteOffer(Long id);

    Page<OfferResponseDto> search(OfferSearchParameters searchParameters, Pageable pageable);

    Page<OfferResponseDto> getAllOfferByTypeId(Long id, Pageable pageable);
}
