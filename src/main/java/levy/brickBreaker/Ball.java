package levy.brickBreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private double x;
    private double y;
    private double diameter;
    private double directionDegrees;
    private double speed;

    public Ball(double x, double y, double diameter, double speed, double directionDegrees) {
        super(x, y, diameter, diameter);
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.speed = speed;
        this.directionDegrees = directionDegrees;
    }

    // Getters and Setters
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public double getDiameter() { return diameter; }
    public void setDiameter(double diameter) { this.diameter = diameter; }
    public double getDirectionDegrees() { return directionDegrees; }
    public void setDirectionDegrees(double directionDegrees) { this.directionDegrees = directionDegrees; }
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public void resetPosition() {
        x = 400;  // Reset to center
        y = 500;
        directionDegrees = 45; // Reset direction
    }
}