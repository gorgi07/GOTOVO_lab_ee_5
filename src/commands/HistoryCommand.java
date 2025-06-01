package commands;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

public class HistoryCommand implements Command {
    String[] args;
    String textCommand;

    public HistoryCommand(String[] Args, Integer argsCount, String textCommand) {
        this.textCommand = textCommand;
        this.args = Args;
    }

    @Override
    public String execute() {
        LinkedList<Map<String, Command>> history = Console.history;

        if (history.isEmpty()) {
            return "История пуста.";
        }

        StringBuilder result = new StringBuilder("Последние команды:\n");

        ListIterator<Map<String, Command>> it = history.listIterator(history.size());
        int count = 0;

        while (it.hasPrevious() && count < 7) {
            Map<String, Command> map = it.previous();
            String commandName = map.keySet().iterator().next(); // Получаем ключ из Map
            result.append("- ").append(commandName).append("\n");
            count++;
        }

        return result.toString();
    }

    @Override
    public String toString() {
        return this.textCommand;
    }
}
