package commands;

import entities.Coordinates;
import entities.MusicBand;
import entities.MusicGenre;
import entities.Label;

import java.util.*;

public class UpdateByIDCommand implements Command {
    HashMap<String, Chenger> objects;
    String[] args;
    String textCommand;
    int argsCount;

    public UpdateByIDCommand(String[] Args, Integer argsCount, String textCommand) {
        this.textCommand = textCommand;
        this.args = Args;
        this.argsCount = argsCount;
        HashMap<String, Chenger> objects = new HashMap<>();

        objects.put("name", new ChengeName());
        objects.put("coordinates", new ChengeCoordinates());
        objects.put("participants", new ChengeMembers());
        objects.put("singles", new ChengeSingles());
        objects.put("description", new ChengeDescription());
        objects.put("genre", new ChengeGenre());
        objects.put("label", new ChengeStudio());
        this.objects = objects;
    }

    @Override
    public String execute() {
        check(argsCount, args);
        MusicBand band;

        try {
            band = GetID.getID(args[0]);
        } catch (NumberFormatException e) {
            return "Введённая строка не является числовым id";
        }

        String field = args[1].toLowerCase();
        Chenger changer = objects.get(field);

        if (changer == null) {
            return "Объект с id " + args[0] + " не содержит поля с именем \"" + field + "\"";
        }

        changer.setValue(band, Arrays.copyOfRange(args, 2, args.length));


        return String.format("Поле \"%s\" объекта с id %s успешно изменено на %s",
                field, args[0], Arrays.toString(Arrays.copyOfRange(args, 2, args.length)));
    }

    @Override
    public String toString() {
        return this.textCommand;
    }

}

interface Chenger {
    default public String setValue(MusicBand band, Object[] value) {
        return "";
    }
}

class ChengeName implements Chenger {
    //Object value;
    //public ChengeName(){};
    @Override
    public String setValue(MusicBand band, Object[] value) {
        String prewious = band.getName();
        band.setName(value[0].toString());
        return "name " + prewious;
    }
}

class ChengeCoordinates implements Chenger {
    //Object value;
    @Override
    public String setValue(MusicBand band, Object[] value) {
        String previouse = band.getCoordinates().getX() + " " + band.getCoordinates().getY();
        band.setCoordinates(new Coordinates(Integer.parseInt(value[0].toString()), Float.parseFloat(value[1].toString())));
        return "coordinates " + previouse;
    }
}

class ChengeMembers implements Chenger {
    //Object value;

    @Override
    public String setValue(MusicBand band, Object[] value) {
        String previouse = band.getParticipants() + "";
        band.setNumberOfParticipants(Integer.parseInt(value[0].toString()));
        return "participants " + previouse;
    }
}

class ChengeSingles implements Chenger {
    //Object value;

    @Override
    public String setValue(MusicBand band, Object[] value) {
        Long previouse = band.getSingles();
        band.setSinglesCount(Long.parseLong(value[0].toString()));
        return "albums " + previouse;
    }
}

class ChengeDescription implements Chenger {
    //Object value;

    @Override
    public String setValue(MusicBand band, Object[] value) {
        String previouse = band.getDescription();
        band.setDescription(value[0].toString());
        return "description " + previouse;
    }
}

class ChengeGenre implements Chenger {
    //Object value;

    @Override
    public String setValue(MusicBand band, Object[] value) {
        MusicGenre previouse = band.getGenre();
        band.setGenre(MusicGenre.valueOf(value[0].toString()));
        return "genre " + previouse;
    }
}

class ChengeStudio implements Chenger {
    //Object value;

    @Override
    public String setValue(MusicBand band, Object[] value) {
        Label previouse = band.getLabel();
        band.setStudio(new Label(Integer.parseInt(value[0].toString())));
        return "studio " + previouse;
    }
}
