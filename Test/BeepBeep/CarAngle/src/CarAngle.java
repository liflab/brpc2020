import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.io.WriteToFile;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.mtnp.DrawPlot;
import ca.uqac.lif.cep.mtnp.UpdateTable;
import ca.uqac.lif.cep.mtnp.UpdateTableStream;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.mtnp.plot.gnuplot.Scatterplot;

import java.io.InputStream;

public class CarAngle {
    public static void main(String[] args) {
        InputStream is=CarAngle.class.getResourceAsStream("d6.txt");
        ReadLines reader=new ReadLines(is);
        Pullable rp=reader.getPullableOutput();

        QueueSource xSource=new QueueSource().loop(false);
        QueueSource ySource=new QueueSource().loop(false);
        Fork xFork= new Fork(2);
        Fork yFork= new Fork(2);
        Connector.connect(xSource,xFork);
        Connector.connect(ySource,yFork);
        Pullable x0= xFork.getPullableOutput(0);
        Pullable x1= xFork.getPullableOutput(1);
        Pullable y0= yFork.getPullableOutput(0);
        Pullable y1= yFork.getPullableOutput(1);

        ApplyFunction absoluteX=new ApplyFunction(Numbers.absoluteValue);
        ApplyFunction absoluteY=new ApplyFunction(Numbers.absoluteValue);
        ApplyFunction quadrant=new ApplyFunction(new getQuadrant());
        ApplyFunction adjustment=new ApplyFunction(new QuadrantAjustement());

        QueueSource xAxisTableSource=new QueueSource();
        ApplyFunction arctan=new ApplyFunction(new ArcTangent());
        UpdateTable angleTable=new UpdateTableStream("time(second)","angle(degree)");
        DrawPlot draw= new DrawPlot(new Scatterplot());
        Pullable p=draw.getPullableOutput();
        WriteToFile w =new WriteToFile("CarAngle6.png");
        Pump pump=new Pump();

        Connector.connect(xFork,1,absoluteX,0);
        Connector.connect(yFork,1,absoluteY,0);
        Connector.connect(absoluteX,0,arctan,0);
        Connector.connect(absoluteY,0,arctan,1);
        Connector.connect(xFork,0,quadrant,0);
        Connector.connect(yFork,0,quadrant,1);
        Connector.connect(quadrant,0,adjustment,0);
        Connector.connect(arctan,0,adjustment,1);


        Connector.connect(xAxisTableSource,0,angleTable,0);
        Connector.connect(adjustment,0,angleTable,1);
        Connector.connect(angleTable,draw);
        Connector.connect(angleTable,w);
        Connector.connect(pump,w);
        Connector.connect(draw,pump);





        float time=0;

        //note: in this test code, the data aquisition rate per second is hardcoded

        while(rp.hasNext()){
            String dictionnary =String.valueOf(rp.pull());
            Object[] out=new Object[1];
            ParseJson.instance.evaluate(new Object[]{dictionnary},out);
            JsonElement j =(JsonElement) out[0];
            JsonMap jMap=(JsonMap) j;


            xSource.addEvent(getData(getSubDict(getSubDict(getSubDict(jMap,"data"),"position and direction"),"direction"),"x"));
            ySource.addEvent(getData(getSubDict(getSubDict(getSubDict(jMap,"data"),"position and direction"),"direction"),"y"));
            xAxisTableSource.addEvent(time);
            time+=0.10;

        }
        pump.run();

    }
    public static JsonMap getSubDict(JsonMap dict,String wantedDictName){
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


