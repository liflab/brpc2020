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
import ca.uqac.lif.cep.util.Numbers;

import java.io.InputStream;

public class MinimalDistance {
    public static void main(String[] args) {
        InputStream is=MinimalDistance.class.getResourceAsStream("dictionnary.txt");
        ReadLines reader=new ReadLines(is);
        Pullable rp=reader.getPullableOutput();
        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfData=new ApplyFunction(new JPathFunction("data.position and direction.position"));
        Fork dataFork=new Fork(2);
        ApplyFunction jpfX=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("x"))));
        ApplyFunction jpfY=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("y"))));
        Constant constX=new Constant(1);
        Constant constY=new Constant(1);
        Constant square1=new Constant(2);
        Constant square2=new Constant(2);

        ApplyFunction substractY=new ApplyFunction(new FunctionTree(Numbers.subtraction,constY, StreamVariable.X));
        ApplyFunction squareY=new ApplyFunction(new FunctionTree(Numbers.power,StreamVariable.X,square1));
        ApplyFunction substractX=new ApplyFunction(new FunctionTree(Numbers.subtraction,constX, StreamVariable.X));
        ApplyFunction squareX=new ApplyFunction(new FunctionTree(Numbers.power,StreamVariable.X,square2));
        ApplyFunction substractYX=new ApplyFunction(new FunctionTree(Numbers.subtraction,StreamVariable.X, StreamVariable.Y));
        ApplyFunction squareRoot=new ApplyFunction(new FunctionTree(Numbers.squareRoot,StreamVariable.X));


        Connector.connect(reader,parseData);
        Connector.connect(parseData,jpfData);
        Connector.connect(jpfData,dataFork);
        Connector.connect(dataFork,0,jpfX,0);
        Connector.connect(dataFork,1,jpfY,0);
        Connector.connect(jpfY,substractY);
        Connector.connect(jpfX,substractX);
        Connector.connect(substractY,squareY);
        Connector.connect(substractX,squareX);
        Connector.connect(squareY,0,substractYX,1);
        Connector.connect(squareX,0,substractYX,0);
        Connector.connect(substractYX,squareRoot);

        Pullable p=squareRoot.getPullableOutput();

        while(p.hasNext()){
            System.out.println(p.pull());
        }


    }

}
