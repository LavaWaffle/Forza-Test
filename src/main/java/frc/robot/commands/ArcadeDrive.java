// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final Joystick m_joystick;
  private final double FORZA_DEADBAND = 0.01;
  private final double JOYSTICK_DEADBAND = 0.2;
  private double triggerRight;
  private double triggerLeft;
  private double speed;
  private double turning;
  private double speedPercentLimit = 1;
  private double turningPercentLimit = 0.85;
  // trigger left
  // trigger right
  // left axis x
  // left axis y

  /**
   * Creates a new ArcadeDrive. This command will drive your robot according to the speed supplier
   * lambdas. This command does not terminate.
   *
   * @param drivetrain The drivetrain subsystem on which this command will run
   * @param xaxisSpeedSupplier Lambda supplier of forward/backward speed
   * @param zaxisRotateSupplier Lambda supplier of rotational speed
   */
  public ArcadeDrive(
      Drivetrain drivetrain,
      Joystick joystick) {
    m_drivetrain = drivetrain;
    m_joystick = joystick;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Turn Percent", turningPercentLimit);
    SmartDashboard.putNumber("Speed Percent", speedPercentLimit);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // left joystick y axis inverted
    m_joystick.getRawAxis(1);
    // left joystick x axis
    turning = m_joystick.getRawAxis(0);
    if (Math.abs(turning) <= JOYSTICK_DEADBAND) {
      turning = 0;
    }

    triggerRight = m_joystick.getRawAxis(5); //right trigger
    triggerRight = applyDeadband(triggerRight, FORZA_DEADBAND);
    triggerRight = -((triggerRight - 1) / 2);
    triggerLeft = m_joystick.getRawAxis(4); //left trigger
    triggerLeft = applyDeadband(triggerLeft, FORZA_DEADBAND);
    triggerLeft = -((triggerLeft - 1) / 2);

    if (triggerRight >= triggerLeft) {
      // going forward : set speed to trigger
      speed = triggerRight;
    } else {
      // going backward : set speed to -trigger and inverse turning
      speed = -triggerLeft;
      turning = -turning;
    }

    turning = clamp(turning, -1.0d, 1.0d);
    speed = clamp(speed, -1.0d, 1.0d);

    turningPercentLimit = SmartDashboard.getNumber("Turn Percent", turningPercentLimit);
    speedPercentLimit = SmartDashboard.getNumber("Speed Percent", speedPercentLimit);

    turning *= turningPercentLimit;
    speed *= speedPercentLimit;

    SmartDashboard.putNumber("triggerLeft", triggerLeft);
    SmartDashboard.putNumber("triggerRight", triggerRight);
    SmartDashboard.putNumber("speed", speed);
    SmartDashboard.putNumber("turning", turning);


    m_drivetrain.forzaDrive(speed, turning);
  }
  
  private double clamp(double val, double min, double max) {
    return Math.max(min, Math.min(max, val));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private double applyDeadband(double input, double deadband) {
    double retval = input;
    if (Math.abs(input) >= deadband) {
      retval = (Math.abs(input) - deadband) / (1 - deadband);
      // Check if value is supposed to be a negative
      if (input > 0) {
        retval = -1 * retval;
      }
    }
    return retval;
  }
}