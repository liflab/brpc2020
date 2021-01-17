import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
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
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.mtnp.plot.gnuplot.Scatterplot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static ca.uqac.lif.cep.Connector.connect;

public class CarAngle {

    /**
     * @Desc Program generate a graphic of the car angle as a function of time from a BeamNG data aquisition.
     *
     * @Note The program can be also executed in the IDE without parameters.
     *
     * @Param string The file path to specify where to write the result.

     */
    public static void main(String[] args) {



        InputStream is=CarAngle.class.getResourceAsStream("data.txt");
        ReadLines reader=new ReadLines(is);


        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        Fork parseDataFork= new Fork(2);
        ApplyFunction jpfData=new ApplyFunction(new JPathFunction("data.position and direction.direction"));
        Fork dataFork=new Fork(2);
        Fork angleForkX=new Fork(2);
        Fork angleForkY=new Fork(2);
        ApplyFunction jpfX=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("x"))));
        ApplyFunction jpfY=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("y"))));
        ApplyFunction absoluteX=new ApplyFunction(Numbers.absoluteValue);
        ApplyFunction absoluteY=new ApplyFunction(Numbers.absoluteValue);
        ApplyFunction quadrant=new ApplyFunction(new getQuadrant());
        ApplyFunction adjustment=new ApplyFunction(new QuadrantAjustement());

        //time
        //Time
        ApplyFunction timeData= new ApplyFunction(new JPathFunction("time"));
        ApplyFunction time= new ApplyFunction(new FunctionTree(new getTime(), StreamVariable.X));
        connect(parseDataFork,1,timeData,0);
        connect(timeData,time);
        Pullable timeP=time.getPullableOutput();


        QueueSource xAxisTableSource=new QueueSource().loop(false);
        KeepLast kl=new KeepLast();
        ApplyFunction arctan=new ApplyFunction(new ArcTangent());
        UpdateTable angleTable=new UpdateTableStream("time(second)","angle(degree)");
        DrawPlot draw= new DrawPlot(new Scatterplot());
        Pullable p=draw.getPullableOutput();
        WriteToFile w =new WriteToFile("CarAngleResult.png");
        Pump pump=new Pump();

        connect(reader,parseData);
        connect(parseData,parseDataFork);
        connect(parseDataFork,0,jpfData,0);
        connect(jpfData,dataFork);
        connect(dataFork,0,jpfX,0);
        connect(dataFork,1,jpfY,0);
        connect(jpfX,angleForkX);
        connect(jpfY,angleForkY);

        connect(angleForkX,1,absoluteX,0);
        connect(angleForkY,1,absoluteY,0);
        connect(absoluteX,0,arctan,0);
        connect(absoluteY,0,arctan,1);
        connect(angleForkX,0,quadrant,0);
        connect(angleForkY,0,quadrant,1);
        connect(quadrant,0,adjustment,0);
        connect(arctan,0,adjustment,1);


        connect(xAxisTableSource,0,angleTable,0);
        connect(adjustment,0,angleTable,1);
        connect(angleTable,kl);
        connect(kl,draw);
        connect(kl,w);
        connect(pump,w);
        connect(draw,pump);







        while(timeP.hasNext()){

            xAxisTableSource.addEvent(timeP.pull());



        }
        pump.run();

        if(args.length==1){
            //Write result

            try {
                //move the result file to the scenario's result folder because you can't change the current working directory in Java (big oof).
                Path moveFile = Files.move(Paths.get("CarAngleResult.png"),
                        Paths.get(args[0]+"CarAngleResult.png"));
            }

            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
    }

}


