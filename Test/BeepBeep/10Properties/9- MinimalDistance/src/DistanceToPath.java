/*
    BeepBeep processor chains for BeamNG
    Copyright (C) 2019-2020  Laboratoire d'informatique formelle

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import ca.uqac.lif.cep.functions.UnaryFunction;

/**
 * Computes the closest distance between a two-dimensional point and a
 * path defined as a sequence of linear segments. The function proceeds
 * by computing the shortest distance between the point and each segment,
 * and returns the smallest of these values.
 */
public class DistanceToPath extends UnaryFunction<Point,Number>
{
    /**
     * The segments that make the path.
     */
    protected Segment[] m_segments;

    /**
     * Creates an instance of of the function.
     * @param segments The linear segments that define the path. The segments
     * do not need to be listed in contiguous order. No check is made to ensure
     * that the segments form a connected path.
     */
    public DistanceToPath(Segment ... segments)
    {
        super(Point.class, Number.class);
        m_segments = segments;
    }

    @Override
    public Number getValue(Point p)
    {
        if (m_segments.length == 0)
        {
            return 0;
        }
        double min_distance = -1;
        for (Segment s : m_segments)
        {
            double d = distanceToSegment(p, s);
            if (min_distance < 0 || d < min_distance)
            {
                min_distance = d;
            }
        }
        return min_distance;
    }

    /**
     * Computes the shortest distance between a point and a finite line segment.
     * This code has been adapted from a post on
     * <a href="https://stackoverflow.com/a/6853926">StackOverflow</a>.
     * @param p The point
     * @param s The segment
     * @return The shortest distance
     */
    protected static double distanceToSegment(Point p, Segment s)
    {
        double x = p.getX();
        double y = p.getY();
        double x1 = s.getaPoint().getX();
        double y1 = s.getaPoint().getY();
        double x2 = s.getbPoint().getX();
        double y2 = s.getbPoint().getY();
        double A = x - x1;
        double B = y - y1;
        double C = x2 - x1;
        double D = y2 - y1;
        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = -1;
        if (len_sq != 0) //in case of 0 length line
        {
            param = dot / len_sq;
        }
        double xx, yy;
        if (param < 0)
        {
            xx = x1;
            yy = y1;
        }
        else if (param > 1)
        {
            xx = x2;
            yy = y2;
        }
        else
        {
            xx = x1 + param * C;
            yy = y1 + param * D;
        }
        double dx = x - xx;
        double dy = y - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

