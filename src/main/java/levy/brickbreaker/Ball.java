package levy.brickbreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private double diameter;
    private double directionDegrees;
    private double speed;

    public Ball(double x, double y, double height, double width, double diameter, double speed, double directionDegree) {

        super(x, y, width, height);
        this.diameter = diameter;
        this.speed = speed;
        this.directionDegrees = directionDegree;

    }

    public double getDirectionDegrees() {
        return directionDegrees;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDiameter() {

        return diameter;
    }

    public void setDirectionDegrees(double directionDegrees) {

        this.directionDegrees = directionDegrees;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

}