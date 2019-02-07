/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.team997coders.spartanlib.commands.CenterCamera;
import org.team997coders.spartanlib.interfaces.IJoystickValueProvider;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.*;
import frc.robot.subsystems.CameraMount;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LiftGear;
import frc.robot.subsystems.LineFollowing;
import frc.robot.vision.CameraControlStateMachine;
import frc.robot.vision.TargetSelector;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Will the getInstance call get the ArcadeDrive? It should.
  //private final Command defaultDriveTrain;
  public static OI oi;
  public static LiftGear liftGear;
  public static DriveTrain driveTrain;
  public static LineFollowing lineFollowing;
  public static CameraMount cameraMount;
  private CenterCamera centerCamera;
  private NetworkTableInstance networkTableInstance;
  public static NetworkTable visionNetworkTable;
  public static CameraControlStateMachine cameraControlStateMachine;
  
  Command autonomousCommand;
  SendableChooser<Command> chooser = new SendableChooser<>();

  public Robot(DriveTrain a, LineFollowing b) {
    super();
    driveTrain = a;
    lineFollowing = b;
  }

  public Robot() { super(); }
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    liftGear = new LiftGear();
    driveTrain = new DriveTrain();
    lineFollowing = new LineFollowing();
    cameraMount = new CameraMount(0, 120, 10, 170, 2, 20);

    networkTableInstance = NetworkTableInstance.getDefault();
    visionNetworkTable = networkTableInstance.getTable("Vision");
    cameraControlStateMachine = new CameraControlStateMachine(new TargetSelector(visionNetworkTable), visionNetworkTable);


    centerCamera = new CenterCamera(cameraMount);

    oi = new OI();

    chooser.setDefaultOption("Do Nothing", new AutoDoNothing());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", chooser);

  }

  @Override
  public void robotPeriodic() {
    updateSmartDashboard();
  }

  @Override
  public void disabledInit() {
    driveTrain.setCoast(); // So the drivers don't want to kill us ;)
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    centerCamera.start();
    autonomousCommand = chooser.getSelected();

    if (autonomousCommand != null) {
      autonomousCommand.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    centerCamera.start();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    // Start your engines
    //defaultDriveTrain.start();
  }

  @Override
  public void teleopPeriodic() {
    lineFollowing.isCloseToTarget();

    // Set current vision pan/tilt joystick values
    cameraControlStateMachine.slew(oi.getVisionLeftXAxis(), oi.getVisionLeftYAxis());

    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }

  public void updateSmartDashboard() {
    liftGear.updateSmartDashboard();
    driveTrain.updateSmartDashboard();
    cameraMount.updateSmartDashboard();
    lineFollowing.updateSmarts();
  }
}
