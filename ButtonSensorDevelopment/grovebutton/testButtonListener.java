package grovebutton;

import org.iot.raspberry.grovepi.*;
import org.iot.raspberry.grovepi.pi4j.*;
import java.io.IOException;
import java.time.LocalTime;

public class testButtonListener

{
    
    private static boolean isDown = false;
    private static boolean keepListening = true;
    private static long currentTime = Long.MAX_VALUE;
    
    

    public static void main() throws IOException, java.lang.InterruptedException,
                                com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException{
        GrovePi gp = new GrovePi4J();
        ButtonListener b = new ButtonListener(gp, 4);
        
        //A continuous loop that calculates the press actions
        //until 2 long Presses in a row are detected.
        System.out.println("Two long presses in a row to exit.");
        boolean lastWasLong = false;
        reset(b); //initializes time values
        while (keepListening) {
            Thread.sleep(100);
            currentTime = System.currentTimeMillis();
            LocalTime localTime = LocalTime.now();
            /*
            System.out.println("###################################################");
            System.out.println("currentTime: " + currentTime);
            System.out.println("pNew "+ b.pressNew + "  rNew " + b.releaseNew);
            System.out.println("pOld "+ b.pressOld + "  rOld " + b.releaseOld + "\n") ;
            System.out.println("button isDown: " + isDown);              
            System.out.println("Interval is " + (currentTime-b.releaseNew));
            */
            if (b.pressNew > 0) {
                //only check presses if there has been a press since the last reset
                if ( currentTime - b.pressNew > GroveButton.LONG_PRESS_INTERVAL)  {
                        System.out.println("Long Press Detected at " + localTime);
                        reset(b);
                        if (lastWasLong) keepListening=false;
                        lastWasLong = true;
                }else if (b.pressNew-b.pressOld < GroveButton.DOUBLE_PRESS_INTERVAL){
                        System.out.println("Double Press Detected at " + localTime);
                        reset(b);
                        lastWasLong = false;
                }else if (b.releaseNew>0 && currentTime-b.releaseNew > GroveButton.DOUBLE_PRESS_INTERVAL) {
                        System.out.println("Single Press Detected at " + localTime);
                        reset(b); 
                        lastWasLong = false;
                }
            }
            
        }
        b.stopListener();
        System.out.println("End of Program.");
    }
    
    
    private static void reset(ButtonListener b){
        currentTime = Long.MAX_VALUE;
        b.reset();
    }
    
}
      
