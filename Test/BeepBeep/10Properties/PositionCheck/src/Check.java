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
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.json.JsonParser;

import java.io.InputStream;
import java.util.Scanner;

public class Check {
    public static void main(String[] args){
        Scanner userInput=new Scanner(System.in);
        System.out.print("Enter the X position to check: ");
        int xPosInput=userInput.nextInt();
        System.out.print("Enter the Y position to check: ");
        int yPosInput=userInput.nextInt();

        InputStream is= Check.class.getResourceAsStream("dictionnary.txt");
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

    }


}
