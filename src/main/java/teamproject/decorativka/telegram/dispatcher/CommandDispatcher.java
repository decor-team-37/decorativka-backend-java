package teamproject.decorativka.telegram.dispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import teamproject.decorativka.telegram.dispatcher.handlers.DefaultCommandHandler;

@Component
public class CommandDispatcher {
    private final Map<String, CommandHandler> commandHandlers;

    public CommandDispatcher(List<CommandHandler> handlers) {
        this.commandHandlers = new HashMap<>();
        handlers.forEach(handler -> commandHandlers.put(handler.getCommand(), handler));
    }

    public void dispatch(Long chatId, String command, String[] args) {
        CommandHandler handler = commandHandlers.getOrDefault(command, new DefaultCommandHandler());
        handler.handleCommand(chatId, command, args);
    }
}
