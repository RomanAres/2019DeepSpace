/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.Robot;

public class DeployBackLandingGear extends Command {
  public DeployBackLandingGear() {
    requires(Robot.liftGear);
  }
  
  @Override
  protected void initialize() { }
  
  @Override
  protected void execute() {
    if (!Robot.liftGear.getBackPistonState()) {
      Robot.liftGear.extendBack();
    }
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    if (Robot.liftGear.getBackPistonState()) {
      Robot.liftGear.retractBack();
    }
  }
  
  @Override
  protected void interrupted() { 
    if (Robot.liftGear.getBackPistonState()) {
      Robot.liftGear.retractBack();
    }
  }
}
