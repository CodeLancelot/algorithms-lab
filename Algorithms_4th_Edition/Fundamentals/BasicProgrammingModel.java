package Fundamentals;

import java.awt.Point;
import libraries.*;

public class BasicProgrammingModel {
    public static void main(String[] args) {
        //Use Polar Coordinate System
        //The coordinate of the polar point is (0, 0)
        int n = 10, scale = 100;//Integer.parseInt(args[0])
        final int POLAR_RADIUS = 75;
        final double RAD = 2 * Math.PI;
        double probability = 0.5;

        StdDraw.setXscale(-scale, scale);
        StdDraw.setYscale(-scale, scale);
        StdDraw.setPenRadius(.01);


        //The coordinate of the first point is (POLAR_RADIUS, 0)
        //x is replaced with rcosθ，y is replaced with rsinθ, r^2=(x^2+y^2)
        double polarAngle = 0;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            polarAngle = (double) i / n * RAD;
            double x = POLAR_RADIUS * Math.cos(polarAngle);
            double y = POLAR_RADIUS * Math.sin(polarAngle);
            points[i] = new Point();
            points[i].setLocation(x, y);
            StdDraw.point(x, y);
        }

        for (int i = 1; i < n; i++) {
            StdOut.printf("(%.2f, %.2f)\n", points[i].getX(), points[i].getY());
            StdDraw.line(points[0].getX(), points[0].getY() , points[i].getX(), points[i].getY());
        }

    }

    public static void randomConnections() {} {

    }
}