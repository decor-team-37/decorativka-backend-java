package teamproject.decorativka.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.decorativka.model.Area;
import teamproject.decorativka.model.City;
import teamproject.decorativka.service.NovaPoshtaCityParserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/city")
public class CityController {
    private final NovaPoshtaCityParserService novaPoshtaCityParserService;

    @GetMapping("/cities/{areaRef}")
    public List<City> getCitiesByRegion(@PathVariable String areaRef) {
        return novaPoshtaCityParserService.getCitiesByArea(areaRef);
    }

    @GetMapping("/areas")
    public List<Area> getAreas() {
        return novaPoshtaCityParserService.getAreas();
    }
}
