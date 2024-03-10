package teamproject.decorativka.telegram.dispatcher.handlers;

import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import teamproject.decorativka.dto.order.OrderResponseDto;
import teamproject.decorativka.service.OrderService;
import teamproject.decorativka.telegram.MessageFormatterService;
import teamproject.decorativka.telegram.TelegramBot;
import teamproject.decorativka.telegram.dispatcher.ActionHandler;

@Component
public class GetAllActionHandler implements ActionHandler {
    private static final String ACTION = "get_all_orders";
    private final OrderService orderService;
    private final TelegramBot telegramBot;
    private final MessageFormatterService messageFormatterService;

    public GetAllActionHandler(OrderService orderService,
                               @Lazy TelegramBot telegramBot,
                               MessageFormatterService messageFormatterService) {
        this.orderService = orderService;
        this.telegramBot = telegramBot;
        this.messageFormatterService = messageFormatterService;
    }

    @Override
    public void handleAction(Long chatId, String action, String[] args) {
        List<OrderResponseDto> allOrders
                = orderService.getAllOrders(PageRequest.of(0, 25));
        telegramBot.sendMessage(chatId, messageFormatterService.formatOrdersMessage(allOrders));
    }

    @Override
    public String getAction() {
        return ACTION;
    }
}
