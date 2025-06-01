package commands;

import exceptions.NotAnIntegerException;
import fileOptions.LoadCommand;
import entities.MusicBand;
import fileOptions.SaveCommand;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Console implements Host {
    public static LinkedList<MusicBand> entities = new LinkedList<>();
    public static final LinkedList<Map<String, Command>> history = new LinkedList<>();
    public static LocalDate initDate;
    public static LocalTime initTime;


    static {
        CommandRegister.register("add", (args) -> new AddCommand(args, 8, """
                Имя [Str] +
                          Координата x [Int] +
                          Координата y [Flt] +
                          Количество участников [Int] +
                          Количество альбомов [Int] +
                          Жанр [Enum] +
                          Лейбл [Int]
                          -> Создание нового элемента"""));
        CommandRegister.register("add_if_max", (args) -> new AddIfMaxCommand(args, 8, """
                Параметр сравнения [Str] +
                                 Имя [Str] +
                                 Координата x [Int] +
                                 Координата y [Flt] +
                                 Количество участников [Int] +
                                 Количество альбомов [Int] +
                                 Жанр [Enum] +
                                 Лейбл [Str]
                                 -> Создание нового элемента если его поле меньше чем любое из существующих"""));
        CommandRegister.register("info", (args) -> new InfoCommand(args, 0, "Текущее количество объектов в коллекции + время инициализации сессии"));
        CommandRegister.register("show", (args) -> new ShowCommand(args, 0, "Вывод текущих элементов коллекции"));
        CommandRegister.register("remove", (args) -> new RemoveCommand(args, 1, "ID [Int] -> Удаление объекта с присвоенным индексом равным введенному"));
        CommandRegister.register("remove_lower", (args) -> new RemoveLower(args, 1, "ID [Int] -> Попытка удаления каждого элемента, присвоенный id которого меньше указанного"));
        CommandRegister.register("save", (args) -> new SaveCommand(args, 0, "Сохранение текущего состояния коллекции в CSV файл"));
        CommandRegister.register("clear", (args) -> new ClearCommand(args, 0, "Очистка коллекции"));
        CommandRegister.register("update_id", (args) -> new UpdateByIDCommand(args, 3, "ID [Int] " +
                "- Поле [Str] " +
                "- Новое значение [Var] -> Установить значение поля объекта коллекции с указанным id на заданное"));
        CommandRegister.register("print_descending", (args) -> new PrintDescendingCommand(args, 0, "Вывод элементов коллекции в порядке убывания"));
        CommandRegister.register("print_field_ascending_genre", (args) -> new PrintFieldAscendingGenreCommand(args, 0, "Вывод id и жанра всех элементов, отсортированных по жанру"));
        CommandRegister.register("print_field_descending_label", (args) -> new PrintFieldDescendingLabelCommand(args, 0, "Вывод значений поля label (bands) всех элементов в порядке убывания"));
        CommandRegister.register("help", (args) -> new HelpCommand(args, 0, "Вывод справки по всем командам"));
        CommandRegister.register("history", (args) -> new HistoryCommand(args, 0, "Вывод последних 7 введенных команд, исключая саму историю"));
    }

    public Console(String filename) {
        LoadCommand.load(filename);
        initDate = LocalDate.now();
        initTime = LocalTime.now();
    }

    @Override
    public String command(String command) {
        String commandName = command.split(" ")[0].toLowerCase();
        Command commandObject;
        try {
            commandObject = CommandRegister.commands.get(commandName).create(Arrays.copyOfRange(command.split(" "), 1, command.split(" ").length));
        } catch (NullPointerException e) {
            return "Команда " + commandName.toLowerCase() + " не найдена";
        }
        try {
            String answer = commandObject.execute();
            if (!(commandName.equalsIgnoreCase("history"))) {
                history.add(Map.of(commandName, commandObject));
            }
            entities.sort(MusicBand::compareTo);
            return answer;
        } catch (NoClassDefFoundError e) {
            return "Объект с указанным Id не существует";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Неверное количество аргументов для команды " + commandName + ": " + (command.split(" ").length - 1) + "/";
        } catch (NumberFormatException e) {
            return "Переданный аргумент " + e.getLocalizedMessage().split(": ")[1] + " не может быть интерпретирован в этой позиции";
        } catch (NotAnIntegerException e) {
            return e.getLocalizedMessage();
        } catch (NoSuchElementException e) {
            return "В коллекции недостаточно элементов(" + entities.toArray().length + ") для выполнения команды " + commandName;
        } catch (RuntimeException e) {
            return "В ходе обработки возникла неинтерпретируемая ошибка: " + e.getMessage();
        }
    }
}