package teamproject.decorativka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import teamproject.decorativka.service.CacheUpdateService;
import teamproject.decorativka.service.NovaPoshtaCityParserService;

@RequiredArgsConstructor
@Service
public class CacheUpdateServiceImpl implements CacheUpdateService {
    private final NovaPoshtaCityParserService novaPoshtaCityParserService;

    @Scheduled(fixedRate = 604800000)
    @Override
    public void updateCacheWeekly() {
        novaPoshtaCityParserService.initializeCitiesCache();
    }
}
