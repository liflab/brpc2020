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






public class MaxAcceleration {

    public static void MaxAccelerationBeepBeep()
    {
        double time0, time1;
        double speed0, speed1;
        double speedx0, speedx1, speedy0, speedy1;
        double angle0, angle1;
        InputStream is=MaxAcceleration.class.getResourceAsStream("dictionnary2.txt");
        ReadLines reader=new ReadLines(is);
        Pullable rp=reader.getPullableOutput();
        QueueSource accelerationXSource = new QueueSource(); // La queue d'input X
        QueueSource accelerationYSource = new QueueSource(); // La queue d'input Y
        Cumulate maxX = new Cumulate(new CumulativeFunction<Number>(Numbers.maximum)); // fonction cumulative qui retourne le max
        Cumulate maxY = new Cumulate(new CumulativeFunction<Number>(Numbers.maximum)); // fonction cumulative qui retourne le max
        Connector.connect(accelerationXSource, maxX); // on lie la queue 1 avec la fonction de max
        Connector.connect(accelerationYSource, maxY);
        Pullable px = maxX.getPullableOutput(); // on fait en sorte qu'on puisse pogner l'output du max
        Pullable py = maxY.getPullableOutput();
        Object maxAccelerationX = 0;
        Object maxAccelerationY = 0;
        String dictionnary = String.valueOf(rp.pull()); // get the first data xD! motherfucK
        speed0 = getFirstSpeed(dictionnary);
        angle0 = getAngle(dictionnary);
        speedx0 = getSpeedX(speed0, angle0);
        speedy0 = getSpeedY(speed0, angle0);
        time0 = getFirstTime(dictionnary);
        while (rp.hasNext()) {
            dictionnary = String.valueOf(rp.pull()); // read la prochaine ligne du dictionnaire aka toute l'esti de frame data
            Object[] out = new Object[1];   // créer un nouveau object JSON a évaluer or something
            ParseJson.instance.evaluate(new Object[]{dictionnary}, out);
            JsonElement j = (JsonElement) out[0];
            JsonMap jMap = (JsonMap) j;
            speed1 = getSpeed(jMap);
            angle1 = getAngle(dictionnary);
            speedx1 = getSpeedX(speed1, angle1);
            speedy1 = getSpeedY(speed1, angle1);
            speedx1 = Math.abs(speedx1);
            speedy1 = Math.abs(speedy1);
            time1 = getTime(jMap);
            double deltaTime = 1 / ((time1 - time0) / 1000);
            double deltaSpeedX = speedx1 - speedx0;
            double deltaSpeedY = speedy1 - speedy0;
            double accelerationX = deltaSpeedX * deltaTime;
            double accelerationY = deltaSpeedY * deltaTime;
            accelerationXSource.addEvent(accelerationX);
            accelerationYSource.addEvent(accelerationY);
            maxAccelerationX = px.pull();
            maxAccelerationY = py.pull();
            speedx0 = speedx1;
            speedy0 = speedy1;
            time0 = time1;
        }
        System.out.println("X: " + maxAccelerationX + " km/h/s");
        System.out.println("Y: " + maxAccelerationY + " km/h/s");
    }


    public static double getSpeed(JsonMap dict) {
        JsonMap subDict = getSubDict(dict, "data");
        subDict = getSubDict(subDict, "engine");
        Number speedNumber = getDataNumber(subDict, "speed");
        double speed = (Double) speedNumber;
        speed = Math.round(speed * 1000.0) / 1000.0;
        return speed;
    }

    public static double getDirX(JsonMap dict) {
        JsonMap subDict = getSubDict(dict, "data");
        subDict = getSubDict(subDict, "position and direction");
        subDict = getSubDict(subDict,"direction");
        Number dirXNumber = getDataNumber(subDict, "x");
        double dirX = (Double) dirXNumber;
        dirX = Math.round(dirX * 1000.0) / 1000.0;
        return dirX;
    }

    public static double getDirY(JsonMap dict) {
        JsonMap subDict = getSubDict(dict, "data");
        subDict = getSubDict(subDict, "position and direction");
        subDict = getSubDict(subDict,"direction");
        Number dirYNumber = getDataNumber(subDict, "y");
        double dirY = (Double) dirYNumber;
        dirY = Math.round(dirY * 1000.0) / 1000.0;
        return dirY;
    }



    public static double getFirstSpeed(String dictionnary) {

        Object[] out = new Object[1];   // créer un nouveau object JSON a évaluer or something
        ParseJson.instance.evaluate(new Object[]{dictionnary}, out);
        JsonElement j = (JsonElement) out[0];
        JsonMap jMap = (JsonMap) j;
        double speed0 = getSpeed(jMap);
        return speed0;
    }

    public static double getAngle(String dictionnary){
    double dirX, dirY;
    Object[] out = new Object[1];   // créer un nouveau object JSON a évaluer or something
    ParseJson.instance.evaluate(new Object[]{dictionnary}, out);
    JsonElement j = (JsonElement) out[0];
    JsonMap jMap = (JsonMap) j;
    dirX = getDirX(jMap);
    dirY = getDirY(jMap);
    QueueSource sourceDirX = new QueueSource();
    QueueSource sourceDirY = new QueueSource();
    ApplyFunction arctan=new ApplyFunction(new ArcTangent());
    sourceDirX.addEvent(dirX);
    sourceDirY.addEvent(dirY);
    Connector.connect(sourceDirX,0,arctan,0);
    Connector.connect(sourceDirY,0,arctan,1);
    Pullable p = arctan.getPullableOutput();
    return (double)p.pull();
    }

    public static double getSpeedX(double speed, double angle) {

        double speedx = speed * Math.cos(angle);
        return speedx;
    }

    public static double getSpeedY(double speed, double angle) {

        double speedy = speed * Math.sin(angle);
        return speedy;
    }

    public static double getFirstTime(String dictionnary) {
        Object[] out = new Object[1];   // créer un nouveau object JSON a évaluer or something
        ParseJson.instance.evaluate(new Object[]{dictionnary}, out);
        JsonElement j = (JsonElement) out[0];
        JsonMap jMap = (JsonMap) j;
        double time0 = getTime(jMap);
        return time0;
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


/*
    public static void MaxAcceleration() {
        try {
            double time0, time1;
            double speedx0, speedx1;
            double speedy0, speedy1;
            File beamngDictionnaries = new File("C:\\Users\\julie\\Desktop\\dictionnary.txt");
            Scanner dictionnariesReader = new Scanner(beamngDictionnaries); // scan le file
            QueueSource accelerationSource = new QueueSource(); // La queue d'input 1
            Cumulate max = new Cumulate(new CumulativeFunction<Number>(Numbers.maximum)); // fonction cumulative qui retourne le max
            Connector.connect(accelerationSource, max); // on lie la queue 1 avec la fonction de max
            Pullable p = max.getPullableOutput(); // on fait en sorte qu'on puisse pogner l'output du max
            Object maxAcceleration = 0; //
            String dictionnary = dictionnariesReader.nextLine(); // get the first data xD! motherfucK
            speedx0 = getFirstSpeedX(dictionnary);
            speedy0 = getFirstSpeedY(dictionnary);
            time0 = getFirstTime(dictionnary);
            while (dictionnariesReader.hasNextLine()) {
                dictionnary = dictionnariesReader.nextLine(); // read la prochaine ligne du dictionnaire aka toute l'esti de frame data
                Object[] out = new Object[1];   // créer un nouveau object JSON a évaluer or something
                ParseJson.instance.evaluate(new Object[]{dictionnary}, out);
                JsonElement j = (JsonElement) out[0];
                JsonMap jMap = (JsonMap) j;
                speed1 = getSpeed(jMap);
                time1 = getTime(jMap);
                double deltaTime = 1 / ((time1 - time0) / 1000);
                double deltaSpeed = speed1 - speed0;
                double acceleration = deltaSpeed * deltaTime ;
                accelerationSource.addEvent(acceleration);
                maxAcceleration = p.pull();
                speed0 = speed1;
                time0 = time1;
            }
            dictionnariesReader.close();
            System.out.println(maxAcceleration + " km/h/s");
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
*/
    public static void MaxSpeed() {
        try {
            File beamngDictionnaries = new File("C:\\Users\\julie\\Desktop\\dictionnary.txt");
            Scanner dictionnariesReader = new Scanner(beamngDictionnaries); // scan le file
            QueueSource speedSource = new QueueSource(); // La queue d'input 1
            Cumulate max = new Cumulate(new CumulativeFunction<Number>(Numbers.maximum)); // fonction cumulative qui retourne le max
            Connector.connect(speedSource, max); // on lie la queue 1 avec la fonction de max
            Pullable p = max.getPullableOutput(); // on fait en sorte qu'on puisse pogner l'output du max
            Object maxSpeed = 0; //
            while (dictionnariesReader.hasNextLine()) {
                String dictionnary = dictionnariesReader.nextLine(); // read la prochaine ligne du dictionnaire aka toute l'esti de frame data
                Object[] out = new Object[1];   // créer un nouveau object JSON a évaluer or something
                ParseJson.instance.evaluate(new Object[]{dictionnary}, out);
                JsonElement j = (JsonElement) out[0];
                JsonMap jMap = (JsonMap) j;
                speedSource.addEvent(getSpeed(jMap));
                maxSpeed = p.pull();
            }
            dictionnariesReader.close();
            System.out.println(maxSpeed);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
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
    /*
    static float getSpeed(JsonMap jMap)
    {
        float speed;
        JsonMap jspeed = jMap.getNumber("speed");
        jspeed.toString();
        speed = jspeed;
        return speed;
    }


    public static void main(String[] args)
    {
        try
        {
            File beamngDictionnaries= new File("C:\\Users\\julie\\Desktop\\dictionnary.txt");
            Scanner dictionnaryReader= new Scanner(beamngDictionnaries);
            QueueSource speedSource=new QueueSource();
            Cumulate max=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
            Connector.connect(speedSource,max);
            Pullable p=max.getPullableOutput();
            Object maxSpeed=0;
            while(dictionnaryReader.hasNextLine())
            {
                String dictionnary=dictionnaryReader.nextLine();
                Object[]out=new Object[1];
                ParseJson.instance.evaluate(new Object[]{dictionnary},out);
                JsonElement j=(JsonElement) out[0];
                JsonMap jMap = (JsonMap) j;
                speedSource.addEvent(getSpeed(jMap));
                maxSpeed=p.pull();
            }

            dictionnaryReader.close();
            System.out.println(maxSpeed);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("error occurred.");
            e.printStackTrace();
        }
    }


}
*/
