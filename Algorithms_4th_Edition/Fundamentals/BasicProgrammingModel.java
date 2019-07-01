package Fundamentals;

import java.awt.Point;
import libraries.*;

public class BasicProgrammingModel {
    public static void main(String[] args) {
        randomConnections(Integer.parseInt(args[0]), Double.parseDouble(args[1]));
    }

    public static void randomConnections(int n, double p) {
        //Use Polar Coordinate System
        //The coordinate of the polar point is (0, 0)
        int scale = 100;
        final int POLAR_RADIUS = 75;
        final double RAD = 2 * Math.PI;
        StdDraw.setXscale(-scale, scale);
        StdDraw.setYscale(-scale, scale);
        StdDraw.setPenRadius(.01);

        //The coordinate of the first point is (POLAR_RADIUS, 0)
        //x is replaced with rcos(theta)，y is replaced with rsin(theta), r^2=(x^2+y^2)
        double polarAngle = 0;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            polarAngle = (double) i / n * RAD;
            double x = POLAR_RADIUS * Math.cos(polarAngle);
            double y = POLAR_RADIUS * Math.sin(polarAngle);
            StdOut.printf("(%.2f, %.2f)\n", x, y);
            points[i] = new Point();
            points[i].setLocation(x, y);
            StdDraw.point(x, y);
        }

        drawLinesAmongPoints(points, p);
    }

    public static void drawLinesAmongPoints(Point[] points, double probability) {
        if (points.length  <= 1) {
            return;
        }

        int len = points.length;
        Point[] untreatedPoints = new Point[len-1];
        for (int i = 1; i < len; i++) {
            if (StdRandom.random() <= probability) {
                StdDraw.line(points[0].getX(), points[0].getY() , points[i].getX(), points[i].getY());
            }
            untreatedPoints[i-1] = points[i];
        }
        drawLinesAmongPoints(untreatedPoints, probability);
    }
}