package commands;

import entities.MusicBand;

import java.util.LinkedList;

public class ClearCommand implements Command {
    private LinkedList<LinkedList<String>> history = new LinkedList<>();
    String command;
    String[] args;

    public ClearCommand(String[] Args, Integer argsCount, String textCommand) {
        //super(command, argsCount,undo);
        check(argsCount, Args);
        this.command = textCommand;
        this.args = Args;
    }

    @Override
    public String execute() {
        int len = Console.entities.size();

        // Сохраняем историю через Stream API

        Console.entities.clear();
        return "Коллекция очищена: " + len + " объектов удалены успешно";
    }

    @Override
    public String toString() {
        return this.command;
    }
}
