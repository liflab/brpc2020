import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.CumulativeFunction;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.io.WriteToFile;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.mtnp.DrawPlot;
import ca.uqac.lif.cep.mtnp.UpdateTable;
import ca.uqac.lif.cep.mtnp.UpdateTableStream;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.mtnp.plot.gral.Scatterplot;

import java.io.InputStream;

public class GForce {
    public static void main(String[] args) {
        InputStream is= GForce.class.getResourceAsStream("static.txt");
        ReadLines read=new ReadLines(is);
        Pullable readp=read.getPullableOutput();

        QueueSource xGForce=new QueueSource().loop(false);
        QueueSource yGForce=new QueueSource().loop(false);
        QueueSource zGForce=new QueueSource().loop(false);
        QueueSource xTablesAxis=new QueueSource();
        QueueSource yTablesAxis=new QueueSource();
        QueueSource zTablesAxis=new QueueSource();



        Cumulate xMax=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
        Cumulate xMin=new Cumulate(new CumulativeFunction<Number>(Numbers.minimum));
        Cumulate yMax=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
        Cumulate yMin=new Cumulate(new CumulativeFunction<Number>(Numbers.minimum));
        Cumulate zMax=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
        Cumulate zMin=new Cumulate(new CumulativeFunction<Number>(Numbers.minimum));

        UpdateTable xTable=new UpdateTableStream("time(second)","gforce???");
        UpdateTable yTable=new UpdateTableStream("time(second)","gforce???");
        UpdateTable zTable=new UpdateTableStream("time(second)","gforce???");

        DrawPlot xDraw= new DrawPlot(new Scatterplot());
        DrawPlot yDraw= new DrawPlot(new Scatterplot());
        DrawPlot zDraw= new DrawPlot(new Scatterplot());

        Pullable xMaxPull=xMax.getPullableOutput();
        Pullable xMinPull=xMin.getPullableOutput();
        Pullable yMaxPull=yMax.getPullableOutput();
        Pullable yMinPull=yMin.getPullableOutput();
        Pullable zMaxPull=zMax.getPullableOutput();
        Pullable zMinPull=zMin.getPullableOutput();

        WriteToFile xWrite =new WriteToFile("Static_xGForce.png");
        WriteToFile yWrite =new WriteToFile("Static_yGForce.png");
        WriteToFile zWrite =new WriteToFile("Static_zGForce.png");

        Pump xPump=new Pump();
        Pump yPump=new Pump();
        Pump zPump=new Pump();


        Connector.connect(xGForce,xMax);
        Connector.connect(xGForce,xMin);
        Connector.connect(yGForce,yMax);
        Connector.connect(yGForce,yMin);
        Connector.connect(zGForce,zMax);
        Connector.connect(zGForce,zMin);

        Connector.connect(xTablesAxis,0,xTable,0);
        Connector.connect(xGForce,0,xTable,1);
        Connector.connect(xTable,xDraw);
        Connector.connect(xTable,xWrite);
        Connector.connect(xPump,xWrite);
        Connector.connect(xDraw,xPump);

        Connector.connect(yTablesAxis,0,yTable,0);
        Connector.connect(yGForce,0,yTable,1);
        Connector.connect(yTable,yDraw);
        Connector.connect(yTable,yWrite);
        Connector.connect(yPump,yWrite);
        Connector.connect(yDraw,yPump);

        Connector.connect(zTablesAxis,0,zTable,0);
        Connector.connect(zGForce,0,zTable,1);
        Connector.connect(zTable,zDraw);
        Connector.connect(zTable,zWrite);
        Connector.connect(zPump,zWrite);
        Connector.connect(zDraw,zPump);




        float time=0;
        Object[] out= new Object[1];



        while(readp.hasNext()){

            ParseJson.instance.evaluate(new Object[]{String.valueOf(readp.pull())},out);
            JsonMap dictionnary=(JsonMap) out[0];

            xGForce.addEvent(getData(getSubDict(getSubDict(dictionnary,"data"),"gforce"),"gx"));
            yGForce.addEvent(getData(getSubDict(getSubDict(dictionnary,"data"),"gforce"),"gy"));
            zGForce.addEvent(getData(getSubDict(getSubDict(dictionnary,"data"),"gforce"),"gz"));

            xTablesAxis.addEvent(time);
            yTablesAxis.addEvent(time);
            zTablesAxis.addEvent(time);

            time+=0.10;




        }

        System.out.println("X GForce max: "+xMaxPull.pull());
        System.out.println("X GForce min: "+xMinPull.pull());
        System.out.println("Y GForce max: "+yMaxPull.pull());
        System.out.println("Y GForce min: "+yMinPull.pull());
        System.out.println("Z GForce max: "+zMaxPull.pull());
        System.out.println("Z GForce min: "+zMinPull.pull());
        xPump.run();
        yPump.run();
        zPump.run();


    }

    public static JsonMap getSubDict(JsonMap dict, String wantedDictName){
        Object[] out = new Object[1];
        JPathFunction data=new JPathFunction(wantedDictName);
        data.evaluate(new Object[]{dict},out);
        JsonMap subDict=(JsonMap) out[0];

        return subDict;

    }

    public static Object getData(JsonMap dict,String elementName){
        Object data= dict.getNumber(elementName);

        return data;

    }

}
