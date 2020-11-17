package Context;

import Sorting.MinPQ;
import libraries.StdDraw;
import libraries.StdIn;

import java.awt.Color;

// Event-driven simulation of the motion of a system of moving particles that behave according to the laws of elastic collision
public class CollisionSystem {
    private static final double HZ = 0.5; // number of redraw events per clock tick
    private MinPQ<Event> pq;
    private double time = 0.0;
    private Particle[] particles;

    private static class Event implements Comparable<Event> {
        private final double time;         // time that event is scheduled to occur
        private final Particle a, b;       // particles involved in event, possibly null
        private final int countA, countB;  // collision counts at event creation

        // create a new event to occur at time t involving a and b
        public Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a = a;
            this.b = b;
            if (a != null) countA = a.count();
            else countA = -1;
            if (b != null) countB = b.count();
            else countB = -1;
        }

        // compare times when two events will occur
        public int compareTo(Event that) {
            return Double.compare(this.time, that.time);
        }

        // has any collision occurred between when event was created and now?
        public boolean isValid() {
            boolean flagA = a != null && a.count() != countA;
            boolean flagB = b != null && b.count() != countB;
            return !(flagA || flagB);
        }
    }

    public CollisionSystem(Particle[] particles) {
        this.particles = particles.clone();
    }

    // redraw all particles
    private void redraw(double limit) {
        StdDraw.clear();
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show();
        StdDraw.pause(20);
        if (time < limit) {
            pq.insert(new Event(time + 1.0 / HZ, null, null));
        }
    }

    private void predictCollisions(Particle a, double limit) {
        if (a == null) return;
        for (Particle b : particles) {
            double dt = a.timeToHit(b);
            if (time + dt < limit) pq.insert(new Event(time + dt, a, b));
        }
        double dtx = a.timeToHitVerticalWall();
        if (time + dtx < limit) pq.insert(new Event(time + dtx, a, null));
        double dty = a.timeToHitHorizontalWall();
        if (time + dty < limit) pq.insert(new Event(time + dty, null, a));
    }

    public void simulate(double limit) {
        pq = new MinPQ<>();
        for (Particle p : particles) {
            predictCollisions(p, limit);
        }
        pq.insert(new Event(0, null, null));

        while (!pq.isEmpty()) {
            Event event = pq.delMin();
            if (!event.isValid()) continue;
            for (Particle p : particles)
                p.move(event.time - time);
            time = event.time;
            Particle a = event.a;
            Particle b = event.b;
            if (a != null && b != null) a.bounceOff(b);
            else if (a != null && b == null) a.bounceOffVerticalWall();
            else if (a == null && b != null) b.bounceOffHorizontalWall();
            else redraw(limit);
            predictCollisions(a, limit);
            predictCollisions(b, limit);
        }
    }

    public static void main(String[] args) {
        StdDraw.setCanvasSize(600, 600);

        // enable double buffering
        StdDraw.enableDoubleBuffering();

        // the array of particles
        Particle[] particles;

        // create n random particles
        if (args.length == 1) {
            int n = Integer.parseInt(args[0]);
            particles = new Particle[n];
            for (int i = 0; i < n; i++)
                particles[i] = new Particle();
        }
        // or read from standard input
        else {
            int n = StdIn.readInt();
            particles = new Particle[n];
            for (int i = 0; i < n; i++) {
                double rx = StdIn.readDouble();
                double ry = StdIn.readDouble();
                double vx = StdIn.readDouble();
                double vy = StdIn.readDouble();
                double radius = StdIn.readDouble();
                double mass = StdIn.readDouble();
                int r = StdIn.readInt();
                int g = StdIn.readInt();
                int b = StdIn.readInt();
                Color color = new Color(r, g, b);
                particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
            }
        }

        // create collision system and simulate
        CollisionSystem system = new CollisionSystem(particles);
        system.simulate(10000);
    }
}
