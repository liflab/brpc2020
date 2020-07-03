import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.*;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.cep.json.NumberValue;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.util.Numbers;

import java.io.InputStream;
import java.text.DecimalFormat;

public class MaxSpeed {
    

    public static void main(String[] args) {
        InputStream is= MaxSpeed.class.getResourceAsStream("dictionnaries.txt");
        ReadLines reader=new ReadLines(is);

        ApplyFunction parseData=new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfSpeedData=new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("data.engine.speed"))));
        Cumulate max=new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));

        Connector.connect(reader,parseData);
        Connector.connect(parseData,jpfSpeedData);
        Connector.connect(jpfSpeedData,max);

        Pullable maxSpeedP=max.getPullableOutput();
        double maxSpeedValue=0;
        DecimalFormat decimalFormat=new DecimalFormat("#.000");//keep three decimal places

        while(maxSpeedP.hasNext()){
            maxSpeedValue=((Number) maxSpeedP.pull()).doubleValue();
        }

        System.out.println("The maximum speed is: "+decimalFormat.format(maxSpeedValue));
    }




}


