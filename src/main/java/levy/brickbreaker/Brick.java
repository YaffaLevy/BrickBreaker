package levy.brickbreaker;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.Scanner;

public class Brick extends Rectangle2D.Double {
    private boolean destroyed;
    private Color color;
    private final static Color[] NEON_COLORS = {
            Color.MAGENTA,
            Color.CYAN,
            Color.GREEN,
            new Color(255, 165, 0),
            new Color(255, 255, 0),
            new Color(255, 105, 180),
            new Color(0, 255, 255),
            new Color(255, 0, 255),
            new Color(0, 255, 0),
            new Color(255, 20, 147)
    };

    public Brick(double x, double y, double width, double height) {

        super(x, y, width, height);
        Random random = new Random();
        this.color = NEON_COLORS[random.nextInt(NEON_COLORS.length)];
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


