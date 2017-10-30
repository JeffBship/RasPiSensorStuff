package org.iot.raspberry.examples;

import org.iot.raspberry.grovepi.GroveDigitalIn;
import org.iot.raspberry.grovepi.GroveDigitalOut;
import org.iot.raspberry.grovepi.GrovePi;
/*
 Connect: Led to D4 and Button to D6
 */

public class ButtonLed {//implements Example {

  //@Override
  //public void run(GrovePi grovePi, Monitor monitor) throws Exception {
  public static void main() throws Exception{
    GrovePi grovePi = new GrovePi();
    GroveDigitalIn button = grovePi.getDigitalIn(6);
    //GroveDigitalOut led = grovePi.getDigitalOut(4);
    while (monitor.isRunning()) {
      try {
        //led.set(button.get());
        if(button.get()){
            System.out.println("button pressed");
            Thread.sleep(100);
        }
      } catch (Exception e) {
      }
    }
    //led.set(false);
  }

}
