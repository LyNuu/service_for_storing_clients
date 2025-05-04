package org.example;

import org.example.application.AccountService;
import org.example.application.ClientService;
import org.example.infrastructure.controller.*;
import org.example.infrastructure.persistence.AccountRepository;
import org.example.infrastructure.persistence.ClientRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var clientRepository = new ClientRepository();
        var clientService = new ClientService(clientRepository);
        var accountRepository = new AccountRepository();
        var accountService = new AccountService(accountRepository);

        var createAccount = new CreateAccountParse(null, accountService);
        var createClient = new CreateClientParse(createAccount, clientService);
        var getAllAccount = new GetAllAccountsParse(createClient, clientService);
        var transferFromAccount = new TransferFromAccountParse(getAllAccount, clientService);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Список доступных команд:\n");
            System.out.println("Создать клиента : create_client 'Фамилия_Имя_Отчество'");
            System.out.println("Создать счет : create_account 'Ваш Id' 'Валюта в которой хотите хранить' 'Сколько изначально хотите положить денег на счет'");
            System.out.println("Получить список всех счетов клиента: get_accounts 'Ваш Id'");
            System.out.println("Перевод средст с одного счета на другой: transfer 'Ваш Id' 'Сумма которую хотите перевести' 'Номер счета откуда хотите перевести' 'Номер счета куда хотите перевести'");
            String input = scanner.nextLine();
            if (input.equals("q")) return;
            transferFromAccount.parse(input);

        }

    }
}