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
import ca.uqac.lif.cep.functions.BinaryFunction;

/**
 * Converts a pair of numbers into a {@link Point} object.
 */
public class CoordinatesToPoint extends BinaryFunction<Number,Number,Point>
{
  /**
   * A single visible instance of the function.
   */
  public static CoordinatesToPoint instance = new CoordinatesToPoint();
  
  /**
   * Creates an instance of the function.
   */
  protected CoordinatesToPoint()
  {
    super(Number.class, Number.class, Point.class);
  }
  
  @Override
  public Point getValue(Number x, Number y)
  {
   return new Point(x.doubleValue(), y.doubleValue());
  }
}
