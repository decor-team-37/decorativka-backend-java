package teamproject.decorativka.service.impl;

import org.springframework.stereotype.Service;
import teamproject.decorativka.model.Order;
import teamproject.decorativka.model.OrderItem;
import teamproject.decorativka.service.EmailContentService;

@Service
public class EmailContentServiceImpl implements EmailContentService {
    @Override
    public String createOrderConfirmationContent(Order order) {
        String htmlContent = "<html>"
                + "<body>"
                + "<h1>Підтвердження замовлення</h1>"
                + "<p>Дякуємо за ваше замовлення!</p>"
                + "<p>Номер вашого замовлення: <strong>" + order.getId() + "</strong></p>"
                + "<p>Ім'я: " + order.getFirstName() + "</p>"
                + "<p>Прізвище: " + order.getLastName() + "</p>"
                + "<p>Адреса доставки: " + order.getShippingAddress() + "</p>"
                + "<p>Дата замовлення: " + order.getOrderDate() + "</p>"
                + "<p>Сума замовлення: " + order.getTotal() + "</p>"
                + "<p>Статус: " + order.getStatus() + "</p>";
        return getOrderItemsInfo(order, htmlContent);
    }

    @Override
    public String createStatusUpdateContent(Order order, String newStatus) {
        String htmlContent = "<html>"
                + "<body>"
                + "<h1>Оновлення статусу замовлення</h1>"
                + "<p>Статус вашого замовлення <strong>#" + order.getId()
                + "</strong> було оновлено.</p>"
                + "<p>Новий статус: <strong>" + newStatus + "</strong></p>"
                + "<p>Ім'я: " + order.getFirstName() + "</p>"
                + "<p>Прізвище: " + order.getLastName() + "</p>"
                + "<p>Адреса доставки: " + order.getShippingAddress() + "</p>"
                + "<p>Дата замовлення: " + order.getOrderDate() + "</p>";
        return getOrderItemsInfo(order, htmlContent);
    }

    private String getOrderItemsInfo(Order order, String htmlContent) {
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            htmlContent += "<h2>Деталі замовлення:</h2><ul>";
            StringBuilder htmlContentBuilder = new StringBuilder(htmlContent);
            for (OrderItem item : order.getOrderItems()) {
                htmlContentBuilder
                        .append("<li>")
                        .append(item.getProduct().getName())
                        .append(" - Кількість: ")
                        .append(item.getQuantity())
                        .append("</li>");
            }
            htmlContent = htmlContentBuilder.toString();
            htmlContent += "</ul>";
        }
        htmlContent += "</body></html>";
        return htmlContent;
    }
}
