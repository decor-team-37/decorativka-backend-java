package teamproject.decorativka.service;

import teamproject.decorativka.model.Order;

public interface EmailContentService {
    String createOrderConfirmationContent(Order order);

    String createStatusUpdateContent(Order order, String newStatus);
}
