package org.example.infrastructure.controller;

import org.example.application.ClientService;

public class TransferFromAccountParse extends Command {
    private final ClientService clientService;

    public TransferFromAccountParse(Command nextCommand, ClientService clientService) {
        super(nextCommand);
        this.clientService = clientService;
    }

    @Override
    public String parse(String command) {
        String[] commandPars = command.split(" ");
        if (commandPars[0].equals("transfer") && commandPars.length == 5) {
            try {
                clientService.transfer(Integer.parseInt(commandPars[1]), Double.parseDouble(commandPars[2]), Integer.parseInt(commandPars[3]), Integer.parseInt(commandPars[4]));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return super.parse(command);
    }
}