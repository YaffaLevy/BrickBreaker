package levy.brickBreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private double directionDegrees;
    private double speed;

    public Ball(double x, double y, double diameter, double speed, double directionDegrees) {
        super(x, y, diameter, diameter);
        this.speed = speed;
        this.directionDegrees = directionDegrees;
    }

    public double getDirectionDegrees() {
        return directionDegrees;
    }

    public void setDirectionDegrees(double directionDegrees) {
        this.directionDegrees = directionDegrees;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setPosition(double x, double y) {
        setFrame(x, y, width, height);
    }
}
