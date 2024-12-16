package org.firstinspires.ftc.teamcode.drive.opmode;

import android.util.Pair;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantFunction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;


public class VerticalSlides {
    DcMotor verticalLeft,verticalRight;
    PIDController controller;
    public static double p=0.00085,i=0.0003,d=0.0001,f=0.01;
    double target=0;
    float ticks_in_rotations=537.6f;
    ElapsedTime timeUpSpecimen = new ElapsedTime();



    public class HighBar implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            target = 800;
            return false;
        }
    }

    public Action highBar() {
        return new HighBar();
    }
    public class Transfer implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            target = 0;
            return false;
        }
    }

    public Action transfer() {
        return new Transfer();
    }
    public class HighBasket implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            target = 3500;
            return false;
        }
    }

    public Action highBasket() {
        return new HighBasket();
    }

    public class SlidesUpdate implements Action {

        private boolean initialized = true;
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            controller.setPID(p, i, d);
            int armPos = (verticalRight.getCurrentPosition());
            double pid = controller.calculate(armPos, target);
            double power = pid;
            verticalRight.setPower(power);
            return  true;

        }
    }
    public Action slidesUpdate() {
        return new SlidesUpdate();
    }
    public class goToHighBar implements Action {

        private boolean initialized = true;
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
           verticalRight.setTargetPosition(750);
           verticalRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           return false;
        }
    }
    public Action GoToHighBar() {
        return new goToHighBar();
    }
    public void InitVerticalExtension(HardwareMap hardwareMap)
    {
        controller= new PIDController(p,i,d);
        //verticalLeft = hardwareMap.get(DcMotor.class,"VerticalLeft");
        verticalRight = hardwareMap.get(DcMotor.class,"VerticalRight");
        verticalRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        verticalRight.setDirection(DcMotorSimple.Direction.REVERSE);
        timeUpSpecimen= new ElapsedTime();
    }

    public void initAutoExtension(HardwareMap hardwareMap){
        controller= new PIDController(p,i,d);
        //verticalLeft = hardwareMap.get(DcMotor.class,"VerticalLeft");
        verticalRight = hardwareMap.get(DcMotor.class,"VerticalRight");
        verticalRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        verticalRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        verticalRight.setDirection(DcMotorSimple.Direction.REVERSE);
        timeUpSpecimen= new ElapsedTime();
    }
    public void VerticalExtensionUpdate(Gamepad gamepad,double lowBasket,double highBasket,double highBar,double Transfer,double kp,double ki,double kd)
    {
        p=kp;
        i=ki;
        d=kd;
        controller.setPID(p,i,d);
        int armpos=verticalRight.getCurrentPosition();
        double pid = controller.calculate(armpos,target);
        double power = pid;
        if(gamepad.a){
            target=Transfer;
        }
        if(gamepad.y){
            target=highBasket;
        }
        if(gamepad.x){
            target = lowBasket;
        }
        if(gamepad.b){
            timeUpSpecimen.startTime();
            timeUpSpecimen.reset();
        }
        if(gamepad.left_trigger>0){
            power=-gamepad.left_trigger;
        }
        if(gamepad.dpad_down){
            verticalRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        if(timeUpSpecimen.milliseconds()>=300&&timeUpSpecimen.milliseconds()<=500){
            target=highBar;
        }

        //verticalLeft.setPower(power);
        verticalRight.setPower(power);
    }
    public Float returnTicksFromMotors(){
        //Double leftMotorTicks = verticalLeft.getCurrentPosition();
        Float rightMotorTicks = (float) verticalRight.getCurrentPosition();
        return  rightMotorTicks;
    }
}
