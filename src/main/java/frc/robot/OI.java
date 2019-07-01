package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;
import frc.robot.commands.vision.ApproachTarget;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  Joystick gamepad1;
  Joystick gamepad3;

  // temporary elevator testing buttons.
  public JoystickButton elevatorGoUp; // Y 2
  public JoystickButton elevatorGoDown; // X 2
  public JoystickButton ballIntake; // Right Bumper 2
  public JoystickButton ballOutake; // Left Bumper 2
  public JoystickButton driveSafe; // Right Bumper 1
  public JoystickButton flip;

  public JoystickButton ArmForward; // Back 2
  public JoystickButton ArmReverse; // Start 2

  private JoystickButton deployLandingGear; // B 1
  private JoystickButton deployBackLandingGear; // Y 1
  private JoystickButton retractLandingGear; // Back 1
  private JoystickButton toggleHatch; // B 2
  private JoystickButton autoDriveToTarget; // A 2

  public int secretModeCounterA = 0, secretModeCounterB = 0;

  public OI() {
    // driver controls... game sticks control the motion of the robot
    // left stick Y-axis is drive power
    // right stick X-axis is drive direction
    gamepad1 = new Joystick(RobotMap.Buttons.GamePad1);
    gamepad3 = new Joystick(RobotMap.Buttons.GamePad3);

    // #region Gamepad1 Controls

    deployLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonB);
    deployLandingGear.whenPressed(new ToggleFrontLandingGear());

    deployBackLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonY);
    deployBackLandingGear.whenPressed(new ToggleRearLandingGear());

    retractLandingGear = new JoystickButton(gamepad1, RobotMap.Buttons.buttonBack);
    retractLandingGear.whenPressed(new RetractLandingGear());

    flip = new JoystickButton(gamepad1, RobotMap.Buttons.buttonX);
    flip.whenPressed(new FlipArmChain());

    autoDriveToTarget = new JoystickButton(gamepad1, RobotMap.Buttons.buttonA);
    autoDriveToTarget.whenPressed(new ApproachTarget(0.3, 100));

    // #endregion

    // #region Gamepad2 Controls
    elevatorGoUp = new JoystickButton(gamepad3, RobotMap.Buttons.buttonY);
    elevatorGoUp.whileHeld(new ElevatorUppity());

    elevatorGoDown = new JoystickButton(gamepad3, RobotMap.Buttons.buttonX);
    elevatorGoDown.whileHeld(new ElevatorDownity());

    ArmForward = new JoystickButton(gamepad3, RobotMap.Buttons.buttonStart);
    ArmForward.whileHeld(new MoveArm(-0.5));

    ArmReverse = new JoystickButton(gamepad3, RobotMap.Buttons.buttonBack);
    ArmReverse.whileHeld(new MoveArm(0.5));

    ballIntake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonRightShoulder);
    ballIntake.whileHeld(new BallIntake());

    ballOutake = new JoystickButton(gamepad3, RobotMap.Buttons.buttonLeftShoulder);
    ballOutake.whileHeld(new BallOuttake());

    toggleHatch = new JoystickButton(gamepad3, RobotMap.Buttons.buttonB);
    toggleHatch.whileHeld(new ToggleHatch());

    // #endregion
  }

  // #region Controller Data

  public double getLeftYAxis() {
    return condition_gamepad_axis(0.05, -gamepad1.getRawAxis(RobotMap.Buttons.leftYAxis), -1, 1);
  }

  public double getLeftYAxis2() {
    return condition_gamepad_axis(0.05, -gamepad3.getRawAxis(RobotMap.Buttons.leftYAxis), -1, 1);
  }

  public double getRightXAxis() {
    return condition_gamepad_axis(0.05, gamepad1.getRawAxis(RobotMap.Buttons.rightXAxis), -1, 1);
  }

  public double getRightYAxis() {
    return condition_gamepad_axis(0.05, -gamepad1.getRawAxis(RobotMap.Buttons.rightYAxis), -1, 1);
  }

  public int getPOV() {
    return gamepad3.getPOV();
  }

  /**
   * Make the gamepad axis less sensitive to changes near their null/zero point.
   * 
   * @param value raw value from the gamepad axis
   * @param dead  value for the deadband size
   * @return
   */
  public double deadBand(double value, double dead) {
    if (Math.abs(value) < dead) {
      return 0;
    } else {
      return value;
    }
  }

  /**
   * Clamp/Limit the value to only be within two limits
   * 
   * @param min lower limit
   * @param max upper limit
   * @param val value to check
   * @return
   */
  public double clamp(double min, double max, double val) {
    if (min > val) {
      return min;
    } else if (max < val) {
      return max;
    } else {
      return val;
    }
  }

  /**
   * Combine both a joystick limit and a clamp within standard limits.
   * 
   * I really wish programmers would name methods descriptively so that I do not
   * have to waste my time figuring out what things like "bing" and "stuff" do! I
   * am guessing that this does good "stuff" to my joystick. CCB.
   * 
   * @param dead deadband limit, no output within this limit. Normally 0.05
   * @param val  raw value from axis
   * @param min  lower limit for axis. Normally -1
   * @param max  upper limit on axis. Normally +1
   * @return conditioned value from axis (limited -1 to +1, with a )
   */
  public double condition_gamepad_axis(double dead, double val, double min, double max) {
    return clamp(min, max, deadBand(val, dead));
  }
  // #endregion
}
