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
import ca.uqac.lif.cep.util.Strings;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Path;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.Scanner;






public class MaxAcceleration {

    public static double getSpeedX(double speed, double angle) {

        double speedx = speed * Math.cos(angle);
        return speedx;
    }

    public static double getSpeedY(double speed, double angle) {

        double speedy = speed * Math.sin(angle);
        return speedy;
    }

    public static double convertTime(String timeString) {
        timeString = timeString.replaceAll("\"", "");
        String[] tokens = timeString.split(":");
        String[] secondsTokens = tokens[2].split("\\.");
        int Ms = Integer.parseInt(secondsTokens[1]);
        Ms = Ms / 1000;
        int secondsToMs = Integer.parseInt(secondsTokens[0]) * 1000;
        int minutesToMs = Integer.parseInt(tokens[1]) * 60000;
        int hoursToMs = Integer.parseInt(tokens[0]) * 3600000;
        long totalMs = secondsToMs + minutesToMs + hoursToMs + Ms;
        return totalMs;
    }

    public static void main(String[] args) {
        InputStream is = MaxAcceleration.class.getResourceAsStream("data.txt");
        ReadLines reader = new ReadLines(is);


        ApplyFunction parseData = new ApplyFunction(ParseJson.instance);
        ApplyFunction jpfData = new ApplyFunction(new JPathFunction("data"));
        ApplyFunction jpfTime = new ApplyFunction(new JPathFunction("time"));
        ApplyFunction jpfSpeed = new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("engine.speed"))));
        ApplyFunction jpfDir = new ApplyFunction(new JPathFunction("position and direction.direction"));
        ApplyFunction jpfDirX = new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("x"))));
        ApplyFunction jpfDirY = new ApplyFunction((new FunctionTree(NumberValue.instance, new JPathFunction("y"))));
        Fork dictFork = new Fork(2);
        Fork dataFork = new Fork(2);
        Fork speedFork = new Fork(2);
        Fork dirFork = new Fork(2);
        Fork dirXFork = new Fork(2);
        Fork dirYFork = new Fork(2);

        ApplyFunction arctan = new ApplyFunction(new ArcTangent());

        QueueSource accX = new QueueSource();
        QueueSource accY = new QueueSource();
        Cumulate maxX = new Cumulate(new CumulativeFunction<Number>(Numbers.maximum)); // fonction cumulative qui retourne le max
        Cumulate maxY = new Cumulate(new CumulativeFunction<Number>(Numbers.maximum));

        Object maxAccelerationX = 0;
        Object maxAccelerationY = 0;


        Connector.connect(reader, parseData);
        Connector.connect(parseData, dictFork);

        // Time Fork
        Connector.connect(dictFork, 0, jpfTime, 0);
        // Data Fork
        Connector.connect(dictFork, 1, jpfData, 0);
        Connector.connect(jpfData, dataFork);

        // Angle
        Connector.connect(dataFork, 0, jpfDir, 0);
        Connector.connect(jpfDir, dirFork);
        Connector.connect(dirFork, 0, jpfDirX, 0);
        Connector.connect(dirFork, 1, jpfDirY, 0);
        Connector.connect(jpfDirX, dirXFork);
        Connector.connect(jpfDirY, dirYFork);
        Connector.connect(dirXFork, 0, arctan, 0);
        Connector.connect(dirYFork, 0, arctan, 1);

        // Speed
        Connector.connect(dataFork, 1, jpfSpeed, 0);
        Connector.connect(jpfSpeed, speedFork);


        // Max Acceleration
        Connector.connect(accX, maxX);
        Connector.connect(accY, maxY);

        // Variables

        Number angle0, angle1;
        Number speed0, speed1;
        Number speedx0, speedx1;
        Number speedy0, speedy1;
        Number time0, time1;

        // Pullables
        Pullable pSpeed = jpfSpeed.getPullableOutput();
        Pullable pAngle = arctan.getPullableOutput();
        Pullable pTime = jpfTime.getPullableOutput();
        Pullable pX = maxX.getPullableOutput();
        Pullable pY = maxY.getPullableOutput();

        angle0 = ((Number) pAngle.pull()).doubleValue();
        speed0 = ((Number) pSpeed.pull()).doubleValue();
        time0 = convertTime(pTime.pull().toString());

        speedx0 = getSpeedX(speed0.doubleValue(), angle0.doubleValue());
        speedy0 = getSpeedY(speed0.doubleValue(), angle0.doubleValue());

        while (pSpeed.hasNext()) {
            angle1 = ((Number) pAngle.pull()).doubleValue();
            speed1 = ((Number) pSpeed.pull()).doubleValue();

            // Calculations

            speedx1 = getSpeedX(speed1.doubleValue(), angle1.doubleValue());
            speedy1 = getSpeedY(speed1.doubleValue(), angle1.doubleValue());
            speedx1 = Math.abs(speedx1.doubleValue());
            speedy1 = Math.abs(speedy1.doubleValue());
            time1 = convertTime(pTime.pull().toString());
            double deltaTime = (time1.doubleValue() - time0.doubleValue()) / 1000;
            double deltaSpeedX = speedx1.doubleValue() - speedx0.doubleValue();
            double deltaSpeedY = speedy1.doubleValue() - speedy0.doubleValue();
            double accelerationX = deltaSpeedX / deltaTime;
            double accelerationY = deltaSpeedY / deltaTime;
            accelerationX = Math.abs(accelerationX);
            accelerationY = Math.abs(accelerationY);
            accX.addEvent(accelerationX);
            accY.addEvent(accelerationY);
            maxAccelerationX = pX.pull();
            maxAccelerationY = pY.pull();

            time0 = time1;
            speedx0 = speedx1;
            speedy0 = speedy1;
        }

        System.out.println("Acceleration X: " + maxAccelerationX);
        System.out.println("Acceleration Y: " + maxAccelerationY);

        if (args.length == 1) {
            //Write result

            try {
                DecimalFormat decimalFormat = new DecimalFormat("#.000");//keep three decimal places

                FileWriter resultWriter = new FileWriter(args[0] + "MaxAccelerationResult.txt");

                resultWriter.write("Maximum X acceleration: " + decimalFormat.format(maxAccelerationX)
                                    +"\n"+"Maximum Y acceleration: "+decimalFormat.format(maxAccelerationY));

                resultWriter.close();
            }

            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }


    }
}
