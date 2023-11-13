package io.github.guilhermemelo01;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {

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

        restTemplate.postForObject("http://NOTIFICATION/api/v1/notification",
                new CustomerNotificationRequest(
                        customer.getId(), customer.getEmail(),
                        "Customer " + customer.getFirstName() + customer.getEmail() + " registed successful"),
                Void.class);
    }


    private String randomIdGenerator() {
        return UUID.randomUUID().toString();
    }


}
