package nl.codecentric.cc.piglow;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

public class PiGlow {

    private static final int I2C_ADDR = 0x54;
    //newer raspberry pi use bus nr 1
    private static final int I2C_BUS_NR = 0;

    private static final int CMD_ENABLE_OUTPUT = 0x00;
    private static final int CMD_ENABLE_LEDS = 0x13;
    private static final int CMD_SET_PWM_VALUES = 0x01;
    private static final int CMD_UPDATE = 0x16;

    private static final byte CMD_ON = 0x01;
    private static final byte CMD_ALL = (byte) 0xff;

    private final I2CDevice device;

    public PiGlow() {
        device = initializeDevice();
    }

    private I2CDevice initializeDevice() {
        I2CDevice device = null;
        try {
            I2CBus bus = I2CFactory.getInstance(I2C_BUS_NR);
            device = bus.getDevice(I2C_ADDR);
            device.write(CMD_ENABLE_OUTPUT, CMD_ON);
            device.write(CMD_ENABLE_LEDS, new byte[]{CMD_ALL, CMD_ALL, CMD_ALL}, 0, 3);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return device;
    }

    public void shineLights(int... lightnrs) throws IOException{
        device.write(CMD_SET_PWM_VALUES, lightOnArray(lightnrs), 0, 18);
        device.write(CMD_UPDATE, CMD_ALL);
    }

    private static byte[] lightOnArray(int... lightnrs) {
        byte[] result = new byte[18];
        for (int nr : lightnrs) {
            result[nr] = (byte) (2 ^ nr);
        }
        return result;

    }


}
