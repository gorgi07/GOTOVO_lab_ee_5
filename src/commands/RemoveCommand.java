package commands;

import entities.MusicBand;

public class RemoveCommand implements Command{
    //private LinkedList<String> history = new LinkedList<>();
    String[] args;
    String textCommand;
    int argsCount;
    public RemoveCommand(String[] Args,Integer argsCount,String textCommand) {
        //super(command, argsCount,undo);

        this.textCommand=textCommand;
        this.args=Args;
        this.argsCount= argsCount;
    }
    @Override
    public String execute(){
        check(argsCount,args);
        MusicBand band;

            band = GetID.getID((args[0]));
            //super.updateHistory(band.getGrandcommand());
            Console.entities.remove(band);
            return "Объект с id "+args[0]+" успешно удален";

    }

    @Override
    public String toString(){
        return this.textCommand;
    }
}
