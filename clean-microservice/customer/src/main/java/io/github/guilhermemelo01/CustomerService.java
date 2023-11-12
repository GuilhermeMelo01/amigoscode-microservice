package io.github.guilhermemelo01;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {

    public void insert(CustomerRequest customerRequest) {
        Customer customer = Customer.builder().id(randomIdGenerator()).firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName()).email(customerRequest.email()).build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId());

        if (fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }
    }


    private String randomIdGenerator() {
        return UUID.randomUUID().toString();
    }


}
