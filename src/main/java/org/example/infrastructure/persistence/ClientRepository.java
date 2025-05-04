package org.example.infrastructure.persistence;

import org.example.domain.model.Account;
import org.example.domain.model.Client;
import org.example.domain.ports.ClientRepositoryPort;
import org.hibernate.Hibernate;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements ClientRepositoryPort {

    @Override
    public Client create(Client entity) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public List<Account> getAllAccounts(Integer clientId) {
        List<Account> accounts = new ArrayList<>();
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Client client = session.get(Client.class, clientId);
            if (client != null) {
                Hibernate.initialize(client.getAccounts());
                accounts = client.getAccounts();
            }
            session.getTransaction().commit();
        }
        return accounts;
    }

    @Override
    public void transfer(Integer clientId, double amount, Integer fromAccountNumber, Integer toAccountNumber) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Account fromAccount = session.get(Account.class, fromAccountNumber);
            Client client = session.get(Client.class, fromAccount.getClient().getId());
            Account toAccount = session.get(Account.class, toAccountNumber);
            try {
                fromAccount.checkRemovalFromBalance(amount);
                client.checkIdValidation(clientId, fromAccountNumber);
                fromAccount.checkCurrencyValidation(toAccount.getCurrency());
            } catch (IllegalArgumentException e) {
                session.getTransaction().rollback();
                throw e;
            }
            fromAccount.setAmount(fromAccount.getAmount() - amount);
            toAccount.setAmount(toAccount.getAmount() + amount);
            session.merge(fromAccount);
            session.merge(toAccount);
            session.getTransaction().commit();
        }
    }
}
