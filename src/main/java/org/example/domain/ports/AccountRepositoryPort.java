package org.example.domain.ports;

import org.example.domain.model.Account;

public interface AccountRepositoryPort {
    Account create(Integer clientId, Account entity);
}
