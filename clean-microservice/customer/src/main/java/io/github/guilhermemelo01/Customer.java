package io.github.guilhermemelo01;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Entity
public class Customer {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public Customer(){
        this.id = UUID.randomUUID().toString();
    }
}
