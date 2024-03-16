package teamproject.decorativka.service;

import java.util.List;
import java.util.Map;
import teamproject.decorativka.model.City;

public interface NovaPoshtaCityParserService {
    List<City> getCitiesByArea(String areaRef);

    List<String> getAreas();

    Map<String, List<City>> initializeCitiesCache();
}
