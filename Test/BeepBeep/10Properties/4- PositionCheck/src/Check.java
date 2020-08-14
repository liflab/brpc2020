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
     * @Param (string) resultFilePath (optional) the file path to specify where to write the result.
     * @Param2 (int) X reference position
     * @Param3 (int) Y reference position
     */
    public static void main(String[] args){
        Scanner userInput=new Scanner(System.in);
        int xPosInput=0;
        int yPosInput=0;

        if (args.length==3){

            xPosInput=Integer.parseInt(args[1].trim());
            yPosInput=Integer.parseInt(args[2].trim());
        }
        else{ //if the program is executed from the ide
            System.out.print("Enter the X position to check: ");
            xPosInput=userInput.nextInt();
            System.out.print("Enter the Y position to check: ");
            yPosInput=userInput.nextInt();
        }


        InputStream is= Check.class.getResourceAsStream("data.txt");
        ReadLines read=new ReadLines(is);

        Constant xPosCheck=new Constant(xPosInput);
        Constant yPosCheck=new Constant(yPosInput);

        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfData=new ApplyFunction(new JPathFunction("data.position and direction.position"));
        Fork datafork=new Fork(2);
        FunctionTree positionCheck=new FunctionTree(new PositionCheck(),xPosCheck,yPosCheck,StreamVariable.X,StreamVariable.Y);
        ApplyFunction positionCheckAF=new ApplyFunction(positionCheck);
        ApplyFunction jpfX=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("x"))));
        ApplyFunction jpfY=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("y"))));


        Connector.connect(read,parseData);
        Connector.connect(parseData,jpfData);
        Connector.connect(jpfData,datafork);
        Connector.connect(datafork,0,jpfX,0);
        Connector.connect(datafork,1,jpfY,0);
        Connector.connect(jpfX,0,positionCheckAF,0);
        Connector.connect(jpfY,0,positionCheckAF,1);

        int counter=0;
        Pullable posCheckPull=positionCheckAF.getPullableOutput();

        while (posCheckPull.hasNext()){
            if((Boolean) posCheckPull.pull()==true){
                counter++;
            }

        }
        System.out.println(counter);

        //If the program is executed with parameters
        if (args.length == 3) {
            //Write result

            try {
                DecimalFormat decimalFormat = new DecimalFormat("#.000");//keep three decimal places

                FileWriter resultWriter = new FileWriter(args[0] + "PositionCheckResult.txt");

                resultWriter.write("Number of data aquisitions on ("+xPosInput+" , "+yPosInput+"): "
                                    + decimalFormat.format(counter));

                resultWriter.close();
            }

            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
    }


}
