package teamproject.decorativka.telegram.dispatcher.handlers;

import teamproject.decorativka.telegram.dispatcher.ActionHandler;

public class DefaultActionHandler implements ActionHandler {

    @Override
    public void handleAction(Long chatId, String action, String[] args) {

    }

    @Override
    public String getAction() {
        return "default";
    }
}
