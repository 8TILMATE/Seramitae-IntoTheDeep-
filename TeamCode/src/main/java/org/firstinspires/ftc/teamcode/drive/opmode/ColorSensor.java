package org.firstinspires.ftc.teamcode.drive.opmode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.SwitchableLight;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorSensor{

    /** The colorSensor field will contain a reference to our color sensor hardware object */
    NormalizedColorSensor colorSensor;

    /** The relativeLayout field is used to aid in providing visual feedback */
    View relativeLayout;

    // Sensor gain
    float gain = 5.0f;
    String CurrentSample="None";

    // Arrays for HSV conversion
    final float[] hsvValues = new float[3];

    // Button press tracking
    boolean xButtonPreviouslyPressed = false;
    boolean xButtonCurrentlyPressed = false;
    public static int status=0;

    public void CSinit(HardwareMap hardwareMap) {
        // Initialize the color sensor
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");

        // Attempt to enable the light if supported
        if (colorSensor instanceof SwitchableLight) {
            ((SwitchableLight) colorSensor).enableLight(true);
        }

        // Get the RelativeLayout for background color changes (visual feedback on Robot Controller)
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

    }


    public void CSUpdate() {
        // Explain gain adjustment information
        colorSensor.setGain(gain);

        // Toggle light with X button

        xButtonPreviouslyPressed = xButtonCurrentlyPressed;

        // Read normalized color values
        NormalizedRGBA colors = colorSensor.getNormalizedColors();

        // Convert RGB to HSV
        Color.colorToHSV(colors.toColor(), hsvValues);


        float value = Math.max(colors.red,colors.green);
        value= Math.max(value,colors.blue);
        if(value>=0.1 && (hsvValues[0]<=170||hsvValues[0]>180)){
            if(colors.red>colors.blue && colors.red>colors.green){
                CurrentSample="Red";
                status=3;
            }
            else if(colors.blue>colors.red && colors.blue>colors.green){
                CurrentSample="Blue";
                status=2;
            }
            else if (colors.green>colors.red && colors.green> colors.blue){
                CurrentSample="Yellow";
                status=1;
            }
        }
        else{
            CurrentSample="None";status=0;
        }

        // If the sensor supports distance measurement, display the distance


        // Change Robot Controller's background color to match the detected color
        relativeLayout.post(() -> relativeLayout.setBackgroundColor(Color.HSVToColor(hsvValues)));
    }

    public int returnSample() {

        return status;
    }
}
