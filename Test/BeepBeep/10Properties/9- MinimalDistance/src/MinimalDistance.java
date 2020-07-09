import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Constant;
import ca.uqac.lif.cep.functions.FunctionTree;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.NumberValue;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.QueueSource;

import java.io.InputStream;

public class MinimalDistance {
    public static void main(String[] args) {

        InputStream is=MinimalDistance.class.getResourceAsStream("dictionnary.txt");
        ReadLines reader=new ReadLines(is);

        QueueSource segments=new QueueSource().setEvents(new Segment(new Point(1.0, 1.0),new Point(3.0,3.0)),new Segment(3.0,3.0,3.0,5.0));

        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfPosition=new ApplyFunction(new JPathFunction("data.position and direction.position"));
        Fork positionFork=new Fork(2);
        ApplyFunction jpfX=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("x"))));
        Fork xFork=new Fork(3);
        ApplyFunction jpfY=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("y"))));
        Fork yFork=new Fork(3);


        Connector.connect(reader,parseData);
        Connector.connect(parseData,jpfPosition);
        Connector.connect(jpfPosition,positionFork);
        Connector.connect(positionFork,0,jpfX,0);// x Position
        Connector.connect(jpfX,xFork);
        Connector.connect(positionFork,1,jpfY,0);// y Position
        Connector.connect(jpfY,yFork);




    }
}
