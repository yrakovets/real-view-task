package com.yrakovets.realview.structures;

public class Segment2D {
    Dot2D start;
    Dot2D end;

    public Segment2D(Dot2D start, Dot2D end) {
        this.start = start;
        this.end = end;
    }

    public Dot2D getStart() {
        return start;
    }

    public Dot2D getEnd() {
        return end;
    }
}
