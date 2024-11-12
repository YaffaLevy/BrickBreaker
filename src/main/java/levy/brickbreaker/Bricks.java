package levy.brickbreaker;

import java.awt.geom.Rectangle2D;

public class Bricks extends Rectangle2D.Double {
    private boolean destroyed;

    public Bricks(double x, double y, double width, double height) {

        super(x, y, width, height);
        this.destroyed = false;
    }

    public boolean isDestroyed() {

        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {

        this.destroyed = destroyed;
    }

}


