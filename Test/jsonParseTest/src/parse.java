import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.json.JsonElement;

import java.util.Scanner;

public class parse {
    public static void main(String[] args) {
        ParseJson parse = ParseJson.instance;
        Object[] out = new Object[1];
        Scanner scan=new Scanner(System.in);
        String patate="{\"a\" : 123, \"b\" : false, \"c\" : [4,5,6]}";
        parse.evaluate(new Object[]{
                patate}, out);
        JsonElement j = (JsonElement) out[0];
        System.out.println(j);
        parse.evaluate(new Object[]{
                "{\"a\" : "}, out);
        System.out.println(out[0].getClass());

    }
}
