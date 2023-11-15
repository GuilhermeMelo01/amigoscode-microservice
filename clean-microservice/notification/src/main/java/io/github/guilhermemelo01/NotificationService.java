package io.github.guilhermemelo01;

import io.github.guilhermemelo01.dto.CustomerNotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void sendNotification(CustomerNotificationRequest customerNotificationRequest){
        log.info("Notification: " + customerNotificationRequest);
        Notification notification = Notification.builder().id(generateUUID()).customerId(customerNotificationRequest.getCustomerId())
                .customerName(customerNotificationRequest.getCustomerName())
                .message(customerNotificationRequest.getMessage()).build();

        notificationRepository.save(notification);
    }

    private static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
