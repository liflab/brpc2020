import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;

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
        Pullable readp=read.getPullableOutput();


        QueueSource xPosSource=new QueueSource();
        QueueSource yPosSource=new QueueSource();
        Constant xPosCheck=new Constant(xPosInput);
        Constant yPosCheck=new Constant(yPosInput);


        FunctionTree positionCheck=new FunctionTree(new PositionCheck(),xPosCheck,yPosCheck,StreamVariable.X,StreamVariable.Y);


        ApplyFunction positionCheckAF=new ApplyFunction(positionCheck);


        Connector.connect(xPosSource,0,positionCheckAF,0);
        Connector.connect(yPosSource,0,positionCheckAF,1);


        int trueCounter=0;
        Pullable posCheckPull=positionCheckAF.getPullableOutput();

        while(readp.hasNext()){
            String dictionnary =String.valueOf(readp.pull());
            Object[] out=new Object[1];
            ParseJson.instance.evaluate(new Object[]{dictionnary},out);
            JsonElement j =(JsonElement) out[0];
            JsonMap jMap=(JsonMap) j;

            xPosSource.addEvent(Math.round(( ((Number) getData(getSubDict(getSubDict(getSubDict(jMap,"data"),"position and direction"),"position"),"x")).floatValue())));
            yPosSource.addEvent(Math.round(( ((Number) getData(getSubDict(getSubDict(getSubDict(jMap,"data"),"position and direction"),"position"),"y")).floatValue())));


            if((Boolean) posCheckPull.pull()==true)
            {
                trueCounter++;
            }

        }
        System.out.println(trueCounter);

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
