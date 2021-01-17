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
import ca.uqac.lif.json.JsonString;

/**
 * Computes an approximation at 1 decimal of the aquistion time of the JSON dictionnary.
 */
public class getTime extends UnaryFunction<Object,Number>
{

    public getTime(){
        super(Object.class,Number.class);
    }

    @Override
    public Number getValue(Object s)
    {

        String s1=((JsonString)s).stringValue();
        s1 = s1.replaceAll("\"", "");
        String[] tokens = s1.split(":");
        String[] secondsTokens = tokens[2].split("\\.");
        int Ms = Integer.parseInt(secondsTokens[1]);
        Ms = Ms / 1000;
        int secondsToMs = Integer.parseInt(secondsTokens[0]) * 1000;
        int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        double totalMs = secondsToMs + minutesToMs + hoursToMs + Ms;
        totalMs/=1000;

        //round to 1 decimal
        Double scale=Math.pow(10,2);

        return (Math.round(totalMs* scale) / scale);
    }

}
