package fileOptions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;

import commands.Command;
import commands.Console;
import entities.MusicBand;

public class SaveCommand implements Command {
    String[] args;
    String textCommand;

    public SaveCommand(String[] Args, Integer argsCount, String textCommand) {
        check(argsCount, Args);
        this.textCommand = textCommand;
        this.args = Args;
    }

    @Override
    public String execute() {
        String fileName = "entities.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Заголовки CSV
            writer.write("id,name,coordinates_x,coordinates_y,creationDate,numberOfParticipants,albumsCount,description,genre,label_bands\n");

            for (MusicBand band : Console.entities) {
                StringJoiner sj = new StringJoiner(",");

                sj.add(String.valueOf(band.getId()));
                sj.add(escapeCsv(band.getName()));
                sj.add(String.valueOf(band.getCoordinates().getX()));
                sj.add(String.valueOf(band.getCoordinates().getY()));
                sj.add(String.valueOf(band.getCreationDate()));
                sj.add(String.valueOf(band.getParticipants()));
                sj.add(String.valueOf(band.getSingles()));
                sj.add(escapeCsv(band.getDescription()));
                sj.add(String.valueOf(band.getGenre()));
                sj.add(escapeCsv(band.getLabel() != null ? band.getLabel().getBands().toString() : ""));

                writer.write(sj.toString());
                writer.newLine();
            }
        } catch (RuntimeException | IOException e) {
            return "Ошибка при сохранении в файл: " + e.getMessage();
        }

        return "Коллекция успешно сохранена в файл " + fileName;
    }

    private String escapeCsv(String field) {
        if (field == null) return null;
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    @Override
    public String toString() {
        return this.textCommand + " - Запись текущей коллекции в файл CSV";
    }
}
