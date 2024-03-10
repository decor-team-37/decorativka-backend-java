package teamproject.decorativka.telegram.dispatcher.handlers;

import org.springframework.stereotype.Component;
import teamproject.decorativka.telegram.dispatcher.CommandHandler;

@Component
public class DefaultCommandHandler implements CommandHandler {
    private static final String COMMAND = "/default";

    @Override
    public void handleCommand(Long chatId, String command, String[] args) {

    }

    @Override
    public String getCommand() {
        return COMMAND;
    }
}
