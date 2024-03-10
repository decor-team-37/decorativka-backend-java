package teamproject.decorativka.telegram.dispatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import teamproject.decorativka.telegram.dispatcher.handlers.DefaultActionHandler;

@Component
public class ActionDispatcher {
    private final Map<String, ActionHandler> commandHandlers;

    public ActionDispatcher(List<ActionHandler> handlers) {
        this.commandHandlers = new HashMap<>();
        handlers.forEach(handler -> commandHandlers.put(handler.getAction(), handler));
    }

    public void dispatch(Long chatId, String command, String[] args) {
        ActionHandler handler = commandHandlers.getOrDefault(command, new DefaultActionHandler());
        handler.handleAction(chatId, command, args);
    }
}
