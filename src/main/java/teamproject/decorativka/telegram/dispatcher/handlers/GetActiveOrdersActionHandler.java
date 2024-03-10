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
public class GetActiveOrdersActionHandler implements ActionHandler {
    private static final String ACTION = "get_open_orders";

    private final TelegramBot telegramBot;
    private final OrderService orderService;
    private final MessageFormatterService messageFormatterService;

    public GetActiveOrdersActionHandler(@Lazy TelegramBot telegramBot,
                                        OrderService orderService,
                                        MessageFormatterService messageFormatterService) {
        this.telegramBot = telegramBot;
        this.orderService = orderService;
        this.messageFormatterService = messageFormatterService;
    }

    @Override
    public void handleAction(Long chatId, String action, String[] args) {
        List<OrderResponseDto> allNotCompletedOrders
                = orderService.getAllNotCompletedOrders(PageRequest.of(0, 25));
        telegramBot.sendMessage(chatId, messageFormatterService
                .formatOrdersMessage(allNotCompletedOrders));

    }

    @Override
    public String getAction() {
        return ACTION;
    }
}
