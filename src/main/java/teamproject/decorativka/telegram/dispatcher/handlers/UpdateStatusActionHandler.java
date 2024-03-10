package teamproject.decorativka.telegram.dispatcher.handlers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import teamproject.decorativka.telegram.TelegramBot;
import teamproject.decorativka.telegram.dispatcher.ActionHandler;

@Component
public class UpdateStatusActionHandler implements ActionHandler {
    private static final String ACTION = "update_order";
    private final TelegramBot telegramBot;

    public UpdateStatusActionHandler(@Lazy TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void handleAction(Long chatId, String action, String[] args) {
        telegramBot.sendMessage(chatId, "Будь ласка, введіть команду у форматі:"
                + " /update_order [номер замовлення] [новий статус]");
    }

    @Override
    public String getAction() {
        return ACTION;
    }
}
