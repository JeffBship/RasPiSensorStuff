package grovebutton;

import org.iot.raspberry.grovepi.*;
import org.iot.raspberry.grovepi.pi4j.*;
import java.io.IOException;

public class testButtonListener{

    public static void main() throws IOException, com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException{
        GrovePi gp = new GrovePi4J();
        ButtonListener b = new ButtonListener(gp, 6);
    }
}
