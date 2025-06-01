package fileOptions;

import commands.CommandRegister;
import commands.Console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class LoadCommand {

    public static void load(String filePath) {
        Set<Long> existingIds = new HashSet<>();
        for (var band : Console.entities) {
            existingIds.add(band.getId());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String header = reader.readLine(); // Пропускаем заголовок
            if (header == null) {
                System.out.println("Файл пуст.");
                return;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] fields = parseCsvLine(line);
                    if (fields.length < 10) {
                        System.err.println("Недостаточно данных: " + line);
                        continue;
                    }

                    Long id = Long.parseLong(fields[0]);
                    if (existingIds.contains(id)) {
                        System.err.println("Пропущен дублирующий id: " + id);
                        continue;
                    }

                    // Строим grandCommand строку, как будто пользователь ввёл её в консоли
                    // Формат должен соответствовать ожидаемому в add-команде
                    String[] grandCommandParts = new String[]{// id
                            fields[1],                           // name
                            fields[2],
                            fields[3],                // coordinates x, y// creationDate
                            fields[5],                           // numberOfParticipants
                            fields[6],                           // albumsCount
                            fields[7],                           // description
                            fields[8],                           // genre
                            fields[9],                           // label.name
                            fields[4],
                            fields[0]// label.studio
                    };

                    System.out.println(CommandRegister.commands.get("add").create(grandCommandParts).execute());
                    existingIds.add(id);

                    System.out.println("Загружено: " + fields[1]);

                } catch (Exception e) {
                    System.err.println("Ошибка в строке: " + line);
                    System.err.println("Причина: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private static String[] parseCsvLine(String line) {
        // CSV парсер с учётом кавычек (если есть)
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }
}
