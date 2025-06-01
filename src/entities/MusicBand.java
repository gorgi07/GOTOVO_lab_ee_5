package entities;

import commands.Console;

import java.time.LocalDate;
import java.util.List;

public class MusicBand implements Comparable<MusicBand> {
    //private final LinkedList<Object> Spisochek;
    //private String grandcommand;
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int numberOfParticipants; //Значение поля должно быть больше 0
    private Long singlesCount; //Поле может быть null, Значение поля должно быть больше 0
    private String description; //Поле может быть null
    private MusicGenre genre; //Поле не может быть null
    private Label label; //Поле не может быть null

    public MusicBand(String name, Coordinates coordinates, int numberOfParticipants, Long singlesCount, String description, MusicGenre genre, Label label) {
        this.id = generateId(); // Генерация ID
        this.creationDate = java.time.LocalDate.now();
        //this.grandcommand=grandcommand;// Автоматическая генерация даты
        setName(name);
        setCoordinates(coordinates);
        setNumberOfParticipants(numberOfParticipants);
        setSinglesCount(singlesCount);
        setDescription(description);
        setGenre(genre);
        setStudio(label);
    }

    public MusicBand(MusicBand band) {
        this.id = band.getId(); // Генерация ID
        this.creationDate = band.creationDate; // Автоматическая генерация даты
        this.name = band.getName();
        this.coordinates = band.getCoordinates();
        this.numberOfParticipants = band.numberOfParticipants;
        this.singlesCount = band.singlesCount;
        this.description = band.getDescription();
        this.genre = band.getGenre();
        this.label = band.getLabel();
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Название не может быть пустым или null");
        }
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null) {
            this.coordinates = coordinates;
        } else {
            throw new IllegalArgumentException("Координаты не могут быть null");
        }
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        if (numberOfParticipants > 0) {
            this.numberOfParticipants = numberOfParticipants;
        } else {
            throw new IllegalArgumentException("Количество участников должно быть больше 0");
        }
    }

    public void setSinglesCount(Long singlesCount) {
        if (singlesCount == null || singlesCount > 0) {
            this.singlesCount = singlesCount;
        } else {
            throw new IllegalArgumentException("Количество альбомов должно быть больше 0 или null");
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(MusicGenre genre) {
        if (genre != null) {
            this.genre = genre;
        } else {
            throw new IllegalArgumentException("Жанр не может быть null");
        }
    }

    public void setStudio(Label label) {
        if (label != null) {
            this.label = label;
        } else {
            throw new IllegalArgumentException("Студия не может быть null");
        }
    }

    public void setCreationDate(String date) {
        this.creationDate = LocalDate.parse(date);
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getParticipants() {
        return this.numberOfParticipants;
    }

    public Long getSingles() {
        return this.singlesCount;
    }

    public String getDescription() {
        return this.description;
    }

    public MusicGenre getGenre() {
        return this.genre;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public String getGrandcommand() {
        return String.join(" ", List.of(name, "" + coordinates.getX(), "" + coordinates.getY(), numberOfParticipants + "", singlesCount + "", description + "", genre + "", label.getBands().toString(), creationDate + ""));
    }

    // Метод для генерации уникального ID (можно заменить на что-то более сложное)
    private static int idCounter = 1;

    private Long generateId() {
        if (!Console.entities.isEmpty()) {
            return Console.entities.stream().sorted().toList().get(Console.entities.size() - 1).getId() + 1;
        }
        return (long) idCounter++;
    }

    public LocalDate getCreationDate() {
        return this.creationDate;
    }

    public Label getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        StringBuilder json = new StringBuilder();
        json.append("{")
                .append("\"id\":").append(id).append(",")
                .append("\"name\":\"").append(name).append("\",")
                .append("\"coordinates\":{")
                .append("\"x\":").append(coordinates.getX()).append(",")
                .append("\"y\":").append(coordinates.getY())
                .append("},")
                .append("\"creationDate\":\"").append(creationDate.toString()).append("\",")
                .append("\"numberOfParticipants\":").append(numberOfParticipants).append(",");


        json.append("\"albumsCount\":").append(singlesCount).append(",");


        json.append("\"description\":\"").append(description).append("\",");


        json.append("\"genre\":\"").append(genre.name()).append("\",")
                .append("\"label\":{")
                .append("\"bands\":").append(label.getBands())
                .append("},")
                //.append("\"grandCommand\":\"").append(this.getGrandcommand()).append("\"")
                .append("}");

        return json.toString();
    }


    @Override
    public int compareTo(MusicBand band) {
        return Long.compare(this.id, band.id);
    }

    public void setId(Long id) {
        this.id = id;
    }
}

