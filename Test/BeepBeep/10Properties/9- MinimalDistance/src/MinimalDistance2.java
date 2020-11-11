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
import java.io.InputStream;

import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.io.Print;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.Pump;

import java.io.IOException;

import static ca.uqac.lif.cep.Connector.connect;

public class MinimalDistance2
{

  public static void main(String[] args) throws IOException
  {
    // Demo path: a square between (0,0) and (250,250)
    Segment[] path = new Segment[] {new Segment(0, 0, 0, 250), 
        new Segment(0, 250, 250, 250), 
        new Segment(250, 250, 250, 0), 
        new Segment(250, 0, 0, 0)};
    
    // The processor chain
    InputStream is = MinimalDistance2.class.getResourceAsStream("foo.txt");
    ReadLines reader = new ReadLines(is);
    ApplyFunction parseData = new ApplyFunction(ParseJson.instance);
    connect(reader, parseData);
    ApplyFunction position = new ApplyFunction(VehiclePosition.instance);
    connect(parseData, position);
    ApplyFunction distance = new ApplyFunction(new DistanceToPath(path));
    connect(position, distance);
    Pump pump = new Pump();
    connect(distance, pump);
    Print print = new Print().setSeparator("\n");
    connect(pump, print);
    pump.run();
    is.close();
    
  }

}
