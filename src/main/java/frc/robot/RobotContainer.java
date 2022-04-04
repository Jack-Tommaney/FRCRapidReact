// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;

import frc.robot.commands.Climber.ConstantSpeedRotateCane;

import frc.robot.commands.Climber.DriveToBar;
import frc.robot.commands.Climber.PIDTurnCaneToAngle;
import frc.robot.commands.Climber.RotateCaneToBar;
import frc.robot.commands.Climber.TankRotateCane;
import frc.robot.commands.Climber.UserOperateCane;
import frc.robot.commands.Climber.CaneExtension.CaneExtendDistance;
import frc.robot.commands.Climber.CaneExtension.ExtendCaneToLimit;
import frc.robot.commands.Climber.CaneExtension.RetractCaneToBar;
import frc.robot.commands.Climber.CaneExtension.SmartCaneExtendDistance;
import frc.robot.commands.Climber.CaneExtension.SmartExtendCaneFully;
import frc.robot.commands.Climber.CaneExtension.SmartExtendCaneToLimit;
import frc.robot.commands.Climber.CaneExtension.TankCane;
import frc.robot.commands.Climber.CaneExtension.ZeroCaneEncoders;
import frc.robot.commands.Climber.Sequences.ClimbStatefully;
import frc.robot.commands.Climber.Sequences.ReachForNextBarSequence;
import frc.robot.commands.Climber.Sequences.ReachForNextBarStatefully;
import frc.robot.commands.Climber.Sequences.TestStateMachineSequence;
import frc.robot.commands.Drive.ArcadeDrive;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.KeyboardDrive;
import frc.robot.commands.Drive.PIDTurnAngle;
import frc.robot.commands.Drive.TankDrive;
import frc.robot.commands.Drive.TurnAngle;
import frc.robot.commands.Intake.LowerIntake;
import frc.robot.commands.Intake.RaiseIntake;
import frc.robot.commands.Intake.UserSpinIntake;
import frc.robot.commands.Shooter.PIDShooter;
import frc.robot.commands.Shooter.ShootBall;
import frc.robot.commands.Shooter.SpinShooter;
import frc.robot.commands.Conveyor.UserSpinConveyor;
import frc.robot.commands.Conveyor.SpinConveyorBackwards;
import frc.robot.commands.Conveyor.SpinConveyorForwards;
import frc.robot.commands.Shooter.ShootBall;
import frc.robot.commands.Auto.ShootTwiceAndCrossLine;
import frc.robot.commands.Auto.ShootAndCrossLine;
import frc.robot.commands.Auto.Wait;
import frc.robot.commands.Auto.ShootBallsHighTimed;
import frc.robot.commands.Auto.ShootBallsLow;
import frc.robot.commands.Auto.DriveBackAndShootHigh;
import frc.robot.commands.Auto.DelayThenFeedBalls;
import frc.robot.commands.Auto.DriveAndSpinIntake;
import frc.robot.commands.Auto.ExpelBalls;
import frc.robot.commands.Auto.IntakeBalls;
import frc.robot.commands.Auto.OpenGateAndFeedBalls;
import frc.robot.commands.Auto.OpenGateWhileActive;
import frc.robot.commands.Auto.RunShooterTimed;
import frc.robot.commands.Auto.ShootBallsHigh;
import frc.robot.commands.Auto.TimedTurn;
import frc.robot.commands.Intake.SpinIntake;
import frc.robot.subsystems.Auto;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberCaneExtension;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here...
  
  private final Drive m_drive;
  private final Climber m_climber;
  private ShootTwiceAndCrossLine m_ShootTwiceAndCrossLineAuto;
  private ShootAndCrossLine m_ShootAndCrossLineAuto;
  private final boolean ADVANCED_AUTO = false;
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Initialize subsystems
    Auto.getInstance();
    m_drive = Drive.getInstance();
    m_climber = Climber.getInstance();
    if(ADVANCED_AUTO == true) {
    m_ShootTwiceAndCrossLineAuto = new ShootTwiceAndCrossLine();
    }
    else if(ADVANCED_AUTO == false) {
      m_ShootAndCrossLineAuto = new ShootAndCrossLine();
    }


    m_drive.setDefaultCommand(new ArcadeDrive());

    // Configure the button bindings
    configureButtonBindings();

    // Register commands with TestingDashboard commands

    // Drive
    TankDrive.registerWithTestingDashboard();
    ArcadeDrive.registerWithTestingDashboard();
    DriveDistance.registerWithTestingDashboard();
    KeyboardDrive.registerWithTestingDashboard();
    TurnAngle.registerWithTestingDashboard();
    PIDTurnAngle.registerWithTestingDashboard();

    // Shooter
    ShootBall.registerWithTestingDashboard();
    PIDShooter.registerWithTestingDashboard();
    SpinShooter.registerWithTestingDashboard();

    // Climber
    DriveToBar.registerWithTestingDashboard();
    TankCane.registerWithTestingDashboard();

    TankRotateCane.registerWithTestingDashboard();
    UserOperateCane.registerWithTestingDashboard();
    ConstantSpeedRotateCane.registerWithTestingDashboard();
    ClimbStatefully.registerWithTestingDashboard();
    TestStateMachineSequence.registerWithTestingDashboard();
    CaneExtendDistance.registerWithTestingDashboard();
    PIDTurnCaneToAngle.registerWithTestingDashboard();
    RetractCaneToBar.registerWithTestingDashboard();
    ReachForNextBarSequence.registerWithTestingDashboard();
    RotateCaneToBar.registerWithTestingDashboard();
    ReachForNextBarStatefully.registerWithTestingDashboard();
    ExtendCaneToLimit.registerWithTestingDashboard();
    SmartCaneExtendDistance.registerWithTestingDashboard();
    SmartExtendCaneFully.registerWithTestingDashboard();
    SmartExtendCaneToLimit.registerWithTestingDashboard();
    ZeroCaneEncoders.registerWithTestingDashboard();
    

    // Conveyor
    SpinConveyorForwards.registerWithTestingDashboard();
    SpinConveyorBackwards.registerWithTestingDashboard();
    UserSpinConveyor.registerWithTestingDashboard();

    // Intake
    UserSpinIntake.registerWithTestingDashboard();
    RaiseIntake.registerWithTestingDashboard();
    LowerIntake.registerWithTestingDashboard();
    SpinIntake.registerWithTestingDashboard();
    DriveAndSpinIntake.registerWithTestingDashboard();

    // Auto
    ShootBallsHighTimed.registerWithTestingDashboard();
    ShootAndCrossLine.registerWithTestingDashboard();
    ShootTwiceAndCrossLine.registerWithTestingDashboard();
    ShootBallsHigh.registerWithTestingDashboard();
    ShootBallsLow.registerWithTestingDashboard();
    DelayThenFeedBalls.registerWithTestingDashboard();
    IntakeBalls.registerWithTestingDashboard();
    OpenGateAndFeedBalls.registerWithTestingDashboard();
    Wait.registerWithTestingDashboard();
    RunShooterTimed.registerWithTestingDashboard();
    ExpelBalls.registerWithTestingDashboard();
    OpenGateWhileActive.registerWithTestingDashboard();
    DriveBackAndShootHigh.registerWithTestingDashboard();
    TimedTurn.registerWithTestingDashboard();
    
    
    // Create Testing Dashboard
    TestingDashboard.getInstance().createTestingDashboard();
    
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    OI.getInstance();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    if(ADVANCED_AUTO == true) {
      return m_ShootTwiceAndCrossLineAuto;
    }
    else {
      return m_ShootAndCrossLineAuto;
    }
  }
}
