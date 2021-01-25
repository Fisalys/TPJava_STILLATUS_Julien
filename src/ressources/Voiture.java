package ressources;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Voiture extends Rectangle
{
    private int speed;
    private double startX;
    private double startY;
    private Circle detection;
    private Circle accident;

    public Voiture(double x, double y)
    {
        super(10, 10, Color.BLUE);
        speed = getSPEED();
        startX = x;
        startY = y;
        super.setX(x);
        super.setY(y);
        detection = new Circle();
        detection.setCenterX(x);
        detection.setCenterY(y);
        detection.setRadius(30.0);
        detection.setFill(Color.TRANSPARENT);
        accident = new Circle();
        accident.setCenterX(x);
        accident.setCenterY(y);
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public int getSpeed() {
        return speed;
    }

    private static int getSPEED()
    {
        return (int)(Math.random()*5+1);
    }

    public Circle getDetection() {
        return detection;
    }

    public Circle getAccident() {
        return accident;
    }
}
