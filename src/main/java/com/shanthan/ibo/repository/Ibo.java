package com.shanthan.ibo.repository;

import com.shanthan.ibo.domain.Address;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Ibo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long iboNumber;
    private String firstName;
    private String lastName;

    private Address address;
    private LocalDate dateOfBirth;
}
