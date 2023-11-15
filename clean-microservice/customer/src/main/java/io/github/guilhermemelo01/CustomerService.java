package io.github.guilhermemelo01;

import io.github.guilhermemelo01.dto.CustomerNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Value("${rabbitmq.queue.name}")
    private String queue;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routingKey.name}")
    private String routingKey;

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;


    public void insertCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder().id(randomIdGenerator()).firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName()).email(customerRequest.email()).build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        rabbitTemplate.convertAndSend(exchange, routingKey, new CustomerNotificationRequest(
                customer.getId(),
                customer.getEmail(),
                "Customer " + customer.getFirstName() +" "+ customer.getEmail() + " registed successful"));
    }


    private String randomIdGenerator() {
        return UUID.randomUUID().toString();
    }


}
