package commands;

import entities.MusicBand;
import entities.MusicGenre;

import java.util.Comparator;

public class PrintDescendingCommand implements Command {
    String[] args;
    String textCommand;

    public PrintDescendingCommand(String[] Args, Integer argsCount, String textCommand) {
        //super(command, argsCount,undo);
        check(argsCount, Args);
        this.args = Args;
        this.textCommand = textCommand;
    }

    @Override
    public String execute() throws NumberFormatException {
        String ans = "";
        for (MusicBand band : Console.entities.stream().sorted(Comparator.comparing(MusicBand::getId).reversed()).toList()) {
            ans += band.toString() + "\n";
        }
        ;
        return ans;
    }

    @Override
    public String toString() {
        return this.textCommand;
    }
}

