package web.week5;

public class Line {
    private final double start, end, common;
    private final boolean isHorizontal;

    public Line(Point p, Point q) {
        if (p == null || q == null) throw new IllegalArgumentException();
        if (p.equals(q)) throw new IllegalArgumentException("Endpoints cannot be the same");
        if ((p.getX() != q.getX()) && (p.getY() != q.getY()))
            throw new IllegalArgumentException("Line has to align to the axes");
        this.isHorizontal = p.getY() == q.getY();
        if (this.isHorizontal) {
            this.common = p.getY();
            if (p.getX() < q.getX()) {
                this.start = p.getX();
                this.end = q.getX();
            } else {
                this.start = q.getX();
                this.end = p.getX();
            }
        } else {
            this.common = p.getX();
            if (p.getY() < q.getY()) {
                this.start = p.getY();
                this.end = q.getY();
            } else {
                this.start = q.getY();
                this.end = p.getY();
            }
        }
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }

    public double getCommon() {
        return common;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }
}
