package teamproject.decorativka.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import teamproject.decorativka.model.Area;
import teamproject.decorativka.model.City;
import teamproject.decorativka.service.NovaPoshtaCityParserService;

@Service
public class NovaPoshtaCityParserServiceImpl implements NovaPoshtaCityParserService {
    private static final int LIMIT = 150;
    private static final String API_URL = "https://api.novaposhta.ua/v2.0/json/";
    private final RestTemplate restTemplate;
    @Value("${novaposhta.apikey}")
    private String apiKey;

    public NovaPoshtaCityParserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<City> getCitiesByArea(String areaRef) {
        List<City> citiesForArea = new ArrayList<>();
        int page = 1;
        while (true) {
            Map<String, Object> requestPayload = getRequestPayload(page++, areaRef);
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(API_URL, requestPayload, Map.class);
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                break;
            }
            List<Map<String, Object>> citiesData =
                    (List<Map<String, Object>>) response.getBody().get("data");
            List<City> cities = citiesData.stream()
                    .map(this::mapApiResponseToCity)
                    .toList();
            citiesForArea.addAll(cities);
            if (cities.size() < LIMIT) {
                break;
            }
        }
        return citiesForArea;
    }

    @Override
    public List<Area> getAreas() {
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("apiKey", apiKey);
        requestPayload.put("modelName", "Address");
        requestPayload.put("calledMethod", "getAreas");

        ResponseEntity<Map> response = restTemplate
                .postForEntity(API_URL, requestPayload, Map.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map<String, Object>> areasData =
                    (List<Map<String, Object>>) response.getBody().get("data");
            return areasData.stream()
                    .map(this::mapApiResponseToRegion)
                    .toList();
        }
        return Collections.emptyList();
    }

    private Map<String, Object> getRequestPayload(int page, String areaRef) {
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("apiKey", apiKey);
        requestPayload.put("modelName", "Address");
        requestPayload.put("calledMethod", "getSettlements");
        Map<String, String> methodProperties = new HashMap<>();
        methodProperties.put("AreaRef", areaRef);
        methodProperties.put("Page", String.valueOf(page));
        methodProperties.put("Warehouse", "1");
        requestPayload.put("methodProperties", methodProperties);
        return requestPayload;
    }

    private Area mapApiResponseToRegion(Map<String, Object> apiResponse) {
        Area area = new Area();
        area.setRef((String) apiResponse.get("Ref"));
        area.setDescription((String) apiResponse.get("Description"));
        return area;
    }

    private City mapApiResponseToCity(Map<String, Object> apiResponse) {
        City city = new City();
        city.setRef((String) apiResponse.get("Ref"));
        city.setName((String) apiResponse.get("Description"));
        city.setRegion((String) apiResponse.get("RegionsDescription"));
        city.setArea((String) apiResponse.get("AreaDescription"));
        city.setAreaRef((String) apiResponse.get("Area"));
        return city;
    }
}
