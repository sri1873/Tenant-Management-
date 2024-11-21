package com.tenant.management.rental.implementation;

import com.tenant.management.rental.entities.Command;
import com.tenant.management.utils.ApiResponse;

import java.util.Stack;

public class CommandInvoker {
    private final Stack<Command> commandHistory = new Stack<>();

    public ApiResponse executeCommand(Command command) {
        ApiResponse execute = command.execute();
        commandHistory.push(command);
        return execute;

    }

    public ApiResponse undoLastCommand() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            return lastCommand.undo();
        }
        return null;
    }
}
