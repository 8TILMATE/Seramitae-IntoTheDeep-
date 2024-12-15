package org.firstinspires.ftc.teamcode.drive.opmode.auto;

import static org.firstinspires.ftc.teamcode.drive.opmode.TeleOP_Red.closed;
import static org.firstinspires.ftc.teamcode.drive.opmode.TeleOP_Red.defaultWrist;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.IntakeClaw;
import org.firstinspires.ftc.teamcode.drive.opmode.LinkageExtension;
import org.firstinspires.ftc.teamcode.drive.opmode.OuttakeClaw;
import org.firstinspires.ftc.teamcode.drive.opmode.VerticalSlides;
import org.opencv.core.Mat;

@Config
@Autonomous(name="Basket")
public class BasketAuto extends LinearOpMode {
    public static int lineXintoSubmersible=-20;
    public static Vector2d intoSample1=new Vector2d(-44,57),intoTheBasket = new Vector2d(-55,67);
    IntakeClaw intakeClaw = new IntakeClaw();
    LinkageExtension linkageExtension = new LinkageExtension();
    OuttakeClaw outtakeClaw = new OuttakeClaw();
    VerticalSlides verticalSlides = new VerticalSlides();
    @Override
    public void runOpMode()
    {
        linkageExtension.InitExtension(hardwareMap);
        intakeClaw.IntakeInit(hardwareMap,defaultWrist);
        verticalSlides.InitVerticalExtension(hardwareMap);
        outtakeClaw.OuttakeInit(hardwareMap,closed);
        Pose2d pose2d = new Pose2d(-60,12,Math.PI);
        Pose2d sampleToHeading = new Pose2d(-36,-48, Math.PI/-6);
        Pose2d fromSampleToBasket = new Pose2d(-48,-48,Math.PI/4);
        Pose2d backBasket = new Pose2d(-48, 54, Math.PI / -4);
        Pose2d basketTo2 = new Pose2d(-60,65, Math.PI/4);
        MecanumDrive drive= new MecanumDrive(hardwareMap, pose2d);
        waitForStart();
        Action goToSubmersible = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(lineXintoSubmersible,0),Math.PI)
                .waitSeconds(1)
                .build();
        Action goToSample1 = drive.actionBuilder(new Pose2d(-24,0,Math.PI))
                        .splineTo(intoSample1,0).waitSeconds(1).build();
        Action goToBasket = drive.actionBuilder(new Pose2d(-44,57,0))
                        .splineTo(intoTheBasket,7*Math.PI/4).waitSeconds(0.5).build();
        Actions.runBlocking(new ParallelAction(

                new ParallelAction(
                        new SequentialAction(
                                verticalSlides.highBar(),
                                new SleepAction(0.2),
                                outtakeClaw.depositSpecimen(),
                                new SleepAction(0.3),
                                goToSubmersible,
                                new SleepAction(0.2),
                                outtakeClaw.dropSpecimen(),
                                new SleepAction(0.2),
                                new ParallelAction(
                                    goToSample1,
                                    verticalSlides.transfer()
                                ),
                                new SleepAction(0.5),
                                new ParallelAction(
                                    linkageExtension.extend(),
                                    intakeClaw.openClaw()
                                        ),
                                new SleepAction(1),
                                intakeClaw.intakeDown(),
                                new SleepAction(1),
                                intakeClaw.closedClaw(),
                                new SleepAction(0.3),
                                new ParallelAction(
                                        intakeClaw.backToTransfer(),
                                        linkageExtension.deextend(),
                                        outtakeClaw.WristToWaitForTransfer()
                                        ),
                                new SleepAction(0.2),
                                outtakeClaw.TransferPos(),
                                new SleepAction(0.1),
                                outtakeClaw.dropSpecimen(),
                                new SleepAction(1),
                                outtakeClaw.PickSample(),
                                new SleepAction(0.4),
                                intakeClaw.openClaw(),
                                //outtakeClaw.depositSpecimen(),
                                new SleepAction(0.3),
                                goToBasket,
                                new SleepAction(0.5),
                                verticalSlides.highBasket(),
                                new SleepAction(2),
                                outtakeClaw.depositSample(),
                                new SleepAction(0.85),
                                outtakeClaw.dropSpecimen(),
                                new SleepAction(1),
                                outtakeClaw.WristToWaitForTransfer(),
                                new SleepAction(2),
                                verticalSlides.transfer(),
                                new SleepAction(0.2)
                                ),
                verticalSlides.slidesUpdate()
                )
               // goToSample1,
               // new SleepAction(1),
                //alignSample1Basket,
                //new SleepAction(0.4),
                //goToBasket,
                //new SleepAction(0.4),
                //alignForBasket,
                //new SleepAction(2),
                //goToSample2
        ));
    }
}