import commands.*;
import commands.Console;
import exceptions.QuitCycleException;
import fileOptions.*;

public class Main {
    public static void main(String[] args) throws QuitCycleException {
        ProtectedScanner sc = new ProtectedScanner();
        System.out.print("Введите имя загружаемого файла коллекции: ");
        Host console = new Console(sc.nextLine());
        InputManager manager = new InputManager(sc);
        System.out.println("Лабораторная работа №5\nВариант 3845\nhelp для вывода команд\nexit для выхода");
        while (true) {
            sc.setFalse();
            System.out.print(">");
            try {
                for (String command : manager.proceedCommand("").split("\n")) {
                    System.out.println(console.command(command));
                }
            } catch (RuntimeException e) {
                System.out.println("Ошибка " + e.getMessage());
            } catch (QuitCycleException e) {
                System.out.println(e.getMessage());
                sc = new ProtectedScanner();
                manager = new InputManager(sc);
            }

        }
    }

}