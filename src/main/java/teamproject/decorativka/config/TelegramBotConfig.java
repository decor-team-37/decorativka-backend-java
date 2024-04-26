package teamproject.decorativka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import teamproject.decorativka.telegram.TelegramBot;

@Profile("!test")
@Configuration
public class TelegramBotConfig {
    private final TelegramBot telegramBotService;

    public TelegramBotConfig(TelegramBot telegramBot) {
        this.telegramBotService = telegramBot;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBotService);
            return telegramBotsApi;
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't create Telegram bot API", e);
        }
    }
}
