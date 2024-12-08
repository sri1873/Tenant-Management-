package com.tenant.management.rental.implementation;

import com.tenant.management.rental.entities.Command;
import com.tenant.management.utils.ApiResponse;

import java.util.ArrayDeque;
import java.util.Deque;

//Author : K S SRI KUMAR
//Id : 24177474
public class CommandInvoker {
    private final Deque<Command> commandHistory = new ArrayDeque<>();

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
