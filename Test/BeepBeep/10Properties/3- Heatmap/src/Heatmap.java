
import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.io.WriteToFile;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.NumberValue;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.mtnp.DrawPlot;
import ca.uqac.lif.cep.mtnp.UpdateTable;
import ca.uqac.lif.cep.mtnp.UpdateTableArray;
import ca.uqac.lif.cep.mtnp.UpdateTableStream;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.mtnp.table.FrequencyTable;
import ca.uqac.lif.mtnp.plot.gnuplot.HeatMap;

import java.io.InputStream;

public class Heatmap {


    public static void main(String[] args) {
        //To read the file
        InputStream is= Heatmap.class.getResourceAsStream("data.txt");
        ReadLines read=new ReadLines(is);
        //Pullable readp=read.getPullableOutput();

        QueueSource x=new QueueSource().loop(false);
        QueueSource y=new QueueSource().loop(false);
        //Constant divider= new Constant(1);

        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfData=new ApplyFunction(new JPathFunction("data.position and direction.position"));
        Fork datafork=new Fork(2);
        ApplyFunction jpfX=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("x"))));
        ApplyFunction jpfY=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("y"))));


        ApplyFunction floorX=new ApplyFunction(new Floor());
        ApplyFunction floorY=new ApplyFunction(new Floor());

        UpdateTable table= new UpdateTableStream(new FrequencyTable(-1,450,10,-1,450,10,1d),"x","y");


        Connector.connect(read,parseData);
        Connector.connect(parseData,jpfData);
        Connector.connect(jpfData,datafork);
        Connector.connect(datafork,0,jpfX,0);
        Connector.connect(datafork,1,jpfY,0);
        Connector.connect(jpfX,floorX);
        Connector.connect(jpfY,floorY);
        Pullable pX=floorX.getPullableOutput();
        Pullable pY=floorY.getPullableOutput();
        while(pX.hasNext())
        {
            x.addEvent(pX.pull());
            y.addEvent(pY.pull());
        }

        Connector.connect(x,0,table,0);
        Connector.connect(y,0,table,1);

        /* Draw a heat map out of the coordinates. */
        HeatMap plot = new HeatMap();
        plot.setTitle("Heatmap");
        DrawPlot draw = new DrawPlot(plot);
        WriteToFile w=new WriteToFile("HeatMapResult.png");
        Pump pump=new Pump();

        Connector.connect(table, draw);
        Connector.connect(table, w);
        Connector.connect(pump,w);
        Connector.connect(draw,pump);

        pump.start();









    }



}
