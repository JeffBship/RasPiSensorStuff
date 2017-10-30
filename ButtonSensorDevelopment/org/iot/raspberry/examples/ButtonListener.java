package org.iot.raspberry.examples;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.iot.raspberry.grovepi.GroveDigitalIn;
import org.iot.raspberry.grovepi.GrovePi;
/*
 Connect: Button to D6
 */

public class ButtonListener implements Example {
  int count = 0;
  @Override
  public void run(GrovePi grovePi, Monitor monitor) throws Exception {
    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();

    GroveDigitalIn button = grovePi.getDigitalIn(6);
    button.setListener((boolean oldValue, boolean newValue) -> {
      //System.out.println("Button " + oldValue + "->" + newValue);
      if (oldValue == false){//if was not pressed and now is
        count++;
        System.out.println("press count: " + count);
      }
    });
    int timeInterval = 75;
    exec.scheduleAtFixedRate(button, 0, timeInterval, TimeUnit.MILLISECONDS);
    monitor.waitForStop();
    exec.shutdown();
  }

}
