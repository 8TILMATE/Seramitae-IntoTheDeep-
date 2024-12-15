package org.firstinspires.ftc.teamcode.drive.opmode.auto;

import static org.firstinspires.ftc.teamcode.drive.opmode.TeleOP_Red.closed;
import static org.firstinspires.ftc.teamcode.drive.opmode.TeleOP_Red.defaultWrist;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
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
@Autonomous(name="Specimen")
public class SpecimenAuto extends LinearOpMode {
    public static int lineXintoSubmersible=-22;
    public static Vector2d intoSample1=new Vector2d(-44,57),intoTheBasket = new Vector2d(-55,67);
    Pose2d atSubmersible = new Pose2d(-24,0,Math.PI);
    Pose2d placedPiec1 = new Pose2d(-24,-5, Math.PI);
    Pose2d backdownPiec1Pos = new Pose2d(-35,-5,0);
    Vector2d replaceToPos1 = new Vector2d(-30,-50);
    Pose2d replaceToPlace1Pos = new Pose2d(-30,-50,0);
    Pose2d semiMoveToMovePos = new Pose2d(-12,-42,-Math.PI/4);
    Pose2d fullyMoveToMovePos = new Pose2d(-20,-50,-Math.PI/2);
    Pose2d atSpecimenPushed = new Pose2d(-55,-58,-Math.PI/2);
    Pose2d readyToCloseupPose = new Pose2d(-48,-50,0);
    Pose2d closeupPose = new Pose2d(-40,-50,0);
    Pose2d gotSpecimenInRobot = new Pose2d(-51,-50,Math.PI);
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
        Pose2d pose2d = new Pose2d(-60,-12,Math.PI);
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
        Action goToPose1 = drive.actionBuilder(new Pose2d(-24, 0,0))
                        .splineTo(new Vector2d(-20,-50),Math.PI/4).waitSeconds(1).build();
        Action placePiece1 = drive.actionBuilder(drive.pose)
                .splineTo(new Vector2d(-20, 0), Math.PI)
                .build();
        Action backdownPiece1 = drive.actionBuilder(placedPiec1)
                .setTangent(0)
                .splineToLinearHeading(backdownPiec1Pos, Math.PI)
                .build();
        Action replaceToPlace1 = drive.actionBuilder(backdownPiec1Pos)
                //.setTangent(0)
                .strafeToConstantHeading(replaceToPos1)//,0
                .build();
        Action semiMoveToMove = drive.actionBuilder(replaceToPlace1Pos)
                //.setTangent(0)
                .splineTo(new Vector2d(0, -60), -Math.PI/2)
                .build();
        Action fullyMoveToMove = drive.actionBuilder(semiMoveToMovePos)
                //.setTangent(0)
                .splineTo(new Vector2d(-35, -55), -Math.PI / 2)
                .build();
        Action moveStartPosition = drive.actionBuilder(fullyMoveToMovePos)
                .strafeToConstantHeading(new Vector2d(-55, -60))
                .build();
        Action repositionForSpecimen = drive.actionBuilder(atSpecimenPushed)
                .splineTo(new Vector2d(-40, -50), 0)
                .build();
        Action comePickup = drive.actionBuilder(closeupPose)
                .splineToConstantHeading(new Vector2d(-51, -50), Math.PI)
                .build();
        /*
        ############################################
        ##########[ CLAW_CLOSE CODE HERE ]##########
        ############################################
        */
        Action moveToPrePlace = drive.actionBuilder(gotSpecimenInRobot)
                .splineToConstantHeading(new Vector2d(-8, 33), -Math.PI)
                .build();
        Pose2d placedPiec1 = new Pose2d(-24,-5, Math.PI);
        Action returnToHumanPlayer = drive.actionBuilder(atSubmersible)
                        .splineTo(new Vector2d(-50,-58),0)
                //.turn(Math.PI)
                                .build();
        Actions.runBlocking(
                new ParallelAction(
                new SequentialAction(
                        new ParallelAction(
                        verticalSlides.highBar(),
                        outtakeClaw.depositSpecimen()),
                        new SleepAction(.05),
                        goToSubmersible,
                        new SleepAction(0.05),
                        outtakeClaw.dropSpecimen(),
                        new SleepAction(0.05),
                        new ParallelAction(
                        backdownPiece1,
                        verticalSlides.transfer()
                        ),
                        new SleepAction(0.05),
                        replaceToPlace1,
                        new SleepAction(0.05),
                        semiMoveToMove,
                        new SleepAction(0.05),
                       // fullyMoveToMove,
                        new SleepAction(0.05),
                        moveStartPosition,
                        new SleepAction(0.05),
                        new ParallelAction(
                        repositionForSpecimen,
                                outtakeClaw.intakeSpecimen()
                        ),
                        new SleepAction(0.05),
                        comePickup,
                        new SleepAction(0.1),
                        outtakeClaw.PickSample(),
                        new SleepAction(0.01),
                        new ParallelAction(
                                verticalSlides.highBar(),
                                outtakeClaw.depositSpecimen()
                        ),
                        new SleepAction(0.1),
                        //new ParallelAction(
                        //        moveToPrePlace,
                        //        verticalSlides.highBar(),
                        //)
                        moveToPrePlace,
                        new SleepAction(0.1),
                        outtakeClaw.dropSpecimen(),
                        verticalSlides.transfer(),
                        new SleepAction(0.05),
                        outtakeClaw.WristToWaitForTransfer(),
                        returnToHumanPlayer
                        //new SleepAction(0.05),
                        //comePickup,
                        //new SleepAction(1)
                        //moveToPrePlace
                        //new SleepAction(1),
                        //placePiece1
                ),
                        verticalSlides.slidesUpdate()
                ));
        /*Actions.runBlocking(new ParallelAction(

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
                                goToPose1,
                                new ParallelAction(
                                    verticalSlides.transfer()
                                ),
                                new SleepAction(0.5)
                                ),
                verticalSlides.slidesUpdate()
                )

         */
               // goToSample1,
               // new SleepAction(1),
                //alignSample1Basket,
                //new SleepAction(0.4),
                //goToBasket,
                //new SleepAction(0.4),
                //alignForBasket,
                //new SleepAction(2),
                //goToSample2
        //));
    }
}