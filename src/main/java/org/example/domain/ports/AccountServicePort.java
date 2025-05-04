package org.example.domain.ports;

import org.example.domain.model.Account;

public interface AccountServicePort {
    Account create(Integer clientId, String currency, double amount);
}
