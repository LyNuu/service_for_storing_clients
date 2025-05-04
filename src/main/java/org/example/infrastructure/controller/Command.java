package org.example.infrastructure.controller;

public abstract class Command {
    protected Command nextCommand;

    public Command(Command nextCommand) {
        this.nextCommand = nextCommand;
    }

    public String parse(String command) {

        if (nextCommand != null)
            return nextCommand.parse(command);

        return String.format("Unknown command: %s", command);
    }
}
