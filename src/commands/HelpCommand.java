package commands;

public class HelpCommand implements Command {
    String[] args;
    String textCommand;

    public HelpCommand(String[] Args, Integer argsCount, String textCommand) {
        check(argsCount, Args);
        this.textCommand = textCommand;
        this.args = Args;
    }

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder("Список доступных команд:\n");

        int index = 1;
        for (String key : CommandRegister.commands.keySet()) {
            try {
                Command cmd = CommandRegister.commands.get(key).create(new String[0]);
                result.append(index++).append(") ").append(key).append(" -> ").append(cmd.toString()).append("\n");
            } catch (Exception e) {
                result.append(index++).append(") ").append(key).append(" -> ошибка при создании команды\n");
            }
        }

        result.append(index++).append(") exit -> Завершение текущей сессии терминала\n").append(index++).append(") execute_script [String] script_name -> Выполнение скрипта по указанному пути.");

        return result.toString();
    }

    @Override
    public String toString() {
        return textCommand;
    }
}
