public class Segment {
    private Point aPoint;
    private Point bPoint;


    public Segment(Point aPoint, Point bPoint) {
        this.aPoint = aPoint;
        this.bPoint = bPoint;

    }

    public Segment(double x1,double x2,double y1,double y2) {
        aPoint=new Point(x1,y1);
        bPoint=new Point(x2,y2);



    }

    public Point getaPoint() {
        return aPoint;
    }

    public Point getbPoint() {
        return bPoint;
    }

    public void setaPoint(Point aPoint) {
        this.aPoint = aPoint;
    }

    public void setaPoint(double x,double y) {
        aPoint.setX(x);
        aPoint.setY(y);
    }

    public void setbPoint(Point bPoint) {
        this.bPoint = bPoint;
    }

    public void setbPoint(double x,double y) {
        bPoint.setX(x);
        bPoint.setY(y);
    }
}
