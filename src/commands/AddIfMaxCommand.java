package commands;

import exceptions.NotAnIntegerException;
import entities.MusicBand;

import java.util.*;

public class AddIfMaxCommand implements Command {
    private AddCommand adder;
    String command;
    String[] args;
    int argsCount;

    //static {addCommand adder = new addCommand("System",8);}
    public AddIfMaxCommand(String[] Args, int argsCount, String textcommand) {
        //super(command, argsCount,undo);
        //check(argsCount,Args);
        this.args = Args;
        this.argsCount = argsCount;
        this.command = textcommand;
    }

    @Override
    public String execute() throws NotAnIntegerException {
        check(argsCount, args);
        int x = Integer.parseInt(args[2]);
        float y = Float.parseFloat(args[3]);
        int participants = Integer.parseInt(args[4]);
        int albums = Integer.parseInt(args[5]);
        int label = Integer.parseInt(args[8]);

        String key = args[0].toLowerCase();

        Optional<MusicBand> maxBand = switch (key) {
            case "albums" -> Console.entities.stream().max(Comparator.comparing(MusicBand::getSingles));
            case "participants" -> Console.entities.stream().max(Comparator.comparing(MusicBand::getParticipants));
            case "cordx" -> Console.entities.stream().max(Comparator.comparing(b -> b.getCoordinates().getX()));
            case "cordy" -> Console.entities.stream().max(Comparator.comparing(b -> b.getCoordinates().getY()));
            case "label" -> Console.entities.stream().max(Comparator.comparing(b -> b.getLabel().getBands()));
            default -> Optional.empty();
        };

        if (maxBand.isEmpty() & Console.entities.isEmpty()) {
            System.out.println("Коллекция пуста, объект будет добавлен в любом случае.");
            return create(args);
        } else if (maxBand.isEmpty()) {
            return "Неизвестный параметр сравнения.\n" +
                    "   Введено: " + args[0] + "\n" +
                    "   Поддерживается: albums/participants/cordx/cordy/label";
        }

        boolean shouldAdd = switch (key) {
            case "albums" -> maxBand.get().getSingles() < albums;
            case "participants" -> maxBand.get().getParticipants() < participants;
            case "cordx" -> maxBand.get().getCoordinates().getX() < x;
            case "cordy" -> maxBand.get().getCoordinates().getY() < y;
            case "label" -> maxBand.get().getLabel().getBands() < label;
            default -> false;
        };

        if (shouldAdd) {
            return create(args);
        }

        String currentValue = switch (key) {
            case "albums" -> String.valueOf(maxBand.get().getSingles());
            case "participants" -> String.valueOf(maxBand.get().getParticipants());
            case "cordx" -> String.valueOf(maxBand.get().getCoordinates().getX());
            case "cordy" -> String.valueOf(maxBand.get().getCoordinates().getY());
            case "label" -> String.valueOf(maxBand.get().getLabel().getBands());
            default -> "?";
        };

        return "Объект не был создан, так как максимальное существующее значение " +
                key + " (" + currentValue + ") больше или равно введённому (" +
                switch (key) {
                    case "albums" -> albums;
                    case "participants" -> participants;
                    case "cordx" -> x;
                    case "cordy" -> y;
                    case "label" -> label;
                    default -> "?";
                } + ")";
    }

    public String create(String[] args) {
        return CommandRegister.commands.get("add").create(Arrays.copyOfRange(args, 1, args.length)).execute();
    }

    @Override
    public String toString() {
        return this.command;
    }
}

class CompareSingles implements Comparator<MusicBand> {

    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return Long.compare(o1.getSingles(), o2.getSingles());
    }
}

class CompareParticipants implements Comparator<MusicBand> {

    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return Integer.compare(o1.getParticipants(), o2.getParticipants());
    }
}

class CompareCordX implements Comparator<MusicBand> {

    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return Float.compare(o1.getCoordinates().getX(), o2.getCoordinates().getX());
    }
}

class CompareCordY implements Comparator<MusicBand> {

    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return Double.compare(o1.getCoordinates().getY(), o2.getCoordinates().getY());
    }
}

class CompareLabel implements Comparator<MusicBand> {

    @Override
    public int compare(MusicBand o1, MusicBand o2) {
        return Double.compare(o1.getLabel().getBands(), o2.getLabel().getBands());
    }
}
