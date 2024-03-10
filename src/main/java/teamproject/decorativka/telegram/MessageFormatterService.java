package teamproject.decorativka.telegram;

import java.util.List;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.order.OrderItemResponseDto;
import teamproject.decorativka.dto.order.OrderResponseDto;

@Service
public class MessageFormatterService {
    public String formatOrdersMessage(List<OrderResponseDto> orders) {
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

    public String formatOrderResponse(OrderResponseDto order) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("Замовлення ID: ").append(order.id()).append("\n");
        messageBuilder.append("Ім'я: ").append(order.firstName()).append("\n");
        messageBuilder.append("Прізвище: ").append(order.lastName()).append("\n");
        if (order.patronymic() != null && !order.patronymic().isEmpty()) {
            messageBuilder.append("По батькові: ").append(order.patronymic()).append("\n");
        }
        messageBuilder.append("Адреса доставки: ").append(order.shippingAddress()).append("\n");
        messageBuilder.append("Email: ").append(order.email()).append("\n");
        messageBuilder.append("Телефон: ").append(order.phoneNumber()).append("\n");
        if (order.comment() != null && !order.comment().isEmpty()) {
            messageBuilder.append("Коментар: ").append(order.comment()).append("\n");
        }
        messageBuilder.append("Статус: ").append(order.status()).append("\n");
        messageBuilder.append("Загальна сума: ").append(order.total()).append("\n");
        messageBuilder.append("Дата замовлення: ").append(order.orderDate()).append("\n");

        if (order.orderItems() != null && !order.orderItems().isEmpty()) {
            messageBuilder.append("Товари:\n");
            for (OrderItemResponseDto item : order.orderItems()) {
                messageBuilder.append(" - ID товару: ").append(item.productId())
                        .append(", Кількість: ").append(item.quantity())
                        .append(", Ціна за одиницю: ").append(item.price()).append("\n");
            }
        } else {
            messageBuilder.append("Товари: Відсутні\n");
        }

        return messageBuilder.toString();
    }

}
