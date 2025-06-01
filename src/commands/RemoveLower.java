package commands;

import entities.MusicBand;
import exceptions.NotAnIntegerException;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class RemoveLower implements Command {
    String[] args;
    String textCommand;
    int argsCount;

    public RemoveLower(String[] Args, Integer argsCount, String textCommand) {
        //super(command, argsCount,undo);

        this.textCommand = textCommand;
        this.args = Args;
        this.argsCount = argsCount;
    }

    @Override
    public String execute() throws NotAnIntegerException {
        check(argsCount, args);

        int upperBound;
        try {
            upperBound = Integer.parseInt(args[0]);
            if (upperBound < 0) {
                throw new NotAnIntegerException("Введённое значение не может быть интерпретировано как положительный индекс");
            }
        } catch (NumberFormatException e) {
            throw new NotAnIntegerException("Введённая строка не может быть интерпретирована как индекс");
        }

        List<Long> idsToRemove = Console.entities.stream()
                .map(MusicBand::getId)
                .filter(id -> id < upperBound)
                .distinct()
                .toList();

        //history.add(new LinkedList<>());

        StringBuilder result = new StringBuilder("Отчёт об удалении " + idsToRemove.size() + " объектов:\n");

        idsToRemove.forEach(id -> {
            try {
                var band = Console.entities.stream()
                        .filter(b -> b.getId() <= id)
                        .findFirst()
                        .orElseThrow();

                //history.getLast().add(band.getGrandcommand());
                Console.entities.remove(band);
                result.append("Объект с id ").append(id).append(" удалён из коллекции\n");
            } catch (Exception e) {
                result.append("Объект с id ").append(id).append(" не существует или уже удалён\n");
            }
        });

        return result.toString();
    }

    @Override
    public String toString() {
        return this.textCommand;
    }
}
