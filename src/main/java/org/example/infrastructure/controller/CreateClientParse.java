package org.example.infrastructure.controller;

import org.example.application.ClientService;
import org.example.domain.model.Client;

public class CreateClientParse extends Command {
    private final ClientService clientService;

    public CreateClientParse(Command nextCommand, ClientService clientService) {
        super(nextCommand);
        this.clientService = clientService;
    }

    @Override
    public String parse(String command) {
        String[] commandPars = command.split(" ");
        if (commandPars[0].equals("create_client") && commandPars.length == 2) {
            Client client = clientService.create(commandPars[1]);
            return "Client created: " + client.getId() + " " + client.getName();
        }
        return super.parse(command);
    }
}
