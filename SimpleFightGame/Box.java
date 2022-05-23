import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Box extends Rectangle{

    private Enums.BoxType type;
    private double offsetX;
    private double offsetY;
    private double dirOffsetX;

    public Box(double dirOffsetX, double offsetX, double offsetY, double width, double height, Enums.BoxType type) {
        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
        setWidth(width);
        setHeight(height);
        this.type = type;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.dirOffsetX = dirOffsetX;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getDirOffsetX() {
        return dirOffsetX;
    }

    public Enums.BoxType getType() {
        return type;
    }
}
