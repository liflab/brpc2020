import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.NumberValue;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;

import java.io.InputStream;

public class GearTime {

    public static void main(String[] args)
    {
        GearTime.GearTime();
    }

    public static void GearTime(){
        double time0, time1, deltatime;
        int currentGear = 0;
        deltatime = 0;
        time0 = 0;
        Double gearTime[] = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0};
        InputStream is=MaxAcceleration.class.getResourceAsStream("dictionnary2.txt");
        ReadLines reader= new ReadLines(is);

        ApplyFunction parseData = new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfGear = new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("data.transmission.actualGear"))));
        ApplyFunction jpfTime = new ApplyFunction(new JPathFunction("time"));

        Fork dictFork= new Fork(2);

        Connector.connect(reader, parseData);

        Connector.connect(parseData, dictFork);
        Connector.connect(dictFork,0,jpfGear,0);
        Connector.connect(dictFork,0,jpfTime,0);

        Connector.connect(parseData, jpfGear);

        Pullable pTime= jpfTime.getPullableOutput();
        Pullable pGear= jpfGear.getPullableOutput();

        while (pGear.hasNext())
        {
            currentGear = ((Number) pGear.pull()).intValue();
            time1 = convertTime(pTime.pull().toString());
            deltatime = time1 - time0;
            gearTime[currentGear] = gearTime[currentGear] + deltatime;
            time0 = time1;
        }

        System.out.println("Gear 0 = " + gearTime[0]/1000 + "s");
        System.out.println("Gear 1 = " + gearTime[1]/1000 + "s");
        System.out.println("Gear 2 = " + gearTime[2]/1000 + "s");
        System.out.println("Gear 3 = " + gearTime[3]/1000 + "s");
        System.out.println("Gear 4 = " + gearTime[4]/1000 + "s");
        System.out.println("Gear 5 = " + gearTime[5]/1000 + "s");
    }

    public static double convertTime(String timeString) {
        timeString = timeString.replaceAll("\"", "");
        String[] tokens = timeString.split(":");
        String[] secondsTokens = tokens[2].split("\\.");
        int Ms = Integer.parseInt(secondsTokens[1]);
        Ms = Ms / 1000;
        int secondsToMs = Integer.parseInt(secondsTokens[0]) * 1000;
        int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        long totalMs = secondsToMs + minutesToMs + hoursToMs + Ms;
        return totalMs;
    }
}
