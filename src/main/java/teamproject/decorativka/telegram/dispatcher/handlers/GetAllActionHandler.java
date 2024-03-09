package teamproject.decorativka.telegram.dispatcher.handlers;

import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import teamproject.decorativka.dto.order.OrderItemResponseDto;
import teamproject.decorativka.dto.order.OrderResponseDto;
import teamproject.decorativka.service.OrderService;
import teamproject.decorativka.telegram.TelegramBot;
import teamproject.decorativka.telegram.dispatcher.ActionHandler;

@Component
public class GetAllActionHandler implements ActionHandler {
    private static final String ACTION = "get_all_orders";
    private final OrderService orderService;
    private final TelegramBot telegramBot;

    public GetAllActionHandler(OrderService orderService, @Lazy TelegramBot telegramBot) {
        this.orderService = orderService;
        this.telegramBot = telegramBot;
    }

    @Override
    public void handleAction(Long chatId, String action, String[] args) {
        List<OrderResponseDto> allOrders
                = orderService.getAllOrders(PageRequest.of(0, 25));
        telegramBot.sendMessage(chatId, formatOrdersMessage(allOrders));
    }

    @Override
    public String getAction() {
        return ACTION;
    }

    private String formatOrdersMessage(List<OrderResponseDto> orders) {
        StringBuilder messageBuilder = new StringBuilder();
        for (OrderResponseDto order : orders) {
            messageBuilder.append("Замовлення ID: ").append(order.id()).append("\n");
            messageBuilder.append("Ім'я: ").append(order.firstName()).append("\n");
            messageBuilder.append("Прізвище: ").append(order.lastName()).append("\n");
            messageBuilder.append("Адреса доставки: ").append(order.shippingAddress()).append("\n");
            messageBuilder.append("Email: ").append(order.email()).append("\n");
            messageBuilder.append("Телефон: ").append(order.phoneNumber()).append("\n");
            messageBuilder.append("Статус: ").append(order.status()).append("\n");
            messageBuilder.append("Всього: ").append(order.total()).append("\n");
            messageBuilder.append("Дата замовлення: ").append(order.orderDate()).append("\n");
            messageBuilder.append("Товари:\n");

            for (OrderItemResponseDto item : order.orderItems()) {
                messageBuilder.append(" - ID товару: ").append(item.productId())
                        .append(", Кількість: ").append(item.quantity())
                        .append(", Ціна: ").append(item.price()).append("\n");
            }
            messageBuilder.append("\n");
        }
        return messageBuilder.toString();
    }
}
