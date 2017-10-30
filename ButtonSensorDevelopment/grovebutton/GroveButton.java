package grovebutton;

import org.iot.raspberry.grovepi.*;
import java.io.IOException;

/**
 * GroveButton class has extra fields (apart from those inherited from class GroveDigitalIn)
 * to facilitate distinguishing between types of button 
 * presses: single, double, long button presses
 */
public class GroveButton extends GroveDigitalIn
{
    /**
     * the time in milliseconds of the last time the button was pressed down
     */
    private long lastPressTime;  
    /**
     * the time in milliseconds of the last time the button was released
     */
    private long lastReleaseTime;
    /**
     * the length of time in milleseconds to define a "long press"
     */
    public final int LONG_PRESS_INTERVAL = 1000;   //milliseconds
    /**
     * the length of time in milliseconds separating two consecutive button presses that constitutes a "double press"
     */
    public final int DOUBLE_PRESS_INTERVAL = 1000;
    
    /**
     * @param grovePi  the GrovePi device to which this GroveButton sensor is plugged into
     * @param pin   the port number this GroveButton sensor is plugged into
     */
    public GroveButton(GrovePi grovePi, int pin) throws IOException{
        super(grovePi, pin);
    }

    public long getLastPressTime() {return lastPressTime;}
    public void setLastPressTime(long time) {lastPressTime = time;}
    public long getLastReleaseTime() {return lastReleaseTime;}
    public void setLastReleaseTime(long time) {lastReleaseTime = time;}
    

}
