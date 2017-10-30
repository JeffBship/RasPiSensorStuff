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
     * Provides a method to stop the listener.  
     */
    public void stopListener(){
        exec.shutdown();
    }
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
    // Need to adjust the visibility for these before final release
    public long eventTime,pressNew,releaseNew,pressOld,releaseOld = Long.MAX_VALUE;
        
    public void reset(){
        eventTime  = Long.MAX_VALUE;
        pressNew   = 0; 
        releaseNew = 0; 
        pressOld   = 0; 
        releaseOld = 0; 
    }
    
    
    @Override
    public void onChange(boolean oldValue, boolean newValue) {
        //In the listener, just update the time values when there is a press or release. 
        eventTime = System.currentTimeMillis();  ;
        if (oldValue){  //if button was just released
            button.setLastReleaseTime(eventTime);
            releaseOld = releaseNew;
            releaseNew = eventTime;
        }else{          //if button was just pressed down
            button.setLastPressTime(eventTime);
            pressOld   = pressNew;
            pressNew   = eventTime;
            releaseOld = releaseNew;
            releaseNew = 0;
            
        };;     
    }
}