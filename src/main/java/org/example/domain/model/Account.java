package org.example.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    @SequenceGenerator(name = "account_seq",
            sequenceName = "account_sequence",
            initialValue = 22_391_143,
            allocationSize = 1)
    Integer number;
    @Enumerated(EnumType.STRING)
    Currency currency;
    Double amount;
    @ManyToOne
    @JoinColumn(name = "client_id")
    @ToString.Exclude
    private Client client;

    public void checkRemovalFromBalance(double amount) {
        if (this.amount < amount) {
            throw new IllegalArgumentException("Недостаточно средств на счете " + this.number);
        }
    }

    public void checkCurrencyValidation(Currency currency) {
        if (!this.currency.equals(currency)) {
            throw new IllegalArgumentException("Несоответствие валют: " + this.currency + " → " + currency);
        }
    }
}
