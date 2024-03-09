package teamproject.decorativka.telegram.dispatcher;

public interface ActionHandler {
    void handleAction(Long chatId, String action, String[] args);

    String getAction();
}
