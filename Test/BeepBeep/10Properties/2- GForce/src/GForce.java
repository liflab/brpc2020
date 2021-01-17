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
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.mtnp.plot.gnuplot.Scatterplot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Scanner;

import static ca.uqac.lif.cep.Connector.connect;

public class GForce {
    public static void main(String[] args) {

        /**
         * @Desc For each dimension, the program generate a graphic of the car's gforce as a function of time from a BeamNG data aquisition.
         *
         * @Note The program can be also executed in the IDE without parameters.
         *
         * @Param string The file path to specify where to write the result.

         */

        long start=System.nanoTime();

        InputStream is= GForce.class.getResourceAsStream("data.txt");
        ReadLines read=new ReadLines(is);


        QueueSource tableXAxis=new QueueSource().loop(false);


        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        Fork baseDictFork= new Fork(2);
        connect(parseData,baseDictFork);
        ApplyFunction jpfGForceData=new ApplyFunction(new JPathFunction("data.gforce"));
        connect(baseDictFork,0,jpfGForceData,0);

        Fork gForceFork=new Fork(3);
        Fork gxFork=new Fork(3);
        Fork gyFork=new Fork(3);
        Fork gzFork=new Fork(3);
        Fork tableXAxisFork=new Fork(3);

        ApplyFunction jpfGX=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("gx"))));
        ApplyFunction jpfGY=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("gy"))));
        ApplyFunction jpfGZ=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("gz"))));


        ApplyFunction timeData=new ApplyFunction(new JPathFunction("time"));
        ApplyFunction time=new ApplyFunction(new FunctionTree(new getTime(), StreamVariable.X));
        connect(baseDictFork,1,timeData,0);
        connect(timeData,time);

        Cumulate xMax=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
        Cumulate xMin=new Cumulate(new CumulativeFunction<Number>(Numbers.minimum));
        Cumulate yMax=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
        Cumulate yMin=new Cumulate(new CumulativeFunction<Number>(Numbers.minimum));
        Cumulate zMax=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));
        Cumulate zMin=new Cumulate(new CumulativeFunction<Number>(Numbers.minimum));

        UpdateTable xTable=new UpdateTableStream("time (s)","gforce");
        UpdateTable yTable=new UpdateTableStream("time (s)","gforce");
        UpdateTable zTable=new UpdateTableStream("time (s)","gforce");

        KeepLast klX=new KeepLast();
        KeepLast klY=new KeepLast();
        KeepLast klZ=new KeepLast();


        DrawPlot xDraw= new DrawPlot(new Scatterplot());
        DrawPlot yDraw= new DrawPlot(new Scatterplot());
        DrawPlot zDraw= new DrawPlot(new Scatterplot());

        WriteToFile xWrite =new WriteToFile("xGForce.png");
        WriteToFile yWrite =new WriteToFile("yGForce.png");
        WriteToFile zWrite =new WriteToFile("zGForce.png");

        Pump xPump=new Pump();
        Pump yPump=new Pump();
        Pump zPump=new Pump();

        connect(read,parseData);
        connect(parseData,jpfGForceData);
        connect(jpfGForceData,gForceFork);
        connect(gForceFork,0,jpfGX,0);
        connect(gForceFork,1,jpfGY,0);
        connect(gForceFork,2,jpfGZ,0);
        connect(jpfGX,gxFork);
        connect(jpfGY,gyFork);
        connect(jpfGZ,gzFork);
        connect(gxFork,0,xMin,0);
        connect(gxFork,1,xMax,0);
        connect(gyFork,0,yMin,0);
        connect(gyFork,1,yMax,0);
        connect(gzFork,0,zMin,0);
        connect(gzFork,1,zMax,0);

        connect(tableXAxis,tableXAxisFork);

        connect(tableXAxisFork,0,xTable,0);
        connect(gxFork,2,xTable,1);
        connect(xTable,klX);
        connect(klX,xDraw);
        connect(klX,xWrite);
        connect(xPump,xWrite);
        connect(xDraw,xPump);

        connect(tableXAxisFork,1,yTable,0);
        connect(gyFork,2,yTable,1);
        connect(yTable,klY);
        connect(klY,yDraw);
        connect(klY,yWrite);
        connect(yPump,yWrite);
        connect(yDraw,yPump);

        connect(tableXAxisFork,2,zTable,0);
        connect(gzFork,2,zTable,1);
        connect(zTable,klZ);
        connect(klZ,zDraw);
        connect(klZ,zWrite);
        connect(zPump,zWrite);
        connect(zDraw,zPump);


        double gxMaxValue=0;
        double gxMinValue=0;
        double gyMaxValue=0;
        double gyMinValue=0;
        double gzMaxValue=0;
        double gzMinValue=0;

        Pullable xMaxPull=xMax.getPullableOutput();
        Pullable xMinPull=xMin.getPullableOutput();
        Pullable yMaxPull=yMax.getPullableOutput();
        Pullable yMinPull=yMin.getPullableOutput();
        Pullable zMaxPull=zMax.getPullableOutput();
        Pullable zMinPull=zMin.getPullableOutput();
        Pullable timeP=time.getPullableOutput();

        DecimalFormat decimalFormat=new DecimalFormat("#.000");



        while(xMaxPull.hasNext()){

            gxMaxValue=((Number) xMaxPull.pull()).doubleValue();
            gxMinValue=((Number) xMinPull.pull()).doubleValue();
            gyMaxValue=((Number) yMaxPull.pull()).doubleValue();
            gyMinValue=((Number) yMinPull.pull()).doubleValue();
            gzMaxValue=((Number) zMaxPull.pull()).doubleValue();
            gzMinValue=((Number) zMinPull.pull()).doubleValue();

            tableXAxis.addEvent(timeP.pull());


        }
        //Print result
        System.out.println("X GForce max: "+decimalFormat.format(gxMaxValue));
        System.out.println("X GForce min: "+decimalFormat.format(gxMinValue));
        System.out.println("Y GForce max: "+decimalFormat.format(gyMaxValue));
        System.out.println("Y GForce min: "+decimalFormat.format(gyMinValue));
        System.out.println("Z GForce max: "+decimalFormat.format(gzMaxValue));
        System.out.println("Z GForce min: "+decimalFormat.format(gzMinValue));

        xPump.run();
        yPump.run();
        zPump.run();


        long stop=System.nanoTime();
        double exectime=stop-start;
        double sexectime=(double)exectime/1_000_000_000.0;
        System.out.println(sexectime);

        if (args.length == 1) {
            //move graphics
            try {
                Path moveFile = Files.move(Paths.get("xGForce.png"),
                        Paths.get(args[0]+"xGForce.png"));

                Path moveFile2=Files.move(Paths.get("yGForce.png"),
                        Paths.get(args[0]+"yGForce.png"));

                Path moveFile3=Files.move(Paths.get("zGForce.png"),
                        Paths.get(args[0]+"zGForce.png"));
            }

            catch (IOException e) {
                System.out.println("An error occurred (move graphics).");
                e.printStackTrace();
            }

            //Write result
            try {

                FileWriter resultWriter = new FileWriter(args[0] + "GForceResult.txt");

                resultWriter.write("X GForce max: "+decimalFormat.format(gxMaxValue)
                        +"\n"+"X GForce min: "+decimalFormat.format(gxMinValue)
                        +"\n"+"Y GForce max: "+decimalFormat.format(gyMaxValue)
                        +"\n"+"Y GForce min: "+decimalFormat.format(gyMinValue)
                        +"\n"+"Z GForce max: "+decimalFormat.format(gzMaxValue)
                        +"\n"+"Z GForce min: "+decimalFormat.format(gzMinValue));

                resultWriter.close();

            }

            catch (IOException e) {
                System.out.println("An error occurred (results file).");
                e.printStackTrace();
            }

        }

    }


}
