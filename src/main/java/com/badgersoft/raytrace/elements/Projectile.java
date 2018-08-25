package com.badgersoft.raytrace.elements;

public class Projectile {

    private Point point;
    private Vector velocity;

    public Projectile() {
    }

    public Projectile(Point point, Vector velocity) {
        this.point = point;
        this.velocity = velocity;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public static Projectile tick(World world, Projectile projectile) {
        final Point projectilePosition = projectile.getPoint().add(projectile.getVelocity());
        final Vector projectileVelocity = projectile.getVelocity().add(world.getGravity()).add(world.getWind());
        return new Projectile(projectilePosition, projectileVelocity);
    }
}
