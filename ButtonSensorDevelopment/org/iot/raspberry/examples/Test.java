package org.iot.raspberry.examples;

import org.iot.raspberry.grovepi.pi4j.*;
import java.io.IOException;

public class Test
{
  public static void main() throws IOException, Exception{
    ButtonListener b = new ButtonListener();  
    GrovePi4J pi = new GrovePi4J();
    Monitor m = new Monitor();
    b.run(pi, m);
  }
}
