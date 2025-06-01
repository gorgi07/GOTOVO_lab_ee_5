package entities;

public class Label {
    private Integer bands = null; //Поле может быть null

    public Label(Integer count) {
        this.bands = count;
    }

    public Integer getBands() {
        return this.bands;
    }
}
