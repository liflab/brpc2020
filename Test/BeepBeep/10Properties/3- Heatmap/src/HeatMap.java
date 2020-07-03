import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.functions.StreamVariable;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.NumberValue;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.mtnp.UpdateTable;
import ca.uqac.lif.cep.mtnp.UpdateTableStream;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;

import java.io.InputStream;
import java.io.ObjectStreamConstants;

public class HeatMap {


    public static void main(String[] args) {

        //To read the file
        InputStream is= HeatMap.class.getResourceAsStream("static.txt");
        ReadLines read=new ReadLines(is);
        Pullable readp=read.getPullableOutput();


        Constant divider= new Constant(100);
        UpdateTable table= new UpdateTableStream("x", "y");


        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfData=new ApplyFunction(new JPathFunction("data.position and direction.position"));
        Fork datafork=new Fork(2);
        ApplyFunction jpfX=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("x"))));
        ApplyFunction jpfY=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("y"))));

        FunctionTree divideBy100=new FunctionTree(Numbers.division, StreamVariable.X, divider);
        ApplyFunction divideX=new ApplyFunction(divideBy100);
        ApplyFunction divideY=new ApplyFunction(divideBy100);
        ApplyFunction floorX=new ApplyFunction(new Floor());
        ApplyFunction floorY=new ApplyFunction(new Floor());



        Connector.connect(read,parseData);
        Connector.connect(parseData,jpfData);
        Connector.connect(jpfData,datafork);
        Connector.connect(datafork,0,jpfX,0);
        Connector.connect(datafork,1,jpfY,0);
        Connector.connect(jpfX,divideX);
        Connector.connect(jpfY,divideY);
        Connector.connect(divideX,floorX);
        Connector.connect(divideY,floorY);
        Connector.connect(floorX,0,table,0);
        Connector.connect(floorY,0,table,1);


        Pullable x=table.getPullableOutput();


        System.out.println(x.pull());







    }



}
