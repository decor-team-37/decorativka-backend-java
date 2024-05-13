package teamproject.decorativka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.dto.offer.OfferResponseDto;
import teamproject.decorativka.search.OfferSearchParameters;
import teamproject.decorativka.service.OfferService;

@Tag(name = "Offers (Services) manager",
        description = "Endpoint to get info about offers (services)")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/offers")
public class OfferController {
    private final OfferService offerService;

    @Operation(summary = "Get one specific offer (service) by id")
    @GetMapping("/{id}")
    public OfferResponseDto getOfferById(@PathVariable Long id) {
        return offerService.getOfferById(id);
    }

    @Operation(summary = "Get all offers (services)",
            description = "Get list of all not deleted offers")
    @Parameter(name = "page", description = "open specified page, default value = 0",
            required = true, example = "0")
    @Parameter(name = "size", description = "describes count element per page",
            required = true, example = "10")
    @GetMapping
    public Page<OfferResponseDto> getAllOffers(Pageable pageable) {
        return offerService.getAllOffers(pageable);
    }

    @Operation(summary = "Search offers (services) for specific parameters",
            description = "Get all offers, which have certain params: name")
    @GetMapping("/search")
    public Page<OfferResponseDto> getAllBySearchParams(OfferSearchParameters searchParameters,
                                                       Pageable pageable) {
        return offerService.search(searchParameters, pageable);
    }

    @Operation(summary = "Get all offers (services) by type id",
            description = "Get list of all not deleted offers by type id")
    @GetMapping("/type/{id}")
    public Page<OfferResponseDto> getAllByCategoryId(@PathVariable Long id, Pageable pageable) {
        return offerService.getAllOfferByTypeId(id, pageable);
    }
}
