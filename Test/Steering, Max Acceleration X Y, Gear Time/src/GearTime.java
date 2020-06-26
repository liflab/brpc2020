import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;

import java.io.InputStream;

public class GearTime {

    public static void GearTime(){
        double time0, time1, deltatime;
        deltatime = 0;
        time0 = 0;
        Double gearTime[] = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0};
        InputStream is=MaxAcceleration.class.getResourceAsStream("dictionnary2.txt");
        ReadLines reader=new ReadLines(is);
        Pullable rp=reader.getPullableOutput();
        while (rp.hasNext()) {

            String dictionnary = String.valueOf(rp.pull()); // read la prochaine ligne du dictionnaire aka toute l'esti de frame data
            Object[] out = new Object[1];   // créer un nouveau object JSON a évaluer or something
            ParseJson.instance.evaluate(new Object[]{dictionnary}, out);
            JsonElement j = (JsonElement) out[0];
            JsonMap jMap = (JsonMap) j;
            time1 = getTime(jMap);
            deltatime = time1 - time0;
            gearTime[getGear(jMap)] = gearTime[getGear(jMap)] + deltatime;
            time0 = time1;

        }

        System.out.println("Gear 0 = " + gearTime[0]/1000 + "s");
        System.out.println("Gear 1 = " + gearTime[1]/1000 + "s");
        System.out.println("Gear 2 = " + gearTime[2]/1000 + "s");
        System.out.println("Gear 3 = " + gearTime[3]/1000 + "s");
        System.out.println("Gear 4 = " + gearTime[4]/1000 + "s");
        System.out.println("Gear 5 = " + gearTime[5]/1000 + "s");
    }

    public static int getGear(JsonMap dict)
    {
        JsonMap subDict = getSubDict(dict, "data");
        subDict = getSubDict(subDict, "transmission");
        Number angleNumber = getDataNumber(subDict, "actualGear");
        int gear = angleNumber.intValue();
        return gear;
    }

    public static double getTime(JsonMap dict) {
        String timeString = getDataString(dict, "time");
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

    public static JsonMap getSubDict(JsonMap dict, String wantedDictName) {
        Object[] out = new Object[1];
        JPathFunction data = new JPathFunction(wantedDictName);
        data.evaluate(new Object[]{dict}, out);
        JsonMap subDict = (JsonMap) out[0];
        return subDict;
    }

    public static Number getDataNumber(JsonMap dict, String elementName) {
        Number data = dict.getNumber(elementName);
        return data;
    }

    public static String getDataString(JsonMap dict, String elementName) {
        String data = dict.getString(elementName);
        return data;
    }
}
