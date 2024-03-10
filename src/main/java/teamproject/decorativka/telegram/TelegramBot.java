package teamproject.decorativka.telegram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import teamproject.decorativka.telegram.dispatcher.ActionDispatcher;
import teamproject.decorativka.telegram.dispatcher.CommandDispatcher;

@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegrambot.botUsername}")
    private String botUsername;
    @Value("${telegrambot.botToken}")
    private String botToken;
    private final TelegramAdminService telegramAdminService;
    private final ActionDispatcher actionDispatcher;
    private final CommandDispatcher commandDispatcher;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            processMessage(chatId, message);
        }
        if (update.hasCallbackQuery()) {
            String callData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String[] parts = callData.split(" ");
            String command = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            actionDispatcher.dispatch(chatId, command, args);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't send the message to chatId: " + chatId, e);
        }
    }

    private void sendMainMenu(Long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Виберіть опцію:")
                .replyMarkup(getMainMenuKeyboard())
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Can't send menu to chatId: " + chatId, e);
        }
    }

    private InlineKeyboardMarkup getMainMenuKeyboard() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(InlineKeyboardButton.builder().text("Всі замовлення")
                .callbackData("get_all_orders").build());
        rowsInline.add(rowInline);
        rowInline = new ArrayList<>();
        rowInline.add(InlineKeyboardButton.builder().text("Всі активні замовлення")
                .callbackData("get_open_orders").build());
        rowInline.add(InlineKeyboardButton.builder().text("Оновити статус")
                .callbackData("update_order").build());
        rowsInline.add(rowInline);
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private void processMessage(Long chatId, String message) {
        if (telegramAdminService.isAuthorized(chatId, message)) {
            if (message.startsWith("/")) {
                String[] parts = message.split(" ");
                String command = parts[0];
                String[] args = Arrays.copyOfRange(parts, 1, parts.length);
                commandDispatcher.dispatch(chatId, command, args);
            } else {
                sendMainMenu(chatId);
            }
        }
    }
}

