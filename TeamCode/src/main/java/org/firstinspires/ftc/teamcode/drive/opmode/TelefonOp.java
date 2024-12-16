package org.firstinspires.ftc.teamcode.drive.opmode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.security.cert.Extension;

import kotlin.Pair;
@Config
@TeleOp(name = "TeleOP")
public class TelefonOp extends OpMode
{
    public static double p=0.0085,i=0.0003,d=0.0001,f=0.01;
    public static  double outtaketestleft,outttaketestright;
    public static double LeftExtended=0.65,RightExtended=0.35,indexExtension=0.015,leftWristDown=0.03,rightWristDown=0.97,leftWristUp=0.85,rightWristUp=0.15,rightRetracted=0.02,leftRetracted=0.98,hoverOverRight=0.87,hoverOverLeft=0.13,openClaw=0.7,closedClaw=0.5,defaultWrist=0.55,lowBasket=1200,highBasket=3000,highBar=700;
    //Hang hang = new Hang();
    MecanumDrive mecanumDrive = new MecanumDrive();
    LinkageExtension linkageExtension = new LinkageExtension();
    IntakeClaw intakeClaw = new IntakeClaw();
    ColorSensor colorSensor = new ColorSensor();
    OuttakeClaw outtakeClaw = new OuttakeClaw();
    VerticalSlides verticalSlides = new VerticalSlides();
    //Extension extension = new Extension();
    //Arm arm = new Arm();
    //Claw claw = new Claw();
    //Drone drone = new Drone();
    Pair<Boolean,Boolean> BoolsFromClaw;
    @Override
    public void init(){
        //hang.HangInit(hardwareMap);
        mecanumDrive.InitDrivetrain(hardwareMap);
        linkageExtension.InitExtension(hardwareMap);
        intakeClaw.IntakeInit(hardwareMap,defaultWrist);
        colorSensor.CSinit(hardwareMap);
        //outtakeClaw.OuttakeInit(hardwareMap,closed);
        verticalSlides.InitVerticalExtension(hardwareMap);
        //extension.Init(hardwareMap);
        //arm.Init(hardwareMap);
        //claw.Init(hardwareMap);
        //drone.Init(hardwareMap);

    }
    @Override
    public void loop(){
        /*telemetry=new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        float value=linkageExtension.RightPos();
        //telemetry.addData("dreapta:",value);
        telemetry.update();
        colorSensor.CSUpdate();
        telemetry.addData("Sample",colorSensor.CurrentSample);
        //hang.HangSetPower(hardwareMap,gamepad2);
        mecanumDrive.Drive(gamepad1);
        linkageExtension.ExtensionUpdate(gamepad1,LeftExtended,RightExtended,indexExtension,leftRetracted,rightRetracted);
        intakeClaw.IntakeUpdate(gamepad1,leftWristDown,leftWristUp,rightWristDown,rightWristUp,hoverOverRight,hoverOverLeft,closedClaw,openClaw,defaultWrist,false);
        outtakeClaw.OuttakeUpdate(gamepad1,outtaketestleft,outttaketestright);
        //extension.UpdateExtension(gamepad2,gamepad1);
        //arm.UpdateArm(gamepad1,gamepad2)
        //claw.UpdateClaw(gamepad1,gamepad2);
        //claw.UpdateParams(arm.isIntaking,arm.isOuttaking);
        //telemetry.addData("Extension",extension.ReturnPosition());
        //telemetry.addData("Brat",arm.ReturnTicks());
        //BoolsFromClaw=claw.SentBoolsBack();
        // drone.UpdateDrone(gamepad2);
        //telemetry.addData("IsIntaking",BoolsFromClaw.component1());
        //telemetry.addData("IsOuttaking",BoolsFromClaw.component2());

         */
        //highBasket=3000;
        //verticalSlides.VerticalExtensionUpdate(gamepad2,lowBasket,highBasket,highBar,200,p,i,d);
        telemetry.addData("TicksRight",verticalSlides.returnTicksFromMotors());
        telemetry.update();
    }
}