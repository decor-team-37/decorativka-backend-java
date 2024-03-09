package teamproject.decorativka.telegram;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.login.LoginRequestDto;
import teamproject.decorativka.dto.login.LoginResponseDto;
import teamproject.decorativka.secutiry.AuthenticationService;

@Service
public class TelegramAdminService {
    private final AuthenticationService authenticationService;
    private final TelegramBot telegramBot;

    private final Map<Long, TelegramAdminState> adminStates = new HashMap<>();

    public TelegramAdminService(AuthenticationService authenticationService,
                                @Lazy TelegramBot telegramBot) {
        this.authenticationService = authenticationService;
        this.telegramBot = telegramBot;
    }

    public boolean isAuthorized(Long chatId, String messageText) {
        TelegramAdminState state = getState(chatId);
        if (state == null) {
            addUserState(chatId, new TelegramAdminState(chatId, true));
            telegramBot.sendMessage(chatId, "Enter your email to verify");
            return false;
        }
        return processState(chatId, state, messageText);
    }

    public boolean registration(Long chatId, String text) {
        if (!login(getState(chatId).getEmail(), text)) {
            telegramBot.sendMessage(chatId, "Невірний email чи пароль");
            return false;
        }
        adminStates.get(chatId).setAwaitingEmail(false);
        adminStates.get(chatId).setEmail(text);
        telegramBot.sendMessage(chatId, "Login successful");
        telegramBot.sendMessage(chatId, "Please, delete password from the chat");
        return true;
    }

    public boolean login(String email, String password) {
        LoginResponseDto authentication
                = authenticationService.authentication(new LoginRequestDto(email, password));
        return authentication != null;
    }

    public TelegramAdminState getState(Long chatId) {
        return adminStates.get(chatId);
    }

    public void addUserState(Long chatId, TelegramAdminState telegramAdminState) {
        adminStates.put(chatId, telegramAdminState);
    }

    public boolean setEmailToState(Long chatId, String email) {
        if (!isEmail(email)) {
            telegramBot.sendMessage(chatId, "Not valid email");
            return false;
        }
        getState(chatId).setEmail(email);
        return true;
    }

    private boolean processState(Long chatId, TelegramAdminState state, String messageText) {
        if (state.isAwaitingEmail()) {
            if (setEmailToState(chatId, messageText)) {
                state.setAwaitingEmail(false);
                state.setAwaitingPassword(true);
                telegramBot.sendMessage(chatId, "Enter your password");
                telegramBot.sendMessage(chatId, "Please, delete password from the chat");

            }
            return false;
        }
        if (state.isAwaitingPassword()) {
            return registration(chatId, messageText);
        }
        return true;
    }

    private boolean isEmail(String email) {
        Pattern p = Pattern.compile("\\b[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,4}\\b",
                Pattern.CASE_INSENSITIVE);
        return p.matcher(email).find();
    }
}
