package commands;

public interface Command {
    public default String execute() {
        return "200";
    }

    public default void check(int argscount, String[] args) throws RuntimeException {
        if (args.length < argscount) {
            throw new RuntimeException("Количество аргументов не соответствует заданному: " + argscount);
        }
    }
}
