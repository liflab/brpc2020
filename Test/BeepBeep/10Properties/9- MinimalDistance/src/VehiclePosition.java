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
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.NumberValue;

/**
 * From a JSON dictionary, extracts the vehicle's position and produces a
 * {@link Point} object containing its x-y coordinates.
 */
public class VehiclePosition extends FunctionTree
{
  /**
   * A single visible instance of the function.
   */
  public static final transient VehiclePosition instance = new VehiclePosition();
  
  /**
   * Creates a new instance of the function.
   */
  protected VehiclePosition()
  {
    super(CoordinatesToPoint.instance,
        new FunctionTree(NumberValue.instance, new JPathFunction("data.position and direction.position.x")),
        new FunctionTree(NumberValue.instance, new JPathFunction("data.position and direction.position.y")));
  }
  
  @Override
  public VehiclePosition duplicate(boolean with_state)
  {
    return this;
  }
}
