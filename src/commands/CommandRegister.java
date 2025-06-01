package commands;

import java.util.*;

public class CommandRegister {
    public static List<String> lineInput = new ArrayList<>();
    //public static  LinkedList<MusicBand> entities = new LinkedList<>();
    public static Map<String, CommandFactory> commands = new HashMap<>();
    private static LinkedList<Runnable> classes = new LinkedList<>();

    @FunctionalInterface
    public interface CommandFactory {
        Command create(String[] args);
    }

    public static void register(String name, CommandFactory function) {
        //classes.add(function);
        commands.put(name, function);
    }

    //  public static void register(Registrable regclass,Boolean isLine){
    //    register(regclass);
    //   lineInput.add(regclass.getCommand());
    //}
    public static LinkedList<Runnable> getClasses() {
        return classes;
    }

}
