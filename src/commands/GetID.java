package commands;

import exceptions.NotAnIntegerException;
import entities.MusicBand;

import java.util.Objects;

public class GetID {
    public static MusicBand getID(String id) throws NoClassDefFoundError {
        for (MusicBand band : Console.entities) {
            try {
                if (band.getId() == Long.parseLong(id)) {
                    return band;
                }
            } catch (RuntimeException e) {
                throw new NotAnIntegerException("Переданное значение " + id + " не может быть интерпретированно как индекс!");
            }
        }
        throw new NoClassDefFoundError();
    }
}
