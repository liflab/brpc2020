import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.NumberValue;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class MinimalDistance {
    public static void main(String[] args) {

        InputStream is=MinimalDistance.class.getResourceAsStream("dictionnary.txt");
        ReadLines reader=new ReadLines(is);

        QueueSource segmentsSource=new QueueSource().setEvents(new Segment(new Point(1.0, 1.0),new Point(1.0,3.0)),new Segment(1.0,3.0,1.0,5.0));
        QueueSource segmentsSourceCopy=segmentsSource;
        Pullable pullSegments=segmentsSourceCopy.getPullableOutput();

       ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfPosition=new ApplyFunction(new JPathFunction("data.position and direction.position"));
        Fork positionFork=new Fork(2);
        ApplyFunction jpfX=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("x"))));

        ApplyFunction jpfY=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("y"))));

        Constant segmentConst=new Constant(pullSegments);
        FunctionTree evaluateMinDistFT=new FunctionTree(new evaluateDistance(), StreamVariable.X,StreamVariable.Y,segmentConst);
        ApplyFunction evaluateMinDist=new ApplyFunction(evaluateMinDistFT);
        Cumulate sum=new Cumulate(new CumulativeFunction<Number>(Numbers.addition));

        Connector.connect(reader,parseData);
        Connector.connect(parseData,jpfPosition);
        Connector.connect(jpfPosition,positionFork);
        Connector.connect(positionFork,0,jpfX,0);// x Position
        Connector.connect(positionFork,1,jpfY,0);// y Position
        Connector.connect(jpfX,0,evaluateMinDist,0);
        Connector.connect(jpfY,0,evaluateMinDist,1);
        Connector.connect(evaluateMinDist,sum);

        Pullable sumP=sum.getPullableOutput();

        while(sumP.hasNext()){

            System.out.println(sumP.pull());
            System.out.println();
            segmentsSourceCopy=segmentsSource;//If it's stupid and it's work, it's not stupid.
        }










    }
}
