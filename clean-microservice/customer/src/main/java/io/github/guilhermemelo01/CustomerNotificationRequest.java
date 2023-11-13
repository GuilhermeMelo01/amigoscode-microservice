package io.github.guilhermemelo01;

public record CustomerNotificationRequest(String customerId, String customerName, String message) {
}
