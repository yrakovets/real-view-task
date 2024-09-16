package com.yrakovets.realview.model;

import com.yrakovets.realview.structures.Dot2D;
import com.yrakovets.realview.structures.Segment2D;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class Calculator2D {
    public boolean isCrossed(Segment2D segment1, Segment2D segment2) {
        return (calcMultiplicator(segment1, segment2.getStart())
                * calcMultiplicator(segment1, segment2.getEnd()) < 0)
                && (calcMultiplicator(segment2, segment1.getStart())
                * calcMultiplicator(segment2, segment1.getEnd()) < 0);
    }

    public double calcMultiplicator(Segment2D segment, Dot2D dot) {
        return (segment.getEnd().x() - segment.getStart().x()) * (dot.y() - segment.getStart().y())
                - (segment.getEnd().y() - segment.getStart().y()) * (dot.x() - segment.getStart().x());
    }

    public boolean hasCommonInterval(Segment2D segment1, Segment2D segment2) {
        return areCollinear(segment1, segment2)
                && hasCommonLength(segment1, segment2);
    }

    private boolean hasCommonLength(Segment2D segment1, Segment2D segment2) {
        double xDiff = min(max(segment1.getStart().x(), segment1.getEnd().x()), max(segment2.getStart().x(), segment2.getEnd().x())) -
                max(min(segment1.getStart().x(), segment1.getEnd().x()), min(segment2.getStart().x(), segment2.getEnd().x()));
        double yDiff = min(max(segment1.getStart().y(), segment1.getEnd().y()), max(segment2.getStart().y(), segment2.getEnd().y())) -
                max(min(segment1.getStart().y(), segment1.getEnd().y()), min(segment2.getStart().y(), segment2.getEnd().y()));
        return xDiff >= 0 && yDiff >= 0 && (xDiff + yDiff) > 0;
    }

    public boolean areCollinear(Segment2D segment1, Segment2D segment2) {
        return calcMultiplicator(segment1, segment2.getStart()) == 0
                && calcMultiplicator(segment1, segment2.getEnd()) == 0;
    }


    public boolean isCollinearReversed(Segment2D shapeSegment, Segment2D shape2Segment) {
        return (shapeSegment.getEnd().x() - shapeSegment.getStart().x())
                * (shape2Segment.getEnd().x() - shape2Segment.getStart().x()) < 0
                || (shapeSegment.getEnd().y() - shapeSegment.getStart().y())
                * (shape2Segment.getEnd().y() - shape2Segment.getStart().y()) < 0;
    }
}
