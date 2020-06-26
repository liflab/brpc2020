import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
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
        int xPosToCheck=userInput.nextInt();
        System.out.print("Enter the Y position to check: ");
        int yPosToCheck=userInput.nextInt();

        InputStream is= Check.class.getResourceAsStream("dictionnary.txt");
        ReadLines read=new ReadLines(is);
        Pullable readp=read.getPullableOutput();

        QueueSource xRefPosSource=new QueueSource();
        QueueSource yRefPosSource=new QueueSource();
        QueueSource xPosSource=new QueueSource();
        QueueSource yPosSource=new QueueSource();

        ApplyFunction xPosCheck=new ApplyFunction(new onePosCheck());
        ApplyFunction yPosCheck=new ApplyFunction(new onePosCheck());
        ApplyFunction posCheck=new ApplyFunction(new twoPosCheck());

        Connector.connect(xRefPosSource,0,xPosCheck,0);
        Connector.connect(xPosSource,0,xPosCheck,1);
        Connector.connect(yRefPosSource,0,yPosCheck,0);
        Connector.connect(yPosSource,0,yPosCheck,1);
        Connector.connect(xPosCheck,0,posCheck,0);
        Connector.connect(yPosCheck,0,posCheck,1);

        int trueCounter=0;
        Pullable posCheckPull=posCheck.getPullableOutput();

        while(readp.hasNext()){
            String dictionnary =String.valueOf(readp.pull());
            Object[] out=new Object[1];
            ParseJson.instance.evaluate(new Object[]{dictionnary},out);
            JsonElement j =(JsonElement) out[0];
            JsonMap jMap=(JsonMap) j;

            xPosSource.addEvent(Math.round(( ((Number) getData(getSubDict(getSubDict(getSubDict(jMap,"data"),"position and direction"),"position"),"x")).floatValue())));
            yPosSource.addEvent(Math.round(( ((Number) getData(getSubDict(getSubDict(getSubDict(jMap,"data"),"position and direction"),"position"),"y")).floatValue())));
            xRefPosSource.addEvent(xPosToCheck);
            yRefPosSource.addEvent(yPosToCheck);

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
