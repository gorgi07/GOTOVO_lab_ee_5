package commands;

//import comparators.GenreComparator;

import entities.MusicBand;
import entities.MusicGenre;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда: выводит id и жанр всех объектов в коллекции,
 * отсортированных по жанру в порядке возрастания.
 */
public class PrintFieldAscendingGenreCommand implements Command {
    String[] args;
    String textCommand;

    public PrintFieldAscendingGenreCommand(String[] Args, Integer argsCount, String textCommand) {
        check(argsCount, Args);
        this.args = Args;
        this.textCommand = textCommand;
    }

    @Override
    public String execute() {
        LinkedList<MusicBand> collection = Console.entities;
        if (collection.isEmpty()) {
            return "Коллекция пуста.";
        }

        String result = collection.stream()
                .filter(b -> b.getGenre() != null)
                .sorted(Comparator.comparing(MusicBand::getGenre, new GenreComparator()))
                .map(b -> "ID: " + b.getId() + ", Жанр: " + b.getGenre().name())
                .collect(Collectors.joining("\n"));

        if (result.isEmpty()) {
            return "В коллекции нет элементов с жанром.";
        }

        return result;
    }

    @Override
    public String toString() {
        return textCommand;
    }
}

/**
 * Компаратор жанров по имени (в алфавитном порядке).
 */
class GenreComparator implements Comparator<MusicGenre> {
    @Override
    public int compare(MusicGenre g1, MusicGenre g2) {
        if (g1 == null && g2 == null) return 0;
        if (g1 == null) return 1;
        if (g2 == null) return -1;
        return g1.name().compareTo(g2.name());
    }
}
