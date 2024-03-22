package teamproject.decorativka.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.model.City;
import teamproject.decorativka.service.NovaPoshtaCityParserService;

@Tag(name = "Region and City manager",
        description = "endpoint to get region and city in this region")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/city")
public class CityController {
    private final NovaPoshtaCityParserService novaPoshtaCityParserService;

    @Operation(summary = "Get city by region (area) name",
            description = "Отримати всі міста за назвою області. Приклад назви "
                    + " Сумська область")
    @GetMapping("/cities/{areaName}")
    public List<City> getCitiesByRegion(@PathVariable String areaName) {
        return novaPoshtaCityParserService.getCitiesByArea(areaName);
    }

    @Operation(summary = "Get all area name like string array")
    @GetMapping("/areas")
    public List<String> getAreas() {
        return novaPoshtaCityParserService.getAreas();
    }
}
