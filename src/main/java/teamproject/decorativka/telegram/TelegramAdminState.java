package teamproject.decorativka.telegram;

import lombok.Data;

@Data
public class TelegramAdminState {
    private Long chatId;
    private boolean awaitingEmail;
    private boolean awaitingPassword;
    private String email;

    public TelegramAdminState(Long chatId, boolean awaitingEmail) {
        this.chatId = chatId;
        this.awaitingEmail = awaitingEmail;
    }
}
