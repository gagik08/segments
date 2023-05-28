package com.epam.rd.autotasks.segments;

public class Segment {
    private final Point start;
    private final Point end;

    public Segment(Point start, Point end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Segment points cannot be null");
        }
        if (start.equals(end)) {
            throw new IllegalArgumentException("Degenerate segment: start and end points are the same");
        }
        this.start = start;
        this.end = end;
    }

    public double length() {
        return start.distanceTo(end);
    }

    public Point middle() {
        double x = (start.getX() + end.getX()) / 2;
        double y = (start.getY() + end.getY()) / 2;
        return new Point(x, y);
    }

    public Point intersection(Segment another) {
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();
        double x3 = another.start.getX();
        double y3 = another.start.getY();
        double x4 = another.end.getX();
        double y4 = another.end.getY();

        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (denominator == 0) {
            // Segments are collinear
            return null;
        }

        double intersectX = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denominator;
        double intersectY = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denominator;

        Point intersection = new Point(intersectX, intersectY);

        // Check if the intersection point is within both segments
        if (isPointOnSegment(intersection) && another.isPointOnSegment(intersection)) {
            return intersection;
        } else {
            return null;
        }
    }

    private boolean isPointOnSegment(Point point) {
        double minX = Math.min(start.getX(), end.getX());
        double maxX = Math.max(start.getX(), end.getX());
        double minY = Math.min(start.getY(), end.getY());
        double maxY = Math.max(start.getY(), end.getY());

        return point.getX() >= minX && point.getX() <= maxX && point.getY() >= minY && point.getY() <= maxY;
    }

}
