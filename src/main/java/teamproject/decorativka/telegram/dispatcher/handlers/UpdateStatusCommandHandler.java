package teamproject.decorativka.telegram.dispatcher.handlers;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import teamproject.decorativka.dto.order.OrderResponseDto;
import teamproject.decorativka.model.Order;
import teamproject.decorativka.service.OrderService;
import teamproject.decorativka.telegram.MessageFormatterService;
import teamproject.decorativka.telegram.TelegramBot;
import teamproject.decorativka.telegram.dispatcher.CommandHandler;

@Component
public class UpdateStatusCommandHandler implements CommandHandler {
    private static final String COMMAND = "/update_order";
    private final TelegramBot telegramBot;
    private final OrderService orderService;
    private final MessageFormatterService messageFormatterService;

    public UpdateStatusCommandHandler(@Lazy TelegramBot telegramBot,
                                      OrderService orderService,
                                      MessageFormatterService messageFormatterService) {
        this.telegramBot = telegramBot;
        this.orderService = orderService;
        this.messageFormatterService = messageFormatterService;
    }

    @Override
    public void handleCommand(Long chatId, String command, String[] args) {
        if (args == null || args.length != 2) {
            telegramBot.sendMessage(chatId, "Будь ласка, введіть команду у форматі:"
                    + " /update_order [номер замовлення] [новий статус]");
            return;
        }
        try {
            Long orderId = Long.parseLong(args[0]);
            String status = args[1].toUpperCase();

            if (!isValidStatus(status)) {
                telegramBot.sendMessage(chatId, "Невірний статус."
                        + " Доступні статуси: COMPLETED, PENDING, DELIVERED, PROCESSING");
                return;
            }
            OrderResponseDto updatedOrder = orderService.updateOrderStatus(orderId, status);
            telegramBot.sendMessage(chatId, "Статус замовлення оновлено: "
                    + messageFormatterService.formatOrderResponse(updatedOrder));
        } catch (NumberFormatException e) {
            telegramBot.sendMessage(chatId, "Невірний формат номера замовлення.");
        } catch (Exception e) {
            telegramBot.sendMessage(chatId, "Помилка при оновленні замовлення: " + e.getMessage());
            throw new RuntimeException("Can't update order status", e);
        }
    }

    @Override
    public String getCommand() {
        return COMMAND;
    }

    private boolean isValidStatus(String status) {
        for (Order.Status s : Order.Status.values()) {
            if (s.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
}
