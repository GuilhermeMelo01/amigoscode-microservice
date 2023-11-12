package io.github.guilhermemelo01;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FraudCheckService {

    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

    public Boolean isFraudlentCustomer(String customerId) {
        fraudCheckHistoryRepository.save(FraudCheckHistory.builder()
                .id(generatorUUID())
                .customerId(customerId)
                .isFraudster(false)
                .createdAt(LocalDateTime.now())
                .build());
        return false;
    }

    private String generatorUUID() {
        return UUID.randomUUID().toString();
    }
}
