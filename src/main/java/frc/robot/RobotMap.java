/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  public static class Ports {
    public static int

/*  Please do not delete...comment out */
/*  Logitec wireless gamepad settings  */
      GamePad1 = 1,
      GamePad2 = 0,
      buttonA = 2,
      buttonB = 3,
      buttonX = 1,
      buttonLeftThumbstick = 11,

/*  TODO: Wired controllers - flip back when at school lab */
/*
      GamePad1 = 0,
      GamePad2 = 1,
      buttonA = 1,
      buttonB = 2,
      buttonX = 3,
      buttonLeftThumbstick = 9,
*/
      buttonY = 4,
      buttonLeftShoulder = 5,
      buttonRightShoulder = 6,
      buttonRightThumbstick = 10,
      buttonLeftTrigger = 9,
      buttonRightTrigger = 10,
      buttonBack = 7,

      landingForward = 0,
      landingReverse = 1,

      leftXAxis = 0,
      leftYAxis = 1,
      rightXAxis = 4,
      rightYAxis = 5,

      leftVictor1 = 5,
      leftVictor2 = 6,
      rightVictor1 = 2,
      rightVictor2 = 3,

      leftTalon = 4,
      rightTalon = 1,

      linesensorleft = 1,  
      linesensorcenter = 3,
      linesensorright = 2,
      followLinebutton = 1,

      ultrasonicsensor = 2,

      panservo = 9,
      tiltservo = 8;

  }

  public class Values {

    public static final double
      P = 0.0002,
      I = 0.0,
      D = 0.0;

  }

}