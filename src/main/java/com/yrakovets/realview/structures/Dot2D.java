package com.yrakovets.realview.structures;

import java.util.Objects;

public record Dot2D(double x, double y) {
    private final static int reversePrecision = 1000;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dot2D dot2D = (Dot2D) o;
        return toMultipliedLong(x) == toMultipliedLong(dot2D.x)
                && toMultipliedLong(y) == toMultipliedLong(dot2D.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toMultipliedLong(x), toMultipliedLong(y));
    }

    private long toMultipliedLong(double coordinate) {
        return (long) (coordinate * reversePrecision);
    }
}
