package nl.codecentric.cc.piglow;

import java.io.IOException;

public class App {


    public static void main(String[] args) throws IOException, InterruptedException {
        final PiGlow piGlow = new PiGlow();

        System.out.println("Press ctrl-c to stop");

        Runtime.getRuntime().addShutdownHook(new ShutDownTask(piGlow));

        int lightNr = 0;
        while (true) {
            Thread.sleep(1000);
            piGlow.shineLights(lightNr);
            lightNr = (lightNr + 1) % 18;
        }
    }

}

