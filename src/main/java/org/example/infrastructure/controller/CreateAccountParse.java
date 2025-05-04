package org.example.infrastructure.controller;

import org.example.application.AccountService;
import org.example.domain.model.Account;

public class CreateAccountParse extends Command {
    private final AccountService accountService;

    public CreateAccountParse(Command nextCommand, AccountService accountService) {
        super(nextCommand);
        this.accountService = accountService;
    }

    @Override
    public String parse(String command) {
        String[] commandPars = command.split(" ");
        if (commandPars[0].equals("create_account") && commandPars.length == 4) {
            Account account = accountService.create(Integer.parseInt(commandPars[1]), commandPars[2], Double.parseDouble(commandPars[3]));
            return "Account created: " + account.getNumber();
        }
        return super.parse(command);
    }
}
