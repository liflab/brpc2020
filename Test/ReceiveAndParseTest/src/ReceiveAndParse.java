
import ca.uqac.lif.cep.json.ParseJson;

import ca.uqac.lif.json.JsonElement;

import java.util.Scanner;

public class ReceiveAndParse {
    public static void main(String[] args) {
        ParseJson parse=ParseJson.instance;
        Object[] out=new Object[1];
        Scanner reader = new Scanner(System.in);


        int time=Integer.parseInt(reader.nextLine());
        int dataRate=Integer.parseInt(reader.nextLine());
        System.out.println(time+" "+dataRate);



        for(int i=0;i<time*dataRate;i++)
        {
            String data=reader.nextLine();
            parse.evaluate(new Object[]{data},out);
            JsonElement j=(JsonElement) out[0];
            System.out.println(j);
            System.out.println();


        }



    }
}
