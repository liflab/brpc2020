import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.NumberValue;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.Fork;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Check {
    /**
     * @Desc Program to get the number of data aquisitions that the vehicule spent on a 1x1 game unit square.
     *
     * @Note The program can run with or without the parameters.
     *
     * @Param (string) resultFilePath  The file path to specify where to write the result.
     * @Param (int) X  reference position
     * @Param (int) Y  reference position
     * @Param (int) stopX  stop reference position
     * @Param (int) stopY  stop reference position
     */
    public static void main(String[] args){
        Scanner userInput=new Scanner(System.in);
        int xPosInput=0;
        int yPosInput=0;
        int stopXPosIntput=0;
        int stopYPosIntput=0;

        if (args.length==5){

            xPosInput=Integer.parseInt(args[1].trim());
            yPosInput=Integer.parseInt(args[2].trim());
            stopXPosIntput=Integer.parseInt(args[3].trim());
            stopYPosIntput=Integer.parseInt(args[4].trim());
        }
        else if(args.length==0){
            //if the program is executed without parameters
            System.out.print("Enter the X position to check: ");
            xPosInput=userInput.nextInt();

            System.out.print("Enter the Y position to check: ");
            yPosInput=userInput.nextInt();

            System.out.print("Enter the stop X position to check:");
            stopXPosIntput=userInput.nextInt();

            System.out.print("Enter the stop Y position to check:");
            stopYPosIntput=userInput.nextInt();

        }
        else
        {
            System.exit(0);
        }


        InputStream is= Check.class.getResourceAsStream("dictionnary.txt");
        ReadLines read=new ReadLines(is);
        Fork readFork=new Fork(2);

        Constant xPosCheck=new Constant(xPosInput);
        Constant yPosCheck=new Constant(yPosInput);
        Constant stopXPosCheck=new Constant(stopXPosIntput);
        Constant stopYPosCheck=new Constant(stopYPosIntput);


        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfData=new ApplyFunction(new JPathFunction("data.position and direction.position"));
        ApplyFunction jpfX=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("x"))));
        ApplyFunction jpfY=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("y"))));
        Fork datafork=new Fork(2);
        Fork posDataFork=new Fork(2);
        Fork xDataFork=new Fork(2);
        Fork yDataFork=new Fork(2);




        //connect position data forks
        Connector.connect(read,parseData);
        Connector.connect(parseData,datafork);
        Connector.connect(datafork,0,jpfData,0);
        Connector.connect(jpfData,posDataFork);
        Connector.connect(posDataFork,0,jpfX,0);
        Connector.connect(posDataFork,1,jpfY,0);
        Connector.connect(jpfX,xDataFork);
        Connector.connect(jpfY,yDataFork);



        FunctionTree positionCheck=new FunctionTree(new PositionCheck(),xPosCheck,yPosCheck,StreamVariable.X,StreamVariable.Y);
        FunctionTree stopPositionCheck=new FunctionTree(new PositionCheck(),stopXPosCheck,stopYPosCheck,StreamVariable.X,StreamVariable.Y);
        ApplyFunction positionCheckAF=new ApplyFunction(positionCheck);
        ApplyFunction stopPositionCheckAF=new ApplyFunction(stopPositionCheck);


        //Connect positionCheck apply functions
        Connector.connect(xDataFork,0,positionCheckAF,0);
        Connector.connect(yDataFork,0,positionCheckAF,1);
        Connector.connect(xDataFork,1,stopPositionCheckAF,0);
        Connector.connect(yDataFork,1,stopPositionCheckAF,1);

        //parse second data fork to get vehicle speed
        ApplyFunction speedData=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("data.engine.speed"))));


        Connector.connect(datafork,1,speedData,0);



        int counter=0;
        boolean stopPosCheckBuf=false;
        double  speedBuf=0;
        Pullable posCheckPull=positionCheckAF.getPullableOutput();
        Pullable stopPosCheckPull=stopPositionCheckAF.getPullableOutput();
        Pullable speedDataPull=speedData.getPullableOutput();



        while (posCheckPull.hasNext()){
            speedBuf=((Number) speedDataPull.pull()).doubleValue();
            stopPosCheckBuf=(boolean) stopPosCheckPull.pull();

            if((Boolean) posCheckPull.pull()==true){
                counter++;
            }

        }

        if(!(Math.round(speedBuf)==0 & stopPosCheckBuf==true)){

            stopPosCheckBuf=false;

        }
        else{
            stopPosCheckBuf=true;
        }


        String result=String.format("Result:\nNumber of data acquisitions for ( %d , %d ) position: %d\nVehicle stopped at ( %d , %d ): %s",
                xPosInput,yPosInput,counter,stopXPosIntput,stopYPosIntput,stopPosCheckBuf);

        System.out.println(result);

        //If the program is executed with parameters
        if (args.length == 5) {
            //Write result

            try {


                FileWriter resultWriter = new FileWriter(args[0] + "PositionCheckResult.txt");

                resultWriter.write(result);

                resultWriter.close();
            }

            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
    }


}
