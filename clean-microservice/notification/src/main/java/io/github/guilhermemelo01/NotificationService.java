package io.github.guilhermemelo01;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record NotificationService(NotificationRepository notificationRepository) {

    public void sendNotification(NotificationRequest notificationRequest){
        Notification notification = Notification.builder().id(generateUUID()).customerId(notificationRequest.customerId())
                .customerName(notificationRequest.customerName())
                .message(notificationRequest.message()).build();

        notificationRepository.save(notification);
    }

    private static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
