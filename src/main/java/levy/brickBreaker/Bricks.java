package levy.brickBreaker;

import java.awt.geom.Rectangle2D;

public class Bricks extends Rectangle2D.Double {
    private boolean destroyed;

    public Bricks(double x, double y, double width, double height) {
        super(x,y,width, height);
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPosition(double x, double y) {
        setRect(x, y, this.width, this.height);
    }
}


