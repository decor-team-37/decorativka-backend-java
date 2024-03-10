package teamproject.decorativka.telegram.dispatcher;

public interface CommandHandler {
    void handleCommand(Long chatId, String command, String[] args);

    String getCommand();
}
