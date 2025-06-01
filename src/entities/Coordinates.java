package entities;

public class Coordinates {
    private float x;
    private Double y;

    public Coordinates(float x, double y) {
        setX(x);
        setY(y);
    }

    private void setX(float x) {
        if (x <= 859) {
            this.x = x;
        } else {
            throw new RuntimeException("Поле х не может быть больше 859");
        }
    }

    private void setY(Double y) {
        this.y = y;
    }

    public float getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }
}
