import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.CumulativeFunction;
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
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.mtnp.plot.gral.Scatterplot;

import java.io.InputStream;


public class MinMaxAverageRPM {
    public static void main(String[] args)
    {
        InputStream is= MinMaxAverageRPM.class.getResourceAsStream("static.txt");
        ReadLines read=new ReadLines(is);
        Pullable readp=read.getPullableOutput();

        QueueSource rpm=new QueueSource().loop(false);
        QueueSource count=new QueueSource();
        QueueSource tableXAxis=new QueueSource();
        Fork rpmFork=new Fork(4);

        UpdateTable rpmTable=new UpdateTableStream("time(second)","rpm");
        DrawPlot rpmDraw=new DrawPlot(new Scatterplot());
        WriteToFile rpmWrite= new WriteToFile("Static_rpm.png");
        Pump rpmpump=new Pump();


        Cumulate rpmMin= new Cumulate(new CumulativeFunction<Number>(Numbers.minimum));
        Cumulate rpmMax= new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
        Cumulate sum=new Cumulate(new CumulativeFunction<Number>(Numbers.addition));
        ApplyFunction division =new ApplyFunction(Numbers.division);
        Pullable minPull=rpmMin.getPullableOutput();
        Pullable maxPull=rpmMax.getPullableOutput();
        Pullable averagePull=division.getPullableOutput();

        Connector.connect(rpm,rpmFork);
        Connector.connect(rpmFork,1,rpmMin,0);
        Connector.connect(rpmFork,2,rpmMax,0);
        Connector.connect(rpmFork,3,sum,0);
        Connector.connect(sum,0,division,0);
        Connector.connect(count,0,division,1);


        Connector.connect(tableXAxis,0,rpmTable,0);
        Connector.connect(rpmFork,0,rpmTable,1);
        Connector.connect(rpmTable,rpmDraw);
        Connector.connect(rpmTable,rpmWrite);
        Connector.connect(rpmpump,rpmWrite);
        Connector.connect(rpmDraw,rpmpump);





        float time=0;
        int i=1;
        Object[] out= new Object[1];
        float min=0;
        float max=0;
        float average=0;


        while(readp.hasNext()){
            ParseJson.instance.evaluate(new Object[]{String.valueOf(readp.pull())},out);
            JsonMap dictionnary=(JsonMap) out[0];
            rpm.addEvent(getData(getSubDict(getSubDict(dictionnary,"data"),"engine"),"rpm"));
            count.addEvent(i);
            tableXAxis.addEvent(time);
            i++;
            time+=0.10;
            min=(Float) minPull.pull();
            max=(Float) maxPull.pull();
            average=(Float) averagePull.pull();


        }

        System.out.println("Max RPM: "+max+"   "+"Min RPM: "+min+"   "+"Average RPM: "+average);
        rpmpump.run();

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
