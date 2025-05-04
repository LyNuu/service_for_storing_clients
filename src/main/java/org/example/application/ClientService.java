package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.model.Account;
import org.example.domain.model.Client;
import org.example.infrastructure.persistence.ClientRepository;
import org.example.domain.ports.ClientServicePort;

import java.util.List;

@AllArgsConstructor
public class ClientService implements ClientServicePort {
    private final ClientRepository repository;

    @Override
    public Client create(String clientName) {
        Client entity = Client.builder()
                .name(clientName)
                .build();
        return repository.create(entity);

    }

    @Override
    public List<Account> getAllAccounts(Integer clientId) {
        return repository.getAllAccounts(clientId);
    }

    @Override
    public void transfer(Integer clientId, double amount, Integer fromAccountNumber, Integer toAccountNumber) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Нельзя переводить деньги самому себе!");
        }
        try {
            repository.transfer(clientId, amount, fromAccountNumber, toAccountNumber);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
