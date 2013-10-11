package nl.codecentric.cc.piglow;

import java.io.IOException;

public class ShutDownTask extends Thread {

    private PiGlow piGlow;

    public ShutDownTask(PiGlow piGlow) {
        this.piGlow = piGlow;
    }

    @Override
    public void run() {
        try {
            piGlow.shineLights();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
