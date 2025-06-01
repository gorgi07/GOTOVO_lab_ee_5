package exceptions;

public class QuitCycleException extends Exception {
    public QuitCycleException() {
        super("Ввод прерван пользователем (Ctrl+D)");
    }
}
