package teamproject.decorativka.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.offer.OfferCreateRequestDto;
import teamproject.decorativka.dto.offer.OfferResponseDto;
import teamproject.decorativka.search.OfferSearchParameters;

public interface OfferService {
    OfferResponseDto createOffer(OfferCreateRequestDto requestDto);

    OfferResponseDto getOfferById(Long id);

    List<OfferResponseDto> getAllOffers(Pageable pageable);

    OfferResponseDto updateOffer(Long id, OfferCreateRequestDto requestDto);

    void deleteOffer(Long id);

    List<OfferResponseDto> search(OfferSearchParameters searchParameters, Pageable pageable);
}
