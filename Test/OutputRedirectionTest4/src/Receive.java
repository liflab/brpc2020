import java.util.Scanner;

//To run (if you are in the java program's directory):
// python ProjectDirectory\brpc2020\Source\PythonDataFetcher\main.py 20 10 | java Receive
public class Receive {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);


        int time=Integer.parseInt(reader.nextLine());
        int dataRate=Integer.parseInt(reader.nextLine());


        for(int i=0;i<time*dataRate;i++)
        {
            String dataTime=reader.nextLine();
            String data=reader.nextLine();
            System.out.println(dataTime);
            System.out.println(data);
            System.out.println();
        }

    }






}
