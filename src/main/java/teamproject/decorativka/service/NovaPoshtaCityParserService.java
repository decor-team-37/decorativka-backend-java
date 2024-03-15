package teamproject.decorativka.service;

import java.util.List;
import teamproject.decorativka.model.Area;
import teamproject.decorativka.model.City;

public interface NovaPoshtaCityParserService {
    List<City> getCitiesByArea(String areaRef);

    List<Area> getAreas();
}
