package teamproject.decorativka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.dto.type.TypeResponseDto;
import teamproject.decorativka.service.TypeService;

@Tag(name = "Types for Offer (service) manager")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/types")
public class TypeController {
    private final TypeService typeService;

    @Operation(summary = "Get all types for Offer (services)",
            description = "Get list of all not deleted types")
    @GetMapping
    public List<TypeResponseDto> getAllTypes(Pageable pageable) {
        return typeService.getAllTypes(pageable);
    }
}
