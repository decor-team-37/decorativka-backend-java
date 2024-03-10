package teamproject.decorativka.telegram.dispatcher.handlers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import teamproject.decorativka.telegram.TelegramBot;
import teamproject.decorativka.telegram.dispatcher.CommandHandler;

@Component
public class StartCommandHandler implements CommandHandler {
    private static final String COMMAND = "/start";
    private final TelegramBot telegramBot;

    public StartCommandHandler(@Lazy TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void handleCommand(Long chatId, String command, String[] args) {
        String welcomeMessage = "Привіт! Пройти авторизацію для можливості користування, "
                + "а потім виконуй вказані дії";
        telegramBot.sendMessage(chatId, welcomeMessage);
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }
}
