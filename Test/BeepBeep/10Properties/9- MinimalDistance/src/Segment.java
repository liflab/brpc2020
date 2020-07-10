public class Segment {
    private Point aPoint;
    private Point bPoint;
    private double xLenght;
    private double yLenght;


    public Segment(Point aPoint, Point bPoint) {
        this.aPoint = aPoint;
        this.bPoint = bPoint;
        setLenght(this.aPoint,this.bPoint);

    }

    public Segment(double x1,double x2,double y1,double y2) {
        aPoint=new Point(x1,y1);
        bPoint=new Point(x2,y2);
        setLenght(aPoint,bPoint);



    }

    private void setLenght(Point a,Point b){
        xLenght=b.getX()-a.getX();
        yLenght=b.getY()-a.getY();
    }

    public Point getaPoint() {
        return aPoint;
    }

    public Point getbPoint() {
        return bPoint;
    }

    public void setaPoint(Point aPoint) {

        this.aPoint = aPoint;
        setLenght(this.aPoint,bPoint);
    }

    public void setaPoint(double x,double y) {
        aPoint.setX(x);
        aPoint.setY(y);
        setLenght(aPoint,bPoint);
    }

    public void setbPoint(Point bPoint) {
        this.bPoint = bPoint;
        setLenght(aPoint,this.bPoint);

    }

    public void setbPoint(double x,double y) {
        bPoint.setX(x);
        bPoint.setY(y);
        setLenght(aPoint,bPoint);

    }

    public double getyLenght() {
        return yLenght;
    }

    public double getxLenght() {
        return xLenght;
    }
}
