package commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Host {
    public String command = "";

    default String command(String args) {
        return "200";
    }

    default String getCommand() {
        return command;
    }

    default List<Map<String, Command>> getHistory() {
        return List.of();
    }

    default Map<String, Command> getCommands() {
        return new HashMap<>();
    }

    ;
}
