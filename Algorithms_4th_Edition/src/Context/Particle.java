package Context;

import libraries.StdDraw;
import libraries.StdOut;
import libraries.StdRandom;

import java.awt.Color;

public class Particle {
    private static final double INFINITY = Double.POSITIVE_INFINITY;

    private double x, y;
    private double vx, vy;
    private final double radius;
    private final double mass;
    private final Color color;
    private int count; // number of collisions so far

    public Particle(double x, double y, double vx, double vy, double radius, double mass, Color color) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.color = color;
    }

    public Particle() {
        x = StdRandom.uniform(0.0, 1.0);
        y = StdRandom.uniform(0.0, 1.0);
        vx = StdRandom.uniform(-0.005, 0.005);
        vy = StdRandom.uniform(-0.005, 0.005);
        radius = 0.02;
        mass = 0.5;
        color = Color.BLACK;
    }

    public void move(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x, y, radius);
    }

    public int count() {
        return count;
    }

    public double timeToHit(Particle that) {
        if (this == that) return INFINITY;
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        // calculate the time as a root of a quadratic equation of one unknown
        double b = 2 * (dx * dvx + dy * dvy);
        if (b > 0) return INFINITY;
        double a = dvx * dvx + dvy * dvy;
        if (a == 0) return INFINITY;
        double sigma = this.radius + that.radius;
        double c = dx * dx + dy * dy - sigma * sigma;
        double d = (b * b) - 4 * a * c;
        if (c < 0) StdOut.println("overlapping particles");
        if (d < 0) return INFINITY;
        return -(b + Math.sqrt(d)) / (2 * a);
    }

    public double timeToHitVerticalWall() {
        if (vx > 0) return (1.0 - x - radius) / vx;
        else if (vx < 0) return (radius - x) / vx;
        else return INFINITY;
    }

    public double timeToHitHorizontalWall() {
        if (vy > 0) return (1.0 - y - radius) / vy;
        else if (vy < 0) return (radius - y) / vy;
        else return INFINITY;
    }

    /**
     * Updates the velocities of this particle and the specified particle according to the laws of elastic collision.
     * Assumes that the particles are colliding at this instant.
     */
    public void bounceOff(Particle that) {
        double dx = that.x - this.x;
        double dy = that.y - this.y;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx * dvx + dy * dvy;
        double dist = this.radius + that.radius;

        // magnitude of normal force
        double magnitude = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);

        // normal force, and in x and y directions
        double fx = magnitude * dx / dist;
        double fy = magnitude * dy / dist;

        // update velocities according to normal force
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;

        // update collision counts
        this.count++;
        that.count++;
    }

    public void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }

    public void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }

    public double kineticEnergy() {
        return 0.5 * mass * (vx * vx + vy * vy);
    }
}
