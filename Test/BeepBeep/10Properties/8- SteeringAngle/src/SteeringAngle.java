import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import javafx.scene.shape.Arc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.Scanner;



public class SteeringAngle {

    public static double getSteering(JsonMap dict) {
        JsonMap subDict = getSubDict(dict, "data");
        subDict = getSubDict(subDict, "position and direction");
        subDict = getSubDict(subDict,"direction");
        Number angleNumber = getDataNumber(subDict, "steering");
        double angle = angleNumber.doubleValue();
        angle = Math.round(angle * 1000.0) / 1000.0;
        return angle;
    }

    public static void SteeringAngle()
    {
        InputStream is=MaxAcceleration.class.getResourceAsStream("dictionnary2.txt");
        ReadLines reader=new ReadLines(is);
        Pullable rp=reader.getPullableOutput();
        while (rp.hasNext()) {
            String dictionnary = String.valueOf(rp.pull()); // read la prochaine ligne du dictionnaire aka toute l'esti de frame data
            Object[] out = new Object[1];   // créer un nouveau object JSON a évaluer or something
            ParseJson.instance.evaluate(new Object[]{dictionnary}, out);
            JsonElement j = (JsonElement) out[0];
            JsonMap jMap = (JsonMap) j;
            System.out.println(getSteering(jMap));
        }
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
