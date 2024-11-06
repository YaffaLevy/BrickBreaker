package levy.brickBreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double{

    private double diameter;
    private double directionDegrees;
    private double speed;

    public Ball(double x, double y, double diameter, double speed, double directionDegrees) {
        super(x,y,width,height);
        this.diameter = diameter;
        this.speed = speed;
        this.directionDegrees = directionDegrees;
    }


    public double getDiameter() { return diameter; }
    public double getDirectionDegrees() { return directionDegrees; }
    public double getSpeed() { return speed; }
}
