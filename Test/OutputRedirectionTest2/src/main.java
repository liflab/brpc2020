import java.io.*;

public class main {
    public static void main(String[] args)
    {
        try{
            String command="python C:\\Users\\marc\\PycharmProjects\\brpc2020\\Test\\OutputRedirectionTest2\\OuputRedirection2.py";
            Process p =Runtime.getRuntime().exec(command);
            BufferedReader read= new BufferedReader(new InputStreamReader(p.getInputStream()));
            String pythonOuput=read.readLine();

            System.out.println(pythonOuput);
        }catch (Exception e){

        }
    }
}
