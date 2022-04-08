// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Climber.RotateCaneToBar;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.ToggleIdleMode;
import frc.robot.commands.Drive.MotorTurnAngle;
import frc.robot.commands.Intake.LowerIntake;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootTwiceAndCrossLine extends SequentialCommandGroup {

  public static final double AUTO_DRIVE_COLLECT_BALL_SPEED = 0.4;
  public static final double AUTO_DRIVE_CROSS_LINE_SPEED = AUTO_DRIVE_COLLECT_BALL_SPEED*1.5;
  public static final double AUTO_DRIVE_DIST = 55.0;
  public static final double AUTO_TURN_ANGLE = 180;

  /** Creates a new ShootTwiceAndCrossLine. */
  public ShootTwiceAndCrossLine() {
    // Add your commands in the addCommands() call, e.g.

    /* 
      1 - Shoot Ball
      2 - Drive back, Turn 180 degrees
      3 - Lower Intake
      4 - Drive Forwards
      5 - Pick Up Ball
      6 - Turn 180 Degrees
      7 - Drive Forwards
      8 - Shoot Ball
      9 - Drive Backwards out of the Zone
    */

    addCommands(
      new RotateCaneToBar(-0.3, true),
      new ShootBallsHighTimed(),
      new DriveDistance((-AUTO_DRIVE_DIST / 4), AUTO_DRIVE_DIST, true),
      new MotorTurnAngle(177, 0.4, 0.5, true),
      new LowerIntake(),
      new DriveAndSpinIntake(3 * ((AUTO_DRIVE_DIST + 12) / 4), AUTO_DRIVE_COLLECT_BALL_SPEED),
      new MotorTurnAngle(177, 0.4, 0.5, true),
      new DriveDistance(AUTO_DRIVE_DIST, AUTO_DRIVE_COLLECT_BALL_SPEED, true),
      new ShootBallsHighTimed(),
      new DriveDistance(-AUTO_DRIVE_DIST, AUTO_DRIVE_CROSS_LINE_SPEED, true)
    );
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    ShootTwiceAndCrossLine cmd = new ShootTwiceAndCrossLine();
    TestingDashboard.getInstance().registerCommand(auto, "AutoSequence", cmd);
  }
}

// Practice Match 4/6/22: 8:46 PM

//Auto:  backup + 1 high (6pts)
// TP:  11 high (22pts)
// CP: trvsl (15pts)

// Notes:  12-13 secs to spare

// total: 43pts.
