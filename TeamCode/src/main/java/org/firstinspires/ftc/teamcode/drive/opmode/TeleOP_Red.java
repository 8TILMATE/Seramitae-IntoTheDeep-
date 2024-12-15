package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import kotlin.Pair;

@Config
@TeleOp(name = "TeleOp_Red")
public class TeleOP_Red extends OpMode
{
    public static double p=0.00085,i=0.0003,d=0.0001,f=0.01;
    public static  double outtaketestleft=0.7,outttaketestright;
    public static double transferPos;
    public static double LeftExtended=0.65,RightExtended=0.35,indexExtension=0.030,leftWristDown=0.03,rightWristDown=0.97,leftWristUp=0.93,rightWristUp=0.07,rightRetracted=0.02,leftRetracted=0.98,hoverOverRight=0.85,hoverOverLeft=0.15,openClaw=0.7,closedClaw=0.5,defaultWrist=0.73,lowBasket=1200,highBasket=3800,highBar=800,transfer=0.78,outtake=0,closed,basketPos=.15,slamDown,specimenDeposit=0.2,pickedUpLeft=0.2,pickedUpRight=0.8;
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
        verticalSlides.InitVerticalExtension(hardwareMap);
        outtakeClaw.OuttakeInit(hardwareMap,closed);
        //extension.Init(hardwareMap);
        //arm.Init(hardwareMap);
        //claw.Init(hardwareMap);
        //drone.Init(hardwareMap);

    }
    @Override
    public void loop(){
        telemetry=new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        float value=linkageExtension.RightPos();
        //telemetry.addData("dreapta:",value);
        telemetry.update();
        //telemetry.addData("Sample",colorSensor.CurrentSample);
        telemetry.addData("status",IntakeClaw.hovering);
        verticalSlides.VerticalExtensionUpdate(gamepad2,lowBasket,highBasket,highBar,50,p,i,d);

        //hang.HangSetPower(hardwareMap,gamepad2);
        mecanumDrive.Drive(gamepad1);
        linkageExtension.ExtensionUpdate(gamepad1,LeftExtended,RightExtended,indexExtension,leftRetracted,rightRetracted);
        intakeClaw.IntakeUpdate(gamepad1,leftWristDown,leftWristUp,rightWristDown,rightWristUp,hoverOverRight,hoverOverLeft,closedClaw,openClaw,defaultWrist,false, outtakeClaw.returnForceIntake(),pickedUpLeft,pickedUpRight);
        outtakeClaw.OuttakeUpdate(gamepad1,gamepad2,outtaketestleft,transfer,outtake,basketPos,specimenDeposit,slamDown);
        telemetry.addData("nigga", outtakeClaw.returnForceIntake());
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
    }
}