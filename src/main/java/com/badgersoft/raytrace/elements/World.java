package com.badgersoft.raytrace.elements;

public class World {

    private Vector wind;
    private Vector gravity;

    public World() {
    }

    public World(Vector wind, Vector gravity) {
        this.wind = wind;
        this.gravity = gravity;
    }

    public Vector getWind() {
        return wind;
    }

    public void setWind(Vector wind) {
        this.wind = wind;
    }

    public Vector getGravity() {
        return gravity;
    }

    public void setGravity(Vector gravity) {
        this.gravity = gravity;
    }
}
