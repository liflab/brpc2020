import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.io.Print;
import ca.uqac.lif.cep.io.ReadLines;
import ca.uqac.lif.cep.io.ReadStringStream;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.util.Numbers;

import java.io.InputStream;

public class Read {
    public static void main(String[] args) {
        InputStream is =Read.class.getResourceAsStream("text.txt");
        ReadLines reader = new ReadLines(is);
        ApplyFunction cast = new ApplyFunction(Numbers.numberCast);
        Connector.connect(reader, cast);
        Pullable p = cast.getPullableOutput();
        while (p.hasNext())
        {
            Number n = (Number) p.next();
            System.out.println(n + "," + n.getClass().getSimpleName());

        }

    }
}
