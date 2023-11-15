package io.github.guilhermemelo01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerNotificationRequest implements Serializable {
    String customerId;
    String customerName;
    String message;
}
