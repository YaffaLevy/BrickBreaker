package levy.brickbreaker;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Brick extends Rectangle2D.Double {
    private boolean destroyed;
    private Color color;

    public Brick(double x, double y, double width, double height) {

        super(x, y, width, height);
    }

    public boolean isDestroyed() {

        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {

        this.destroyed = destroyed;
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}


