package com.yrakovets.realview;

import com.yrakovets.realview.model.Calculator2D;
import com.yrakovets.realview.structures.Dot2D;
import com.yrakovets.realview.structures.Segment2D;
import com.yrakovets.realview.structures.Shape2D;

import java.util.*;

public class TheShapeFixer {
    private final Calculator2D calculator =  new Calculator2D();
    private boolean removeIntermediateDots = false;

    public void setRemoveIntermediateDots(boolean removeIntermediateDots) {
        this.removeIntermediateDots = removeIntermediateDots;
    }

    public boolean isValid(Shape2D shape) {
        return checkDotsUnique(shape.dots())
                && checkShapeIsClosed(shape.dots())
                && checkSegmentsIsNotCrossed(shape.dots());
    }

    private boolean checkDotsUnique(Dot2D[] dots) {
        int length = dots.length;
        Set<Dot2D> knownDots = new HashSet<>();
        for (int i = 0; i < length - 1; i++) {
            if (knownDots.contains(dots[i])) {
                return false;
            }
            knownDots.add(dots[i]);
        }
        return true;
    }

    private boolean checkShapeIsClosed(Dot2D[] dots) {
        return dots[dots.length - 1].equals(dots[0]);
    }

    private boolean checkSegmentsIsNotCrossed(Dot2D[] dots) {
        int length = dots.length;
        for (int i = 0; i < length - 2; i++) {
            for (int j = i + 1; j < length - 1; j++) {
                Segment2D segment1 = new Segment2D(dots[i], dots[i+1]);
                Segment2D segment2 = new Segment2D(dots[j], dots[j+1]);
                if (calculator.isCrossed(segment1, segment2)) {
                    return false;
                }
            }
        }

        return true;
    }

    public Shape2D repair(Shape2D shape) {
        List<Shape2D> simpleShapes = splitToSimpleShapes(shape.dots());
        for (Shape2D simpleShape: simpleShapes) {
            if (!isValid(simpleShape)) {
                throw new IllegalArgumentException("Shape contains not valid shapes");
            }
        }
        Shape2D resultShape = simpleShapes.getFirst();
        for (int i = 1; i < simpleShapes.size(); i++) {
            resultShape = connectShapes(resultShape, simpleShapes.get(i));
        }
        return resultShape;
    }

    private Shape2D connectShapes(Shape2D shape, Shape2D anotherShape) {
        List<Dot2D> newShapeDots = new ArrayList<>();

        int[] lineSegmentsIndexes = findCommonLineSegments(shape, anotherShape);
        int shapeCommonSegmentIndex = lineSegmentsIndexes[0];
        int anotherShapeCommonSegmentIndex = lineSegmentsIndexes[1];

        Segment2D shapeCommonSegment =  new Segment2D(shape.dots()[shapeCommonSegmentIndex],
                shape.dots()[shapeCommonSegmentIndex + 1]);
        Segment2D newShapeCommonSegment = new Segment2D(anotherShape.dots()[anotherShapeCommonSegmentIndex],
                anotherShape.dots()[anotherShapeCommonSegmentIndex + 1]);
        boolean isReversed = calculator.isCollinearReversed(shapeCommonSegment, newShapeCommonSegment);

        newShapeDots.addAll(Arrays.stream(Arrays.copyOfRange(shape.dots(), 0, shapeCommonSegmentIndex + 1)).toList());
        newShapeDots.addAll(extractConnectedShapeDots(shape, anotherShape, isReversed, shapeCommonSegmentIndex,
                anotherShapeCommonSegmentIndex));
        newShapeDots.addAll(Arrays.stream(Arrays.copyOfRange(shape.dots(), shapeCommonSegmentIndex + 1, shape.dots().length)).toList());

        List<Dot2D> cleanDots = getCleanDots(newShapeDots);

        Dot2D[] array = new Dot2D[cleanDots.size()];
        cleanDots.toArray(array);
        return new Shape2D(array);
    }

    private List<Dot2D> extractConnectedShapeDots(Shape2D shape, Shape2D anotherShape, boolean isReversed,
                                                  int shapeCommonSegmentIndex, int anotherShapeCommonSegmentIndex) {
        if (!isReversed) {
            return extractStraightConnectionDots(shape, anotherShape, shapeCommonSegmentIndex, anotherShapeCommonSegmentIndex);
        } else {
            return extractReversedConnectionDots(shape, anotherShape, shapeCommonSegmentIndex, anotherShapeCommonSegmentIndex);
        }
    }

    private List<Dot2D> extractReversedConnectionDots(Shape2D shape, Shape2D anotherShape, int shapeCommonSegmentIndex, int anotherShapeCommonSegmentIndex) {
        List<Dot2D> connectedDots = new ArrayList<>();
        if (!shape.dots()[shapeCommonSegmentIndex].equals(anotherShape.dots()[anotherShapeCommonSegmentIndex + 1])) {
            connectedDots.add(anotherShape.dots()[anotherShapeCommonSegmentIndex + 1]);
        }
        for (int i = anotherShapeCommonSegmentIndex + 2; i < anotherShape.dots().length; i++) {
            connectedDots.add(anotherShape.dots()[i]);
        }
        for (int i = 0; i < anotherShapeCommonSegmentIndex; i++) {
            connectedDots.add(anotherShape.dots()[i]);
        }
        if (!shape.dots()[shapeCommonSegmentIndex + 1].equals(anotherShape.dots()[anotherShapeCommonSegmentIndex])) {
            connectedDots.add(anotherShape.dots()[anotherShapeCommonSegmentIndex]);
        }
        return connectedDots;
    }

    private List<Dot2D> extractStraightConnectionDots(Shape2D shape, Shape2D anotherShape, int shapeCommonSegmentIndex, int anotherShapeCommonSegmentIndex) {
        List<Dot2D> connectedDots = new ArrayList<>();
        if (!shape.dots()[shapeCommonSegmentIndex].equals(anotherShape.dots()[anotherShapeCommonSegmentIndex])) {
            connectedDots.add(anotherShape.dots()[anotherShapeCommonSegmentIndex]);
        }
        for (int i = anotherShapeCommonSegmentIndex - 1; i > 0; i--) {
            connectedDots.add(anotherShape.dots()[i]);
        }
        for (int i = shape.dots().length - 2; i > anotherShapeCommonSegmentIndex + 1; i--) {
            connectedDots.add(anotherShape.dots()[i]);
        }
        if (!shape.dots()[shapeCommonSegmentIndex + 1].equals(anotherShape.dots()[anotherShapeCommonSegmentIndex + 1])) {
            connectedDots.add(anotherShape.dots()[anotherShapeCommonSegmentIndex + 1]);
        }
        return connectedDots;
    }

    private List<Dot2D> getCleanDots(List<Dot2D> shapeDots) {
        List<Dot2D> cleanDots = new ArrayList<>();
        cleanDots.add(shapeDots.getFirst());

        for (int i = 1; i < shapeDots.size() - 1; i++) {
            if (shapeDots.get(i).equals(shapeDots.get(i + 1))) {
                continue;
            }
            Segment2D segment1 = new Segment2D(cleanDots.getLast(), shapeDots.get(i));
            Segment2D segment2 = new Segment2D(shapeDots.get(i), shapeDots.get(i + 1));
            if (calculator.areCollinear(segment1, segment2)) {
                if (removeIntermediateDots || calculator.isCollinearReversed(segment1, segment2)) {
                    continue;
                }
            }
            cleanDots.add(shapeDots.get(i));
        }
        cleanDots.add(shapeDots.getLast());
        return cleanDots;
    }

    private int[] findCommonLineSegments(Shape2D shape, Shape2D newShape) {
        for (int i = 0; i < shape.dots().length - 1; i++) {
            for (int j = 0; j < newShape.dots().length - 1; j++) {
                Segment2D segment1 = new Segment2D(shape.dots()[i], shape.dots()[i+1]);
                Segment2D segment2 = new Segment2D(newShape.dots()[j], newShape.dots()[j+1]);
                if (calculator.hasCommonInterval(segment1, segment2)){
                    return new int[]{i,j};
                }
            }
        }

        throw new IllegalArgumentException("Shapes has not common lines");
    }

    private List<Shape2D> splitToSimpleShapes(Dot2D[] dots) {
        List<Shape2D> splittedShapes = new ArrayList<>();
        Integer opened = null;
        for (int i = 0; i < dots.length; i++) {
            if (opened == null) {
                opened = i;
            } else {
                if (dots[i].equals(dots[opened])) {
                    splittedShapes.add(new Shape2D(Arrays.copyOfRange(dots, opened, i + 1)));
                    opened = null;
                }
            }
        }
        if (opened != null) {
            throw new IllegalArgumentException("Could not split to closed shapes");
        }
        return splittedShapes;
    }
}
