package org.firstinspires.ftc.teamcode.drive.opmode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class OuttakeClaw {

    public static double LeftExtended=0.65,RightExtended=0.35,indexExtension=0.030,leftWristDown=0.03,rightWristDown=0.97,leftWristUp=0.93,rightWristUp=0.07,rightRetracted=0.02,leftRetracted=0.98,hoverOverRight=0.85,hoverOverLeft=0.15,openClaw=0.7,closedClaw=0.5,defaultWrist=0.73,lowBasket=1200,highBasket=3800,highBar=700,transfer=0.78,outtake=0,closed,basketPos=.15,slamDown,specimenDeposit=0.2,pickedUpLeft=0.2,pickedUpRight=0.8;
    Servo PivotLeft,PivotRight,Claw;
    boolean WaitForPickUp=false,WaitForReturn=false;
    ColorSensor colorSensor = new ColorSensor();

    ElapsedTime time= new ElapsedTime();
    double closedd=0.58,opened=0.4;
    IntakeClaw intakeClaw = new IntakeClaw();
    boolean forceOpenIntake=false;
    boolean isOuttaking=false;
    boolean isSpecimen=false;
    int state=0;

        public class DepositSpecimen implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                PivotRight.setPosition(specimenDeposit);
                return false;
            }

        }

        public Action depositSpecimen() {
            return new DepositSpecimen();
        }
    public class IntakeSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            PivotRight.setPosition(0);
            return false;
        }

    }

    public Action intakeSpecimen() {
        return new IntakeSpecimen();
    }
    public class DepositSample implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            PivotRight.setPosition(basketPos);
            return false;
        }

    }

    public Action depositSample() {
        return new DepositSample();
    }

        public class DropSpecimen implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                Claw.setPosition(opened);
                return false;
            }

        }

        public Action dropSpecimen() {
            return new DropSpecimen();
        }
    public class SlamSpecimen implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            PivotRight.setPosition(0);
            return false;
        }

    }
    public Action slamSpecimen() {
        return new SlamSpecimen();
    }

    public class wristToWaitForTransfer implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                PivotRight.setPosition(0.7);
                return false;
            }

        }

        public Action WristToWaitForTransfer() {
            return new wristToWaitForTransfer();
        }
    public class transferPos implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            PivotRight.setPosition(transfer);
            Claw.setPosition(opened);
            return false;
        }

    }

    public Action TransferPos() {
        return new transferPos();
    }
    public class pickSample implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            Claw.setPosition(closedd);
            return false;
        }
    }
    public Action PickSample() {
        return new pickSample();
    }




    public void OuttakeInit(HardwareMap hardwareMap,double closed)
    {

        PivotLeft = hardwareMap.get(Servo.class,"PivotLeftOuttake");
        PivotRight = hardwareMap.get(Servo.class,"PivotRightOuttake");
        Claw = hardwareMap.get(Servo.class,"OuttakeClaw");
        colorSensor.CSinit(hardwareMap);
        time= new ElapsedTime();
        Claw.setPosition(closedd);
        PivotRight.setPosition(0.78);
        forceOpenIntake=false;
        isSpecimen=false;
        isOuttaking=false;
        //Left.setPosition(1);
       // Right.setPosition(0);


    }
    public void OuttakeUpdate(Gamepad gamepad,Gamepad gamepad1,double waitingForTransfer,double trasnfer,double outtake,double basketPos,double depositSpecimen,double slamDown){

        if(gamepad.a){
            PivotRight.setPosition(waitingForTransfer);
            Claw.setPosition(opened);
            isOuttaking=false;
        }
        if(gamepad.b){
            PivotRight.setPosition(waitingForTransfer);
            time.startTime();
            time.reset();
            state=1;
            isOuttaking=false;
        }
        if(time.milliseconds()>=600&& time.milliseconds()<=800 && state==1){
            PivotRight.setPosition(trasnfer);
            state=2;
            time.reset();
            //intakeClaw.OpenClaw();
        }
        if(time.milliseconds()>=900&&time.milliseconds()<=1100){
            Claw.setPosition(closedd);
            forceOpenIntake=true;
        }
        if(time.milliseconds()>=1300&& time.milliseconds()<=1500 && state==2){
            PivotRight.setPosition(outtake);
            forceOpenIntake=false;
            isOuttaking=true;
        }
        if(isOuttaking){
            if(gamepad1.left_bumper){
                Claw.setPosition(opened);
            }
        }
        if(gamepad1.left_bumper){
            Claw.setPosition(opened);
        }
        if(gamepad1.a){
            isOuttaking=false;
            PivotRight.setPosition(waitingForTransfer);
            Claw.setPosition(opened);
        }
        if(gamepad1.y||gamepad1.x){
            PivotRight.setPosition(basketPos);
            isOuttaking=true;
        }
        if(gamepad1.back){
                PivotRight.setPosition(0);
                Claw.setPosition(opened);
        }
        if(gamepad1.b){
            Claw.setPosition(closedd);
            PivotRight.setPosition(depositSpecimen);
        }
        if(gamepad1.right_bumper){
            PivotRight.setPosition(slamDown);
        }
    }
    public boolean returnForceIntake(){
        return forceOpenIntake;
    }
}
