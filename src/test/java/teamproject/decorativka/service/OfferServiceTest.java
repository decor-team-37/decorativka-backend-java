package teamproject.decorativka.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import teamproject.decorativka.dto.offer.OfferCreateRequestDto;
import teamproject.decorativka.dto.offer.OfferResponseDto;
import teamproject.decorativka.mapper.OfferMapper;
import teamproject.decorativka.model.Offer;
import teamproject.decorativka.model.Type;
import teamproject.decorativka.repository.TypesRepository;
import teamproject.decorativka.repository.offer.OfferRepository;
import teamproject.decorativka.service.impl.OfferServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {
    private static final Long VALID_ID = 1L;
    private static final Long NOT_VALID_ID = -1L;
    private static final String VALID_NAME = "Offer name";
    private static final String VALID_DESCRIPTION = "Offer description";
    private static final List<String> VALID_IMAGES_URLS = List.of("https://example.com/image1.jpg",
            "https://example.com/image2.jpg",
            "https://example.com/image3.jpg");

    @InjectMocks
    private OfferServiceImpl offerService;

    @Mock
    private OfferMapper offerMapper;
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private TypesRepository typesRepository;

    private Offer createValidOffer() {
        Offer offer = new Offer();
        offer.setId(VALID_ID);
        offer.setName(VALID_NAME);
        offer.setDescription(VALID_DESCRIPTION);
        offer.setImageUrl(VALID_IMAGES_URLS);
        return offer;
    }

    private OfferCreateRequestDto createValidRequestDto() {
        return new OfferCreateRequestDto(
                VALID_NAME,
                VALID_DESCRIPTION,
                VALID_ID,
                VALID_IMAGES_URLS
        );
    }

    private OfferResponseDto createValidResponseDto() {
        return new OfferResponseDto(
                VALID_ID,
                VALID_NAME,
                VALID_DESCRIPTION,
                VALID_ID,
                VALID_IMAGES_URLS
        );
    }

    @Test
    @DisplayName("Verify createOffer() method works")
    public void createOffer_ValidRequestDto_ValidResponseDto() {
        Offer validOffer = createValidOffer();
        OfferCreateRequestDto validRequestDto = createValidRequestDto();
        OfferResponseDto expected = createValidResponseDto();
        when(offerMapper.toModel(validRequestDto)).thenReturn(validOffer);
        when(typesRepository.findById(VALID_ID)).thenReturn(Optional.of(new Type()));
        when(offerRepository.save(validOffer)).thenReturn(validOffer);
        when(offerMapper.toDto(validOffer)).thenReturn(expected);

        OfferResponseDto actual = offerService.createOffer(validRequestDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify getOfferById() method works")
    public void getOfferById_ValidId_ValidResponseDto() {
        OfferResponseDto expected = createValidResponseDto();
        Offer validOffer = createValidOffer();
        when(offerRepository.findById(VALID_ID))
                .thenReturn(Optional.of(validOffer));
        when(offerMapper.toDto(validOffer)).thenReturn(expected);

        OfferResponseDto actual = offerService.getOfferById(VALID_ID);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify getOfferById() method throw exception with not valid id")
    public void getOfferById_NotValidId_EntityNotFoundException() {
        when(offerRepository.findById(NOT_VALID_ID))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> offerService.getOfferById(NOT_VALID_ID));
    }

    @Test
    @DisplayName("Verify getAllOffers() method works")
    public void getAllOffers_ValidPageable_ValidListResponseDto() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Offer> offerList = List.of(createValidOffer());
        List<OfferResponseDto> expected = List.of(createValidResponseDto());
        Page<Offer> offerPage = new PageImpl<>(offerList, pageable, offerList.size());

        when(offerRepository.findAll(pageable)).thenReturn(offerPage);
        when(offerMapper.toDto(createValidOffer())).thenReturn(createValidResponseDto());

        Page<OfferResponseDto> actual = offerService.getAllOffers(pageable);

        assertEquals(expected, actual.getContent());
    }

    @Test
    @DisplayName("Verify updateOffer() method works")
    public void updateOffer_ValidIdAndRequestDto_UpdatedOffer() {
        Offer validOffer = createValidOffer();
        OfferCreateRequestDto validRequestDto = createValidRequestDto();
        OfferResponseDto expected = createValidResponseDto();
        when(offerRepository.findById(VALID_ID))
                .thenReturn(Optional.of(validOffer));
        when(offerMapper.toDto(validOffer)).thenReturn(expected);

        OfferResponseDto actual = offerService.updateOffer(VALID_ID, validRequestDto);

        verify(offerMapper).updateOfferFromDto(validRequestDto, validOffer);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify deleteOffer() method works")
    public void deleteOffer_ValidId_DeletedTrue() {
        offerService.deleteOffer(VALID_ID);

        verify(offerRepository).deleteById(VALID_ID);
    }
}
