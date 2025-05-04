package org.example.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "client")
    @ToString.Exclude
    private List<Account> accounts;

    public void checkIdValidation(Integer id, Integer accountNumber) {
        if (!this.id.equals(id)) {
            throw new IllegalArgumentException("Перевод не возможен. Вы не владелец счета: " + accountNumber);
        }
    }
}
