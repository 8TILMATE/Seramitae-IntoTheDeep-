package org.firstinspires.ftc.teamcode.drive.opmode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.opencv.core.Mat;

public class IntakeClaw {
    Servo PivotLeft,PivotRight,Wrist,Claw;
    boolean WaitForPickUp=false,WaitForReturn=false;
    public static boolean hovering=false;
    ColorSensor colorSensor = new ColorSensor();
    public static double currentIntakePos=0.73;
    public static double LeftExtended=0.65,RightExtended=0.35,indexExtension=0.030,leftWristDown=0.03,rightWristDown=0.97,leftWristUp=0.93,rightWristUp=0.07,rightRetracted=0.02,leftRetracted=0.98,hoverOverRight=0.85,hoverOverLeft=0.15,openClaw=0.7,closedClaw=0.5,defaultWrist=0.73,lowBasket=1200,highBasket=3800,highBar=700,transfer=1,outtake=0,closed,basketPos=.2,slamDown,specimenDeposit=0.3,pickedUpLeft=0.2,pickedUpRight=0.8;

    ElapsedTime time= new ElapsedTime();
    double openClaw1=0;
    public class IntakeDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            PivotRight.setPosition(rightWristDown);
            PivotLeft.setPosition(leftWristDown);
            return false;
        }

    }

    public Action intakeDown() {
        return new IntakeDown();
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Claw.setPosition(openClaw);
            return false;
        }

    }

    public Action openClaw() {
        return new OpenClaw();
    }
    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Claw.setPosition(closedClaw);
            return false;
        }

    }

    public Action closedClaw() {
        return new CloseClaw();
    }
    public class BackToTransfer implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            PivotRight.setPosition(rightWristUp);
            PivotLeft.setPosition(leftWristUp);
            return false;
        }

    }

    public Action backToTransfer() {
        return new BackToTransfer();
    }

    public class wristToWaitForTransfer implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            PivotRight.setPosition(0.7);
            return false;
        }

    }

    public void IntakeInit(HardwareMap hardwareMap,double defaultWrist)
    {
        PivotLeft = hardwareMap.get(Servo.class,"PivotLeft");
        PivotRight = hardwareMap.get(Servo.class,"PivotRight");
        Wrist = hardwareMap.get(Servo.class,"Wrist");
        Claw = hardwareMap.get(Servo.class,"Claw");
        colorSensor.CSinit(hardwareMap);
        Wrist.setPosition(defaultWrist);
        currentIntakePos=defaultWrist;
        //Left.setPosition(1);
        // Right.setPosition(0);

    }
    public void IntakeUpdate(Gamepad gamepad,double leftWristDown,double leftWristUp,double rightWristDown,double rightWristUp,double hoverOverRight,double hoverOverLeft,double closedClaw,double openClaw,double defaultWRist,boolean playingBlue,boolean forceOpenIntake,double pickedUpPosLeft,double pickedUpRight){
        if(gamepad.a){
            PivotLeft.setPosition(hoverOverLeft);
            PivotRight.setPosition(hoverOverRight);
            Claw.setPosition(openClaw);
        }
        if(gamepad.dpad_down){
            currentIntakePos=.4;
            Wrist.setPosition(currentIntakePos);
        }
        if(gamepad.dpad_up){
            currentIntakePos=defaultWRist;
            Wrist.setPosition(currentIntakePos);
        }if(gamepad.dpad_left){
            currentIntakePos=0.5;
            Wrist.setPosition(currentIntakePos);
        }
        if(gamepad.dpad_right){
            currentIntakePos=0.96;
            Wrist.setPosition(currentIntakePos);
        }
        if(gamepad.x){
            PivotLeft.setPosition(leftWristDown);
            PivotRight.setPosition(rightWristDown);
            Claw.setPosition(closedClaw);
            WaitForPickUp=true;
        }
        if(WaitForPickUp){
            time.reset();
            WaitForPickUp=false;
        }
        /*
        if(gamepad.dpad_left){
            currentIntakePos = Math.max(0,currentIntakePos-0.015);
            Wrist.setPosition(currentIntakePos);
        }
        if(gamepad.dpad_right){

            currentIntakePos= Math.min(1,currentIntakePos+0.015);
            Wrist.setPosition(currentIntakePos);
        }
        */

        if(time.milliseconds()>=400&&time.milliseconds()<=650){
            PivotLeft.setPosition(pickedUpPosLeft);
            PivotRight.setPosition(pickedUpRight);
            hovering=true;
        }
        if(hovering){
            colorSensor.CSUpdate();
            if(colorSensor.status==3&&playingBlue){
                Claw.setPosition(openClaw);
            }
            if(colorSensor.status==2&&!playingBlue){
                Claw.setPosition(openClaw);
            }
           /* if(colorSensor.status==0){
                Claw.setPosition(openClaw);
            }*/

        }
        if(gamepad.b){
            PivotLeft.setPosition(leftWristUp);
            PivotRight.setPosition(rightWristUp);
            Wrist.setPosition(defaultWRist);
            hovering=false;
        }
        if(forceOpenIntake==true){
            OpenClaw();
        }

    }
    public void OpenClaw(){
        Claw.setPosition(0.7);
    }
}
