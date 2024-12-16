package com.rsproductions.meepmeeptesting;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setStartPose(new Pose2d(0,0,Math.toRadians(90)))
                .setConstraints(60, 60, Math.toRadians(90), Math.toRadians(90), 30)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-34, 56, Math.toRadians(90)))
                                .turn(Math.toRadians(180))
                                .splineToLinearHeading(new Pose2d(-35,30),Math.toRadians(-15))
                                .splineToLinearHeading(new Pose2d(-34,10),0)
                                //.splineToConstantHeading(new Vector2d(-34,20),0)
                                .splineToSplineHeading(new Pose2d(30,10),Math.toRadians(0))
                                .splineTo(new Vector2d(50,38),0)
                                .build()


                );



        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}