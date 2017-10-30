package grovebutton;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.iot.raspberry.grovepi.*;
import java.io.IOException;

/**
 * class ButtonListener makes intermittent reads to a GrovePi button sensor to determine when it is pressed
 * 
 * JL: still not finished, cannot distinguish between single and double press.  I forsee this 
 * class and the GroveButton class getting refactored as implementation for other sensors are created.
 */

public class ButtonListener implements GroveDigitalInListener
{
    /**
     * exec   responsible for making intermittent reads of the button sensor
     */
    private final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
    /**
     * button   the object that an instance of this class is listening to
     */
    private GroveButton button;
    
    
    /**
     * instantiates a new ButtonListener object and the GroveButton object it listens to
     * 
     * @param grovePi   
     * @param pin    number of the port that button is plugged into
     */
    public ButtonListener(GrovePi grovePi, int pin) throws IOException{
        button = new GroveButton(grovePi, pin);
        button.setListener(this);
        int timeInterval = 75; //number of milliseconds between button reads
        exec.scheduleAtFixedRate(button, 0, timeInterval, TimeUnit.MILLISECONDS);
    }
    
    /**
     * invoked when ScheduledExecutorService detects a change in button status
     * determines the type of press (single, double, long) then invokes the 
     * method associated with that type of button press
     * 
     * @param oldValue    the old status of the button
     * @param newvalue    the new (and current) status of the button
     */
    @Override
    public void onChange(boolean oldValue, boolean newValue) {
        long currentTime = System.currentTimeMillis();  
        long lastPress = button.getLastPressTime();
        long lastRelease = button.getLastReleaseTime();
        
        if (oldValue){ //if button was just released
            if (currentTime - lastPress > 5000){ //5 second press ends it
                exec.shutdown();
            }
            else if (currentTime - lastPress > button.LONG_PRESS_INTERVAL){
                System.out.println("Long Press Detected");
            }else if (currentTime - lastRelease < button.DOUBLE_PRESS_INTERVAL){
                System.out.println("Double Press Detected");
            }else{
                System.out.println("Single Press Detected");
            }
            button.setLastReleaseTime(currentTime);
        }else{//if button just pressed down
            button.setLastPressTime(currentTime);
        }         
    }
}