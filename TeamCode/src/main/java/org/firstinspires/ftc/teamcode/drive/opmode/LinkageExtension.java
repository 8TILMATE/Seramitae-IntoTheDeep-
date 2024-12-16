package org.firstinspires.ftc.teamcode.drive.opmode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class LinkageExtension {
    public static double LeftExtended=0.53,RightExtended=0.47,indexExtension=0.030,leftWristDown=0.03,rightWristDown=0.97,leftWristUp=0.93,rightWristUp=0.07,rightRetracted=0.02,leftRetracted=0.98,hoverOverRight=0.85,hoverOverLeft=0.15,openClaw=0.7,closedClaw=0.5,defaultWrist=0.73,lowBasket=1200,highBasket=3800,highBar=700,transfer=1,outtake=0,closed,basketPos=.2,slamDown,specimenDeposit=0.3,pickedUpLeft=0.2,pickedUpRight=0.8;

    public class Extend implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Left.setPosition(LeftExtended);
            Right.setPosition(RightExtended);
            return false;
        }

    }

    public Action extend() {
        return new Extend();
    }
    public class Deextend implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Left.setPosition(leftRetracted);
            Right.setPosition(rightRetracted);
            return false;
        }

    }

    public Action deextend() {
        return new Deextend();
    }
    Servo Left,Right;
    private double currentPosLeft,currentPosRight;
    public void InitExtension(HardwareMap hardwareMap)
    {
        Left = hardwareMap.get(Servo.class,"Left");
        Right = hardwareMap.get(Servo.class,"Right");
        Left.setPosition(leftRetracted);
        Right.setPosition(rightRetracted);
        currentPosLeft=leftRetracted;
        currentPosRight=rightRetracted;
        //Left.setPosition(1);
        // Right.setPosition(0);
    }
    public void ExtensionUpdate(Gamepad gamepad,double leftExtended,double rightExtended,double indexExtension,double retractLeft,double retractRigh){

        if(gamepad.a){

            Left.setPosition(currentPosLeft);
            Right.setPosition(currentPosRight);
        }
        if(gamepad.b){
            currentPosRight = retractRigh;
            currentPosLeft= retractLeft;
            Right.setPosition(currentPosRight);
            Left.setPosition(currentPosLeft);
            currentPosRight=rightExtended;
            currentPosLeft=leftExtended;
        }
        if(gamepad.left_bumper){
            if(currentPosLeft+indexExtension<=0.98){
                currentPosLeft+=indexExtension;
                currentPosRight-=indexExtension;
                Right.setPosition(currentPosRight);
                Left.setPosition(currentPosLeft);
            }
        }
        if(gamepad.right_bumper){
            if(currentPosLeft-indexExtension>=0.53){
                currentPosLeft-=indexExtension;
                currentPosRight+=indexExtension;
                Right.setPosition(currentPosRight);
                Left.setPosition(currentPosLeft);
            }
        }
    }
    public float RightPos(){
        return (float) Right.getPosition();
    }
}
