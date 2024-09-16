package com.yrakovets.realview;

import com.yrakovets.realview.structures.Dot2D;
import com.yrakovets.realview.structures.Shape2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

public class TheShapeFixerTest {
    @Test
    public void squareTest() {
        Shape2D shape = new Shape2D(new Dot2D[]{new Dot2D(0d, 0d), new Dot2D(1d, 0d), new Dot2D(1d, 1d),
                new Dot2D(0d, 1d), new Dot2D(0d, 0d)});
        Assertions.assertTrue(new TheShapeFixer().isValid(shape));
    }

    @Test
    public void crossedTest() {
        Shape2D shape = new Shape2D(new Dot2D[]{new Dot2D(0d, 0d), new Dot2D(1d, 1d), new Dot2D(0d, 1d),
                new Dot2D(1d, 0d), new Dot2D(0d, 0d)});
        Assertions.assertFalse(new TheShapeFixer().isValid(shape));
    }

    @Test
    public void notClosedTest() {
        Shape2D shape = new Shape2D(new Dot2D[]{new Dot2D(0d, 0d), new Dot2D(0d, 1d), new Dot2D(1d, 1d),
                new Dot2D(1d, 0d)});
        Assertions.assertFalse(new TheShapeFixer().isValid(shape));
    }

    @Test
    public void repairTwoReversedConnectedTest() {
        Shape2D shape = new Shape2D(new Dot2D[]{new Dot2D(0d, 0d), new Dot2D(1d, 0d), new Dot2D(1d, 1d),
                new Dot2D(0d, 1d), new Dot2D(0d, 0d), new Dot2D(1d, 0d), new Dot2D(2d, 0d),
                new Dot2D(1d, 1d), new Dot2D(1d, 0d)});

        Set<Dot2D> expectedDots = Set.of(new Dot2D(0d, 0d), new Dot2D(2d, 0d),
                new Dot2D(1d, 1d), new Dot2D(0d, 1d));

        Shape2D result = new TheShapeFixer().repair(shape);

        Assertions.assertEquals(expectedDots.size() + 1, result.dots().length);
        Assertions.assertTrue(expectedDots.containsAll(Arrays.asList(result.dots())));
    }

    @Test
    public void repairTwoStraightConnectedTest() {
        Shape2D shape = new Shape2D(new Dot2D[]{new Dot2D(0d, 0d), new Dot2D(1d, 0d), new Dot2D(1d, 1d),
                new Dot2D(0d, 1d), new Dot2D(0d, 0d), new Dot2D(1d, 0d), new Dot2D(1d, 1d),
                new Dot2D(2d, 0d), new Dot2D(1d, 0d)});

        Set<Dot2D> expectedDots = Set.of(new Dot2D(0d, 0d), new Dot2D(2d, 0d),
                new Dot2D(1d, 1d), new Dot2D(0d, 1d));

        Shape2D result = new TheShapeFixer().repair(shape);

        Assertions.assertEquals(expectedDots.size() + 1, result.dots().length);
        Assertions.assertTrue(expectedDots.containsAll(Arrays.asList(result.dots())));
    }

    @Test
    public void repairThreeConnectedTest() {
        Shape2D shape = new Shape2D(new Dot2D[]{new Dot2D(0d, 0d), new Dot2D(1d, 0d), new Dot2D(1d, 1d),
                new Dot2D(0d, 1d), new Dot2D(0d, 0d), new Dot2D(1d, 0d), new Dot2D(1d, 1d),
                new Dot2D(2d, 0d), new Dot2D(1d, 0d), new Dot2D(2d, 0d), new Dot2D(3d, 3d),
                new Dot2D(1d, 1d), new Dot2D(2d, 0d)});

        Set<Dot2D> expectedDots = Set.of(new Dot2D(0d, 0d), new Dot2D(2d, 0d), new Dot2D(3d, 3d),
                new Dot2D(1d, 1d), new Dot2D(0d, 1d));

        Shape2D result = new TheShapeFixer().repair(shape);

        Assertions.assertEquals(expectedDots.size() + 1, result.dots().length);
        Assertions.assertTrue(expectedDots.containsAll(Arrays.asList(result.dots())));
    }
}
