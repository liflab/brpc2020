import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.io.Print;
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
import ca.uqac.lif.mtnp.plot.gnuplot.Scatterplot;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static ca.uqac.lif.cep.Connector.connect;

public class MinimalDistance {

    public static void main(String[] args) throws IOException
    {
        Segment[] path;
        Scanner userInput=new Scanner(System.in);

        //initiation of the segment list
        if((args.length>4)&&(args.length%2==1)){
            int numberOfSegments=((args.length-1)/2)-1;
            path = new Segment[numberOfSegments];
            Point[] points= new Point[numberOfSegments+1];


            for(int x=0; x<(numberOfSegments+1);x++){

                points[x]=new Point(Double.parseDouble(args[(2*x)+1]),Double.parseDouble(args[(2*x)+2]));

                if (x>0){
                    path[x-1]=new Segment(points[x-1],points[x]);
                }

            }

        }
        //demo for debugging
        else if(args.length==0){

            // Demo path: a square between (0,0) and (250,250)
            path = new Segment[] {new Segment(0, 0, 0, 250),
                    new Segment(0, 250, 250, 250),
                    new Segment(250, 250, 250, 0),
                    new Segment(250, 0, 0, 0)};

        }
        else{
            throw  new IllegalArgumentException(String.format("\n\tThis program can only be executed with 0 or any impair number of arguments superior or equal to 5.\n\targs.length value: %d",args.length));
        }






        // The processor chain for getting the minimal distance data
        InputStream is = MinimalDistance.class.getResourceAsStream("data.txt");
        ReadLines reader = new ReadLines(is);
        ApplyFunction parseData = new ApplyFunction(ParseJson.instance);
        connect(reader, parseData);
        Fork dataFork=new Fork(2);
        connect(parseData,dataFork);
        ApplyFunction position = new ApplyFunction(VehiclePosition.instance);
        connect(dataFork,0, position,0);
        ApplyFunction distance = new ApplyFunction(new DistanceToPath(path));
        connect(position, distance);

        Pump pump = new Pump();
        if(args.length==0){

            connect(distance, pump);
            Print print = new Print().setSeparator("\n");
            connect(pump, print);
            pump.start();

        }
        else{
            //processor chain for getting the time of each aquisition
            ApplyFunction timeData=new ApplyFunction(new JPathFunction("time"));
            connect(dataFork,1,timeData,0);
            ApplyFunction time=new ApplyFunction(new FunctionTree(new getTime(), StreamVariable.X));
            connect(timeData,time);
            QueueSource timeSource=new QueueSource().loop(false);
            QueueSource distanceSource=new QueueSource().loop(false);

            Pullable timeP=time.getPullableOutput();
            Pullable distanceP=distance.getPullableOutput();

            //fill sources to generate a graphic
            while (timeP.hasNext()){
                timeSource.addEvent(timeP.pull());
                distanceSource.addEvent(distanceP.pull());
            }

            //To generate the graphic
            UpdateTable minimaldistance=new UpdateTableStream("time (s)","minimal distance to the nearest segment");
            DrawPlot drawPlot=new DrawPlot(new Scatterplot());
            WriteToFile write=new WriteToFile("MinimalDistance.png");
            connect(timeSource,0,minimaldistance,0);
            connect(distanceSource,0,minimaldistance,1);
            connect(minimaldistance,drawPlot);
            connect(minimaldistance,write);
            connect(pump,write);
            connect(drawPlot,pump);

            pump.run();
            try{
                Path moveFile= Files.move(Paths.get("MinimalDistance.png"),Paths.get(args[0]+"MinimalDistance.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }




    }



}

