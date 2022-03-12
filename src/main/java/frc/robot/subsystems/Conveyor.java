// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

public class Conveyor extends SubsystemBase {
  private static Conveyor m_conveyor;
  private CANSparkMax m_conveyorRight;
  private CANSparkMax m_conveyorLeft;
  boolean conveyor = false;

  /** Creates a new Conveyor. */
  public Conveyor() {
    if(conveyor == true){
    m_conveyorLeft = new CANSparkMax(RobotMap.C_LEFT_MOTOR,MotorType.kBrushless);
    m_conveyorRight = new CANSparkMax(RobotMap.C_RIGHT_MOTOR,MotorType.kBrushless);
    }
  }

  public static Conveyor getInstance() {
    if (m_conveyor == null) {
      m_conveyor = new Conveyor();
      TestingDashboard.getInstance().registerSubsystem(m_conveyor, "Conveyor");
    }
    return m_conveyor;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void spinConveyor(double speed) {
    if(conveyor == true){
    m_conveyorLeft.set(speed);
    m_conveyorRight.set(speed);
    }
  }
}
