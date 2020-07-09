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
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import javafx.scene.shape.Arc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.Scanner;



public class SteeringAngle {

    public static void main(String[] args)
    {
        SteeringAngle();
    }

    public static void SteeringAngle()
    {
        InputStream is=SteeringAngle.class.getResourceAsStream("dictionnary2.txt");
        ReadLines reader=new ReadLines(is);

        ApplyFunction parseData = new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfSteering = new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("data.position and direction.direction.steering"))));

        Connector.connect(reader, parseData);
        Connector.connect(parseData, jpfSteering);

        Pullable pSteering = jpfSteering.getPullableOutput();

        while (pSteering.hasNext()) {
            System.out.println(pSteering.pull());
        }
    }

}
