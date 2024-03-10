package teamproject.decorativka.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import teamproject.decorativka.dto.feedback.FeedbackRequestDto;
import teamproject.decorativka.service.EmailContentService;
import teamproject.decorativka.service.EmailSenderService;
import teamproject.decorativka.service.FeedbackService;
import teamproject.decorativka.telegram.TelegramAdminService;
import teamproject.decorativka.telegram.TelegramBot;

@RequiredArgsConstructor
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final TelegramBot telegramBot;
    private final TelegramAdminService telegramAdminService;
    private final EmailSenderService emailSenderService;
    private final EmailContentService emailContentService;

    @Override
    public void processFeedback(FeedbackRequestDto requestDto) {
        emailSenderService.sendEmail(requestDto.email(),
                "Дякуємо за ваше зверення",
                emailContentService.createFeedbackEmailContend(requestDto));
        sendTelegramMessage(createTelegramMessage(requestDto));
    }

    private void sendTelegramMessage(String text) {
        Set<Long> allAdminsChatIds = telegramAdminService.getAllAdminsChatIds();
        for (Long adminChatId : allAdminsChatIds) {
            telegramBot.sendMessage(adminChatId, text);
        }
    }

    private String createTelegramMessage(FeedbackRequestDto requestDto) {
        return "Нове звернення від користувача:\n"
                + "Ім'я: " + requestDto.name() + "\n"
                + "Email: " + requestDto.email() + "\n"
                + "Коментар: " + requestDto.comment() + "\n";
    }
}

