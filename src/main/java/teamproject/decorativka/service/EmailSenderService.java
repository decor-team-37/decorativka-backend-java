package teamproject.decorativka.service;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String text);
}
