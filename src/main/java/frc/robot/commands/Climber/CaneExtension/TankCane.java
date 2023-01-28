// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber.CaneExtension;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.input.XboxController;
import frc.robot.input.XboxController.XboxAxis;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberCaneExtension;
import frc.robot.testingdashboard.TestingDashboard;


public class TankCane extends CommandBase {
  private Climber m_climber;
  private ClimberCaneExtension m_climberCaneExtension;
  private OI m_oi;
  private double m_caneSpeed;
  private boolean m_parameterized;

  /** Creates a new tankCane. **/
  public TankCane(double caneSpeed, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climberCaneExtension = ClimberCaneExtension.getInstance();
    addRequirements(m_climberCaneExtension);
    m_caneSpeed = caneSpeed;
    m_parameterized = parameterized;
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    TankCane cmd = new TankCane(Climber.INITIAL_CANE_EXTENTION_SPEED, false);
    TestingDashboard.getInstance().registerCommand(climber, "CaneExtension", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_climber.tankCane(0, 0);
    m_oi = OI.getInstance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_caneSpeed = TestingDashboard.getInstance().getNumber(m_climber, "ExtensionSpeed");
    }
    
    double leftSpeed = 0;
    double rightSpeed = 0;
    if (Constants.XBOX_CONTROLLER_DRIVER_ENABLE) {
      XboxController xbox = m_oi.getDriverXboxController();

      if (xbox.getAxis(XboxAxis.kLeftTrigger) > 0) {
        leftSpeed = m_caneSpeed;
      } else if (xbox.getButtonLeftBumper().getAsBoolean()) {
        leftSpeed = -m_caneSpeed;
      }
      if (xbox.getAxis(XboxAxis.kRightTrigger) > 0) {
        rightSpeed = m_caneSpeed;
      } else if (xbox.getButtonRightBumper().getAsBoolean()) {
        rightSpeed = -m_caneSpeed;
      }
    }
    m_climber.tankCane(leftSpeed, rightSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climber.tankCane(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
