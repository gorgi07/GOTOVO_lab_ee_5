package commands;

public class InfoCommand implements Command {
    String[] args;
    String textCommand;

    public InfoCommand(String[] Args, Integer argsCount, String textCommand) {
        //super(command, argsCount,undo);
        check(argsCount, Args);
        this.textCommand = textCommand;
        this.args = Args;
    }

    @Override
    public String execute() {
        long count = Console.entities.size();
        return String.format("Количество объектов в коллекции: " + count + "\nВремя инициализации сессии: " + Console.initDate + " в " + Console.initTime + "\nТип коллекции: " + Console.entities.getClass().getSimpleName());
    }

    //{register();}
    @Override
    public String toString() {
        return this.textCommand;
    }
}
