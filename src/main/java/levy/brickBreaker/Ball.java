package levy.brickBreaker;

import java.awt.geom.Ellipse2D;

public class Ball extends Ellipse2D.Double {
    private double diameter;
    private double directionDegrees;
    private double speed;

    public Ball(double x, double y, double height,double width, double diameter, double speed, double directionDegrees) {
        super(x, y, width, height);
        this.diameter = diameter;
        this.speed = speed;
        this.directionDegrees = directionDegrees;
    }

    public double getDirectionDegrees() { return directionDegrees; }
    public double getSpeed() { return speed; }
   // public void setSpeed(double speed) { this.speed = speed; }
   /*public void resetPosition() {
        x = 400;
        y = 500;
       directionDegrees = 45;
    }

    */

    public double getDiameter() {
        return diameter;
    }

   /* public void setDiameter(double diameter) {
        this.diameter = diameter;
    }
    */

    public void setDirectionDegrees(double directionDegrees) {
        this.directionDegrees = directionDegrees;
    }

}