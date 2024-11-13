package levy.brickbreaker;

import java.awt.geom.Rectangle2D;

public class Brick extends Rectangle2D.Double {
    private boolean destroyed;

    public Brick(double x, double y, double width, double height) {

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


