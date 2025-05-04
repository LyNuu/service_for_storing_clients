package org.example.infrastructure.persistence;

import lombok.AllArgsConstructor;
import org.example.domain.model.Account;
import org.example.domain.model.Client;
import org.example.domain.ports.AccountRepositoryPort;
import org.hibernate.cfg.Configuration;

@AllArgsConstructor
public class AccountRepository implements AccountRepositoryPort {
    @Override
    public Account create(Integer clientId, Account entity) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Client client = session.get(Client.class, clientId);
            if (client == null) {
                System.out.println("Клиент с Id: " + clientId + " не найден.");
                session.getTransaction().rollback();
                return entity;
            }
            entity.setClient(client);
            session.persist(entity);
            session.getTransaction().commit();
        }
        return entity;
    }
}
