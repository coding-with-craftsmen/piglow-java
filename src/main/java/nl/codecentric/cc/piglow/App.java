package nl.codecentric.cc.piglow;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    private static final int I2C_ADDR = 0x54;

    private static final int CMD_ENABLE_OUTPUT = 0x00;
    private static final int CMD_ENABLE_LEDS = 0x13;
    private static final int CMD_SET_PWM_VALUES = 0x01;
    private static final int CMD_UPDATE = 0x16;

    private static final byte CMD_ON = 0x01;
    private static final byte CMD_ALL = (byte) 0xff;

    private static final byte[] ALL_ON_LEDS = new byte[]{0x01, 0x02, 0x04, 0x08, 0x10, 0x18, 0x20, 0x30, 0x40, 0x50, 0x60, 0x70, (byte) 0x80, (byte) 0x90, (byte) 0xA0, (byte) 0xC0, (byte) 0xE0, (byte) 0xFF};

    private static final byte[] ALL_OFF_LEDS = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

    public static void main(String[] args) throws IOException, InterruptedException {
        I2CBus bus = I2CFactory.getInstance(0);
        I2CDevice device = bus.getDevice(I2C_ADDR);
        device.write(CMD_ENABLE_OUTPUT, CMD_ON);
        device.write(CMD_ENABLE_LEDS, new byte[]{CMD_ALL, CMD_ALL, CMD_ALL}, 0, 3);
        Scanner keyboard = new Scanner(System.in);
        int index=0;
        System.out.println("enter stop to stop");
        while (!keyboard.nextLine().trim().equalsIgnoreCase("stop")) {
            device.write(CMD_SET_PWM_VALUES, createOnlyLightOnNr(index), 0, 18);
            device.write(CMD_UPDATE, CMD_ALL);
            index=(index+1)%18;
        }
        device.write(CMD_SET_PWM_VALUES, ALL_OFF_LEDS, 0, 18);
        device.write(CMD_UPDATE, CMD_ALL);
    }

    private static byte[] createOnlyLightOnNr(int lightOn){
        byte[] result=new byte[18];
        for (int i=0;i<18;i++){
            if (i==lightOn){
                result[i]=(byte)(2^i);
            } else {
                result[i]=0;
            }
        }
        return result;

    }

}

