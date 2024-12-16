package org.firstinspires.ftc.teamcode.drive.opmode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDrive {
    DcMotor LeftFront,LeftRear,RightFront,RightRear;
    float leftstickx,leftsticky,pivot;
    DcMotor[] motors = new DcMotor[4];
    public void InitDrivetrain(HardwareMap hardwareMap)
    {
        LeftFront = hardwareMap.get(DcMotor.class,"LF");
        LeftRear = hardwareMap.get(DcMotor.class,"LR");
        RightFront = hardwareMap.get(DcMotor.class,"RF");
        RightRear = hardwareMap.get(DcMotor.class,"RR");
        //RightRear.setDirection(DcMotor.Direction.REVERSE);
        //LeftFront.setDirection(DcMotor.Direction.REVERSE);
        LeftRear.setDirection(DcMotor.Direction.REVERSE);
        LeftFront.setDirection(DcMotor.Direction.REVERSE);
        motors[0]=LeftFront;
        motors[1]=LeftRear;
        motors[2]=RightFront;
        motors[3]=RightRear;
        for (DcMotor motor :motors) {
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }
    public void Drive(Gamepad gamepad){
        leftstickx=gamepad.left_stick_x;
        leftsticky= gamepad.left_stick_y;
        pivot=gamepad.right_stick_x/2f;
        double denominator = Math.max(Math.abs(leftstickx)+Math.abs(leftsticky)+ Math.abs(pivot),1);
        denominator+=(gamepad.right_trigger*2.2);
        motors[0].setPower((pivot+ -leftsticky+leftstickx)/denominator);
        motors[1].setPower((pivot+ -leftsticky-leftstickx)/denominator);
        motors[2].setPower((-pivot+ -leftsticky-leftstickx)/denominator);
        motors[3].setPower((-pivot+ -leftsticky+leftstickx)/denominator);
    }
}
