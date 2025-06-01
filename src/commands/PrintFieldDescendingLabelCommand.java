package commands;

import entities.MusicBand;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Команда: выводит id и количество bands (лейбла) всех объектов коллекции
 * в порядке убывания значения bands.
 */
public class PrintFieldDescendingLabelCommand implements Command {

    String[] args;
    String textCommand;

    public PrintFieldDescendingLabelCommand(String[] Args, Integer argsCount, String textCommand) {
        this.args = Args;
        this.textCommand = textCommand;
    }

    @Override
    public String execute() {
        LinkedList<MusicBand> collection = Console.entities;
        if (collection.isEmpty()) {
            return "Коллекция пуста.";
        }

        StringBuilder result = new StringBuilder();

        collection.stream()
                .sorted(Comparator.comparing(
                        (MusicBand band) -> band.getLabel().getBands(),
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .forEach(band -> result.append("ID: ")
                        .append(band.getId())
                        .append(", Лейбл (bands): ")
                        .append(band.getLabel().getBands() == null ? "null" : band.getLabel().getBands())
                        .append("\n"));

        return result.isEmpty() ? "В коллекции нет значений label." : result.toString();
    }

    @Override
    public String toString() {
        return textCommand;
    }
}
