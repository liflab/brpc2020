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
        System.out.print("Enter the min speed to consider vehicle stopped");
        int minStopSpeed=userInput.nextInt();

        InputStream is= Check.class.getResourceAsStream("dictionnary.txt");
        ReadLines read=new ReadLines(is);
        Pullable readp=read.getPullableOutput();

        QueueSource speed= new QueueSource();
        QueueSource xPosSource=new QueueSource();
        QueueSource yPosSource=new QueueSource();
        Constant xPosCheck=new Constant(xPosInput);
        Constant yPosCheck=new Constant(yPosInput);
        Constant minStopCheck=new Constant(minStopSpeed);

        FunctionTree checkX=new FunctionTree(new onePosCheck(),xPosCheck, StreamVariable.X);
        FunctionTree checkY=new FunctionTree(new onePosCheck(),yPosCheck,StreamVariable.X);
        FunctionTree posCheck=new FunctionTree(new twoPosCheck(),StreamVariable.X,StreamVariable.Y);

        FunctionTree checkSpeed=new FunctionTree(new stopCheck(),minStopCheck,StreamVariable.X); // stopcheck

        ApplyFunction xPosCheckAF=new ApplyFunction(checkX);
        ApplyFunction yPosCheckAF=new ApplyFunction(checkY);
        ApplyFunction posCheckAF=new ApplyFunction(posCheck);

        ApplyFunction checkSpeedAF= new ApplyFunction(checkSpeed); // stopcheck

        Connector.connect(xPosSource,xPosCheckAF);
        Connector.connect(yPosSource,yPosCheckAF);
        Connector.connect(xPosCheckAF,0,posCheckAF,0);
        Connector.connect(yPosCheckAF,0,posCheckAF,1);

        Connector.connect(speed,checkSpeedAF); // stopcheck

        int trueCounter=0;
        Pullable posCheckPull= posCheckAF.getPullableOutput();
        Pullable checkSpeedPull= checkSpeedAF.getPullableOutput();

        while(readp.hasNext()){
            String dictionnary =String.valueOf(readp.pull());
            Object[] out=new Object[1];
            ParseJson.instance.evaluate(new Object[]{dictionnary},out);
            JsonElement j =(JsonElement) out[0];
            JsonMap jMap=(JsonMap) j;
            speed.addEvent(getSpeed(jMap));
            xPosSource.addEvent(Math.round(( ((Number) getData(getSubDict(getSubDict(getSubDict(jMap,"data"),"position and direction"),"position"),"x")).floatValue())));
            yPosSource.addEvent(Math.round(( ((Number) getData(getSubDict(getSubDict(getSubDict(jMap,"data"),"position and direction"),"position"),"y")).floatValue())));

            if((Boolean) posCheckPull.pull()==true && (Boolean) checkSpeedPull.pull()==true)
            {
                trueCounter++;
            }

        }
        System.out.println(trueCounter);

    }


    public static double getSpeed(JsonMap dict) {
        JsonMap subDict = getSubDict(dict, "data");
        subDict = getSubDict(subDict, "engine");
        Number speedNumber = getDataNumber(subDict, "speed");
        double speed = (Double) speedNumber;
        speed = Math.round(speed * 1000.0) / 1000.0;
        return speed;
    }

    public static Number getDataNumber(JsonMap dict, String elementName) {
        Number data = dict.getNumber(elementName);
        return data;
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
