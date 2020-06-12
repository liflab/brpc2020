import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.io.WriteToFile;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.mtnp.DrawPlot;
import ca.uqac.lif.cep.mtnp.UpdateTable;
import ca.uqac.lif.cep.mtnp.UpdateTableStream;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Bags;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.mtnp.plot.gnuplot.Scatterplot;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class MaxSpeedAndAngle {




    public static void main(String[] args) {
        System.out.println("MaxSpeed:");
        MaxSpeed();
        System.out.println();
        System.out.println("MaxSpeedBeepBeep:");
        MaxSpeedBeepBeep();
        System.out.println();
        System.out.println("Angles:");
        Angle();
    }

    public static void MaxSpeed(){
        try {
            File beamngDictionnaries = new File("C:\\Users\\marc\\PycharmProjects\\brpc2020\\Test\\BeepBeep\\MaxSpeed\\src\\dictionnaries.txt");
            Scanner dictionnariesReader = new Scanner(beamngDictionnaries);
            QueueSource speedSource=new QueueSource();
            Cumulate max=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
            Connector.connect(speedSource,max);
            Pullable p =max.getPullableOutput();
            Object maxSpeed=0;
            while (dictionnariesReader.hasNextLine()) {
                String dictionnary = dictionnariesReader.nextLine();
                Object[] out = new Object[1];
                ParseJson.instance.evaluate(new Object[]{dictionnary},out);
                JsonElement j=(JsonElement) out[0];
                JsonMap jMap=(JsonMap) j;
                speedSource.addEvent(getSpeed(jMap));
                maxSpeed=p.pull();
            }
            dictionnariesReader.close();

            System.out.println(maxSpeed);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void MaxSpeedBeepBeep(){
        InputStream is=MaxSpeedAndAngle.class.getResourceAsStream("dictionnaries.txt");
        ReadLines reader=new ReadLines(is);
        Pullable rp=reader.getPullableOutput();
        QueueSource speedSource=new QueueSource();
        Cumulate max=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
        Connector.connect(speedSource,max);
        Pullable p =max.getPullableOutput();
        Object maxSpeed=0;

        while(rp.hasNext()){
            String dictionnary =String.valueOf(rp.pull());
            Object[] out=new Object[1];
            ParseJson.instance.evaluate(new Object[]{dictionnary},out);
            JsonElement j=(JsonElement) out[0];
            JsonMap jMap=(JsonMap) j;
            speedSource.addEvent(getSpeed(jMap));
            maxSpeed=p.pull();
        }

        System.out.println(maxSpeed);

    }

    public static JsonMap getSubDict(JsonMap dict,String wantedDictName){
        Object[] out = new Object[1];
        JPathFunction data=new JPathFunction(wantedDictName);
        data.evaluate(new Object[]{dict},out);
        JsonMap subDict=(JsonMap) out[0];

        return subDict;

    }

    public static Number getDataNumber(JsonMap dict,String elementName){
       Number data= dict.getNumber(elementName);

        return data;

    }

    public static double getSpeed(JsonMap dict){

        JsonMap subDict=getSubDict(dict,"data");
        subDict=getSubDict(subDict,"engine");
        Number speedNumber= getDataNumber(subDict,"speed");
        double speed=(Double)speedNumber;
        speed= Math.round(speed*1000.0)/1000.0;



        return speed;

    }

    public static void Angle(){


            QueueSource xSource=new QueueSource();
            QueueSource ySource=new QueueSource();
            xSource.setEvents(7,7,7,7,7);
            xSource.loop(false);
            ySource.setEvents(10,10,10,10,10).loop(false);
            QueueSource axeXSource=new QueueSource();
            axeXSource.setEvents(1,2,3,4,5);
            ApplyFunction arctan=new ApplyFunction( new ArcTangent());

            Connector.connect(xSource,0,arctan,0);
            Connector.connect(ySource,0,arctan,1);
            UpdateTable table = new UpdateTableStream("x", "y");

            Connector.connect(axeXSource,0,table,0);
            Connector.connect(arctan,0,table,1);


            DrawPlot draw = new DrawPlot(new Scatterplot());
            Pullable p=draw.getPullableOutput();
            Connector.connect(table,draw);
            WriteToFile w =new WriteToFile("graphic.png");
            Connector.connect(table,w);
            Pump pump=new Pump();
            Connector.connect(pump,w);
            Connector.connect(draw,pump);
            pump.run();








        //System.out.println(Math.toDegrees(Math.atan2(7,10)));
    }


}


