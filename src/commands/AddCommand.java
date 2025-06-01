package commands;

import entities.Coordinates;
import entities.MusicBand;
import entities.MusicGenre;
import entities.Label;

import java.util.Objects;

public class AddCommand implements Command {
    String[] args;
    String textCommand;
    int argsCount;

    public AddCommand(String[] Args, Integer argsCount, String textCommand) {
        //super(command, argsCount,undo);
        this.args = Args;
        this.argsCount = argsCount;
        this.textCommand = textCommand;

    }

    @Override
    public String execute() {
        check(argsCount, args);
        MusicGenre genre;
        //System.out.println(grandcommand);
        Coordinates coordinates;
        try {
            coordinates = new Coordinates(Float.parseFloat(args[1]), Double.parseDouble(args[2]));
        } catch (RuntimeException e) {
            throw new RuntimeException("Координаты не были сформированны из-за ошибки: " + e.getMessage());
        }
        try {
            genre = MusicGenre.valueOf(args[6].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Невозможно создать альбом жанра " + args[6]);
        }
        try {
            MusicBand newBand = (new MusicBand(args[0].replace("_", " "), coordinates, Integer.parseInt(args[3]), Objects.equals(args[4], "null") ? null : Long.parseLong(args[4]), Objects.equals(args[5], "null") ? null : args[5], genre, new Label(Integer.parseInt(args[7]))));
            try {
                newBand.setCreationDate(args[8]);
                newBand.setId(Long.parseLong(args[9]));
            } catch (RuntimeException ignore) {

            }
            Console.entities.add(newBand);
        } catch (RuntimeException e) {
            throw new RuntimeException("Неверный формат аргумента(ов): " + e.getMessage());
        }
        //Console.chenges.add(new LinkedList<>(List.of("add "+String.join(" ",args),"remove_first")));
        return "Успешно!";
    }

    @Override
    public String toString() {
        return this.textCommand;
    }

}
