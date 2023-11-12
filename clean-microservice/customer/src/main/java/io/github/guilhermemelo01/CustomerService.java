package io.github.guilhermemelo01;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record CustomerService(CustomerRepository customerRepository){
    public void insert(CustomerRequest customerRequest){
        Customer customer = Customer.builder().id(randomIdGenerator()).firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName()).email(customerRequest.getEmail()).build();
        customerRepository.save(customer);

    }

    private String randomIdGenerator() {
        return UUID.randomUUID().toString();
    }


}
