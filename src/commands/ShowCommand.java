package commands;

import entities.MusicBand;

public class ShowCommand implements Command {
    String[] args;
    String textCommand;

    public ShowCommand(String[] Args, Integer argsCount, String textCommand) {
        //super(command, argsCount,undo);
        check(argsCount, Args);
        this.textCommand = textCommand;
        this.args = Args;
    }

    @Override
    public String execute() {
        String entitiesValues = "Список доступных объектов: \n";
        for (MusicBand band : Console.entities) {
            entitiesValues += band.toString() + "\n";
        }
        return entitiesValues;
    }

    @Override
    public String toString() {
        return this.textCommand;
    }
}
