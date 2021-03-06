import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.io.WriteToFile;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.NumberValue;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.mtnp.DrawPlot;
import ca.uqac.lif.cep.mtnp.UpdateTable;
import ca.uqac.lif.cep.mtnp.UpdateTableStream;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.KeepLast;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.mtnp.plot.gnuplot.Scatterplot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import static ca.uqac.lif.cep.Connector.connect;


public class MinMaxAverageRPM {

    /**
     * @Desc Program to get min, max and average rpm from a BeamNG data aquisition and generate a graphic of the rpm as a function of time.
     *
     * @Param resultFilePath (optional): The file path to specify where to write the results.
     */
    public static void main(String[] args)
    {
        InputStream is= MinMaxAverageRPM.class.getResourceAsStream("data.txt");
        ReadLines read=new ReadLines(is);
        Pullable readp=read.getPullableOutput();


        QueueSource count=new QueueSource();
        QueueSource tableXAxis=new QueueSource().loop(false);

        UpdateTable rpmTable=new UpdateTableStream("time(second)","rpm");
        KeepLast kl=new KeepLast();
        DrawPlot rpmDraw=new DrawPlot(new Scatterplot());
        WriteToFile rpmWrite= new WriteToFile("RPM-Result.png");
        Pump rpmpump=new Pump();




        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        Fork dataFork=new Fork(2);
        connect(parseData,dataFork);

        ApplyFunction jpfRPMData=new ApplyFunction(new FunctionTree( NumberValue.instance,(new JPathFunction("data.engine.rpm"))));
        Fork rpmFork=new Fork(4);
        Cumulate rpmMin= new Cumulate(new CumulativeFunction<Number>(Numbers.minimum));
        Cumulate rpmMax= new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
        Cumulate sum=new Cumulate(new CumulativeFunction<Number>(Numbers.addition));
        ApplyFunction division =new ApplyFunction(Numbers.division);
        Pullable minPull=rpmMin.getPullableOutput();
        Pullable maxPull=rpmMax.getPullableOutput();
        Pullable averagePull=division.getPullableOutput();

        connect(read,parseData);
        connect(dataFork,0,jpfRPMData,0);
        connect(jpfRPMData,rpmFork);
        connect(rpmFork,1,rpmMin,0);
        connect(rpmFork,2,rpmMax,0);
        connect(rpmFork,3,sum,0);
        connect(sum,0,division,0);
        connect(count,0,division,1);

        //Time
        ApplyFunction timeData= new ApplyFunction(new JPathFunction("time"));
        ApplyFunction time= new ApplyFunction(new FunctionTree(new getTime(), StreamVariable.X));
        connect(dataFork,1,timeData,0);
        connect(timeData,time);
        Pullable timeP=time.getPullableOutput();

        connect(tableXAxis,0,rpmTable,0);
        connect(rpmFork,0,rpmTable,1);
        connect(rpmTable,kl);
        connect(kl,rpmDraw);
        connect(kl,rpmWrite);
        connect(rpmpump,rpmWrite);
        connect(rpmDraw,rpmpump);






        int i=1;
        float min=0;
        float max=0;
        float average=0;


        while(readp.hasNext()){

            count.addEvent(i);
            tableXAxis.addEvent(timeP.pull());
            i++;
            min=(Float) minPull.pull();
            max=(Float) maxPull.pull();
            average=(Float) averagePull.pull();


        }


        rpmpump.run();//start the pump to generate de graphic

        //Print results
        System.out.println("Max RPM: "+max+"   "+"Min RPM: "+min+"   "+"Average RPM: "+average);

        //if the program is not executed in an ide.
        if (args.length == 1) {
            DecimalFormat decimalFormat = new DecimalFormat("#.000");//keep three decimal places


            //move graphics
            try {
                Path moveFile = Files.move(Paths.get("RPM-Result.png"),
                        Paths.get(args[0]+"RPM-Result.png"));
            }

            catch (IOException e) {
                System.out.println("An error occurred (move graphics).");
                e.printStackTrace();
            }

            //Write result
            try {

                FileWriter resultWriter = new FileWriter(args[0] + "RPM-Result.txt");

                resultWriter.write("Max RPM: "+max+"\n"+"Min RPM: "+min+"\n"+"Average RPM: "+average);

                resultWriter.close();
            }

            catch (IOException e) {
                System.out.println("An error occurred (results file).");
                e.printStackTrace();
            }

        }


    }

}
