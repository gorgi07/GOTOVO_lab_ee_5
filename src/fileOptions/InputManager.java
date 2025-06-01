package fileOptions;

import commands.Command;
import entities.MusicGenre;
import exceptions.QuitCycleException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class InputManager {
    Map<String, Command> commands = new HashMap<>();
    ProtectedScanner sc;
    List<String> fields = List.of("albums", "participants", "cordx", "cordy", "label");

    public InputManager(ProtectedScanner sc) {
        this.sc = sc;
        //this.commands=console.getCommands();
    }

    String getName() throws QuitCycleException {
        String name = "";
        while (true) {
            System.out.print("    Имя группы: ");
            name = sc.nextLine();
            if (Objects.equals(name, "")) {
                System.out.println("Значение поля name не может быть null");
            } else {
                break;
            }
        }
        return name.replace(" ", "_");
    }

    Integer getX() throws QuitCycleException {
        String X;
        while (true) {
            System.out.print("    Координата х: ");
            X = sc.nextLine();
            try {
                if (Integer.parseInt(X) < 860) {
                    break;
                } else {
                    System.out.println("Значение этого поля не может превышать 859");
                }
            } catch (RuntimeException e) {
                System.out.println("Значение этого поля должно быть целым числом");
            }
        }
        return Integer.parseInt(X);
    }

    Float getY() throws QuitCycleException {
        String Y;
        while (true) {
            System.out.print("    Координата y: ");
            Y = sc.nextLine();
            try {
                Double.parseDouble(Y);
                break;
            } catch (RuntimeException e) {
                System.out.println("Значение этого поля должно быть числом");
            }
        }
        return Float.parseFloat(Y);
    }

    private Integer getParticipants() throws QuitCycleException {
        String participants;
        while (true) {
            System.out.print("    Количество участников: ");
            participants = sc.nextLine();
            try {
                if (Integer.parseInt(participants) > 0) {
                    break;
                } else {
                    System.out.println("Значение этого поля должно быть натуральным числом");
                }
            } catch (RuntimeException e) {
                System.out.println("Значение этого поля должно быть натуральным числом");
            }
        }
        return Integer.parseInt(participants);
    }

    private Integer getAlbums() throws QuitCycleException {
        String albums;
        while (true) {
            System.out.print("    Количество синглов: ");
            albums = sc.nextLine();
            if (Objects.equals(albums, "")) {
                System.out.println("    null");
                return null;
            }
            try {
                if (Integer.parseInt(albums) > 0) {
                    break;
                } else {
                    System.out.println("Значение этого поля должно быть натуральным числом");
                }
            } catch (RuntimeException e) {
                System.out.println("Значение этого поля должно быть натуральным числом");
            }
        }
        return Integer.parseInt(albums);
    }

    private String getDescription() throws QuitCycleException {
        String description;
        while (true) {
            System.out.print("    Описание: ");
            description = sc.nextLine();
            if (Objects.equals(description, "")) {
                System.out.println("    null");
                return null;
            }
            return description;
        }
    }

    private MusicGenre getGenre() throws QuitCycleException {
        String needGenre = "| ";
        for (MusicGenre c : MusicGenre.values()) {
            needGenre += c + " | ";
        }
        while (true) {
            System.out.print("    Жанр: " + needGenre);
            try {
                return MusicGenre.valueOf(sc.nextLine().toUpperCase());
            } catch (RuntimeException | QuitCycleException e) {
                System.out.println("Значение этого поля должно быть одним из " + needGenre);
            }
        }
    }

    private Integer getBands() throws QuitCycleException {
        String bands = "";
        while (true) {
            try {
                System.out.print("    Лейбл: ");
                bands = sc.nextLine();
                if (Objects.equals(bands, "")) {
                    System.out.println("Значение поля name не может быть null");
                } else {
                    Integer.parseInt(bands);
                    break;
                }
            } catch (RuntimeException | QuitCycleException e) {
                System.out.println("Введено не число");
            }
        }
        return Integer.parseInt(bands);
    }

    private String getParam() throws QuitCycleException {
        String param;
        while (true) {
            try {
                System.out.print("    Параметр сравнения: ");
                param = sc.nextLine().toLowerCase();
                if (fields.contains(param)) {
                    break;
                } else {
                    System.out.println("Поддерживаются имена полей: " + fields);
                }
            } catch (RuntimeException | QuitCycleException ignore) {

            }
        }
        return param;
    }

    private String adder() throws QuitCycleException {
        try {
            StringBuilder command = new StringBuilder();
            command.append(" ").append(getName());
            command.append(" ").append(getX());
            command.append(" ").append(getY());
            command.append(" ").append(getParticipants());
            command.append(" ").append(getAlbums());
            command.append(" ").append(getDescription());
            command.append(" ").append(getGenre());
            command.append(" ").append(getBands());
            return String.join(" ", command);
        } catch (RuntimeException e) {
            return "null";
        }
    }

    public String proceedCommand(String commands) throws QuitCycleException {
        String input = "";
        System.out.print(commands + "> ");
        try {
            input = sc.nextLine();
        } catch (Exception ignored) {
        }
        switch (input.split(" ")[0].toLowerCase()) {
            case "add" -> {
                sc.setTrue();
                System.out.println("Создаем новую группу");
                //System.out.println("Имя: ");

                return "add" + adder();
            }
            case "add_if_max" -> {
                sc.setTrue();
                System.out.println("Группа будет создана если значение указанного параметра окажется наибольшим из всех существующих");
                System.out.println("Поддерживаются имена полей: " + fields);
                return "add_if_max " + getParam() + adder();
            }
            case "exit" -> {
                System.exit(-1);
            }
            case "execute_script" -> {
                try {
                    return ProtectedReader.readFile(input.split(" ")[1], new HashSet<>());
                } catch (FileNotFoundException e) {
                    System.out.print("Файл не найден");
                }
            }
        }
        return input;
    }

}

class ProtectedReader {
    //static HashSet<String> hash = new HashSet<>();
    public static String readFile(String filePath, HashSet<String> recursion) throws FileNotFoundException {
        String line;
        int linesToSupply = 0;
        StringBuilder script = new StringBuilder();
        HashSet<String> rec = new HashSet<>(recursion);
        rec.add(filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // обработка вложенного exec
                if (line.startsWith("execute_script")) {
                    try {
                        String path = line.split(" ")[1];
                        if (!rec.contains(path)) {
                            script.append("\n").append(readFile(path, rec));
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Скрипт " + line.split(" ")[1] + " не доступен");
                    }
                    continue; // не включаем строку exec в финальный скрипт
                }

                if (!script.isEmpty() && linesToSupply == 0) {
                    script.append("\n");
                } else if (!script.isEmpty()) {
                    script.append(" ");
                }

                // определяем количество строк в "блоке"
                if (line.startsWith("add_if_max") && linesToSupply == 0) {
                    linesToSupply = 10;
                } else if (line.startsWith("add") && linesToSupply == 0) {
                    linesToSupply = 9;
                }

                script.append(line);
                if (linesToSupply > 0) {
                    linesToSupply--;
                }
            }

            return script.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}