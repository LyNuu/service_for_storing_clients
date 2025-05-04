package org.example.infrastructure.controller;

import org.example.application.ClientService;
import org.example.domain.model.Account;

import java.util.List;

public class GetAllAccountsParse extends Command {
    private final ClientService clientService;

    public GetAllAccountsParse(Command nextCommand, ClientService clientService) {
        super(nextCommand);
        this.clientService = clientService;
    }

    @Override
    public String parse(String command) {
        String[] commandPars = command.split(" ");
        if (commandPars[0].equals("get_accounts") && commandPars.length == 2) {
            List<Account> accounts = clientService.getAllAccounts(Integer.parseInt(commandPars[1]));
            accounts.forEach(account ->
                    System.out.println("Account: " + account.getNumber() + " " + account.getCurrency() + " " + account.getAmount()));
        }
        return super.parse(command);
    }
}
