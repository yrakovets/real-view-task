package com.yrakovets.realview.model;

import com.yrakovets.realview.structures.Dot2D;
import com.yrakovets.realview.structures.Segment2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Calculator2DTest {
    private static final Calculator2D calculator = new Calculator2D();

    @Test
    public void calculationSampleTest() {
        Segment2D segment = new Segment2D(new Dot2D(3d, 3d), new Dot2D(6d, 4d));
        Dot2D dot = new Dot2D(9d, 3d);
        Assertions.assertEquals(-6d, calculator.calcMultiplicator(segment, dot), 0.001);
    }

    @Test
    public void calculationSample2Test() {
        Segment2D segment = new Segment2D(new Dot2D(9d, 3d), new Dot2D(6d, 4d));
        Dot2D dot = new Dot2D(3d, 3d);
        Assertions.assertEquals(6d, calculator.calcMultiplicator(segment, dot), 0.001);
    }

    @Test
    public void oneLineSegmentDotTest() {
        Segment2D segment = new Segment2D(new Dot2D(3d, 3d), new Dot2D(6d, 4d));
        Dot2D dot = new Dot2D(9d, 5d);
        Assertions.assertEquals(0d, calculator.calcMultiplicator(segment, dot), 0.001);
    }

    @Test
    public void oneLine2Test() {
        Segment2D segment = new Segment2D(new Dot2D(3d, 3d), new Dot2D(6d, 4d));
        Dot2D dot = new Dot2D(4.5d, 3.5d);
        Assertions.assertEquals(0d, calculator.calcMultiplicator(segment, dot), 0.001);
    }

    @Test
    public void oneLine3Test() {
        Segment2D segment = new Segment2D(new Dot2D(3d, 3d), new Dot2D(6d, 4d));
        Dot2D dot = new Dot2D(0d, 2d);
        Assertions.assertEquals(0d, calculator.calcMultiplicator(segment, dot), 0.001);
    }



    @Test
    public void horisontalToVericalCrossTest() {
        Segment2D segment1 = new Segment2D(new Dot2D(0d, 2d), new Dot2D(4d, 2d));
        Segment2D segment2 = new Segment2D(new Dot2D(2d, 0d), new Dot2D(2d, 4d));
        Assertions.assertTrue(calculator.isCrossed(segment1, segment2));
    }

    @Test
    public void diagonalCrossTest() {
        Segment2D segment1 = new Segment2D(new Dot2D(0d, 0d), new Dot2D(4d, 4d));
        Segment2D segment2 = new Segment2D(new Dot2D(4d, 0d), new Dot2D(0d, 4d));
        Assertions.assertTrue(calculator.isCrossed(segment1, segment2) );
    }

    @Test
    public void notTouchedTest() {
        Segment2D segment1 = new Segment2D(new Dot2D(0d, 0d), new Dot2D(1d, 1d));
        Segment2D segment2 = new Segment2D(new Dot2D(4d, 0d), new Dot2D(0d, 4d));
        Assertions.assertFalse(calculator.isCrossed(segment1, segment2));
    }

    @Test
    public void touchedTest() {
        Segment2D segment1 = new Segment2D(new Dot2D(0d, 0d), new Dot2D(2d, 2d));
        Segment2D segment2 = new Segment2D(new Dot2D(4d, 0d), new Dot2D(0d, 4d));
        Assertions.assertFalse(calculator.isCrossed(segment1, segment2));
    }

    @Test
    public void oneLineTest() {
        Segment2D segment1 = new Segment2D(new Dot2D(0d, 0d), new Dot2D(4d, 4d));
        Segment2D segment2 = new Segment2D(new Dot2D(1d, 1d), new Dot2D(2d, 2d));
        Assertions.assertFalse(calculator.isCrossed(segment1, segment2));
    }

    @Test
    public void parallelTest() {
        Segment2D segment1 = new Segment2D(new Dot2D(0d, 0d), new Dot2D(3d, 2d));
        Segment2D segment2 = new Segment2D(new Dot2D(1d, 1d), new Dot2D(4d, 3d));
        Assertions.assertFalse(calculator.isCrossed(segment1, segment2));
    }

    @Test
    public void notCrossedTest() {
        Segment2D segment1 = new Segment2D(new Dot2D(0.324d, 5.789d), new Dot2D(8.454d, 2.986d));
        Segment2D segment2 = new Segment2D(new Dot2D(4.231d, 1.231d), new Dot2D(4.34d, 3.421d));
        Assertions.assertFalse(calculator.isCrossed(segment1, segment2));
    }

}
