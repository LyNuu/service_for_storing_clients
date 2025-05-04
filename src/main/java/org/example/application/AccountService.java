package org.example.application;

import lombok.AllArgsConstructor;
import org.example.domain.model.Account;
import org.example.domain.model.Currency;
import org.example.domain.ports.AccountServicePort;
import org.example.infrastructure.persistence.AccountRepository;

@AllArgsConstructor
public class AccountService implements AccountServicePort {
    private final AccountRepository accountRepository;

    @Override
    public Account create(Integer clientId, String currency, double amount) {
        Currency currencyEnum;
        currencyEnum = Currency.valueOf(currency);
        var account = Account.builder().currency(currencyEnum).amount(amount).build();
        return accountRepository.create(clientId, account);
    }
}
