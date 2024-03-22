package teamproject.decorativka.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import teamproject.decorativka.model.City;
import teamproject.decorativka.service.NovaPoshtaCityParserService;

@Service
public class NovaPoshtaCityParserServiceImpl implements NovaPoshtaCityParserService {
    private static final int LIMIT = 150;
    private static final String API_URL = "https://api.novaposhta.ua/v2.0/json/";
    private final RestTemplate restTemplate;
    private final Map<String, List<City>> areaToCitiesMap = new HashMap<>();
    private final List<City> cities = new ArrayList<>();
    @Value("${novaposhta.apikey}")
    private String apiKey;

    public NovaPoshtaCityParserServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<City> getCitiesByArea(String areaName) {
        return areaToCitiesMap.getOrDefault(areaName, Collections.emptyList());
    }

    @Override
    public List<String> getAreas() {
        return new ArrayList<>(areaToCitiesMap.keySet());
    }

    @Cacheable("areaToCitiesMap")
    @Override
    public Map<String, List<City>> initializeCitiesCache() {
        List<String> areas = getAreasFromApi();
        for (String area : areas) {
            areaToCitiesMap.put(area, getCitiesByAreaFromApi(area));
        }
        cities.clear();
        return areaToCitiesMap;
    }

    private List<City> getAllCities() {
        int page = 1;
        while (true) {
            Map<String, Object> requestPayload = getRequestPayload(page++);
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(API_URL, requestPayload, Map.class);
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                break;
            }
            List<Map<String, Object>> citiesData =
                    (List<Map<String, Object>>) response.getBody().get("data");
            this.cities.addAll(citiesData.stream().map(this::mapApiResponseToCity).toList());
            if (citiesData.size() < LIMIT) {
                break;
            }
        }
        return cities;
    }

    private List<City> getCitiesByAreaFromApi(String name) {
        if (cities.isEmpty()) {
            getAllCities();
        }
        return cities.stream().filter(city -> city.getArea().equals(name)).toList();
    }

    private List<String> getAreasFromApi() {
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

    private Map<String, Object> getRequestPayload(int page) {
        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("apiKey", apiKey);
        requestPayload.put("modelName", "Address");
        requestPayload.put("calledMethod", "getSettlements");
        Map<String, String> methodProperties = new HashMap<>();
        methodProperties.put("Page", String.valueOf(page));
        methodProperties.put("Warehouse", "1");
        requestPayload.put("methodProperties", methodProperties);
        return requestPayload;
    }

    private String mapApiResponseToRegion(Map<String, Object> apiResponse) {
        return (apiResponse.get("Description") + " область");
    }

    private City mapApiResponseToCity(Map<String, Object> apiResponse) {
        City city = new City();
        city.setName((String) apiResponse.get("Description"));
        city.setRegion((String) apiResponse.get("RegionsDescription"));
        city.setArea((String) apiResponse.get("AreaDescription"));
        return city;
    }
}
