package org.example.domain.ports;

import org.example.domain.model.Account;
import org.example.domain.model.Client;

import java.util.List;

public interface ClientRepositoryPort {
    Client create(Client entity);

    List<Account> getAllAccounts(Integer clientId);

    void transfer(Integer clientId, double amount, Integer fromAccountId, Integer toAccountId);
}
