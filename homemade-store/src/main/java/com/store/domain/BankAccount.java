package com.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bankAccounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bankAccountCVV;

    @Column(name = "cardNumber")
    private String cardNumber;

    @Column(name = "accountNumber")
    private String accountNumber;

    @Column(name = "balance")
    private double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bankAccount")
    private List<Order> orders;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customerId")
    private Customer customer;
}
