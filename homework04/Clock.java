/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  File name     :  Clock.java
*  Purpose       :  Provides a class defining methods for the ClockSolver class
*  @author       :  M. McPartlan
*  Date written  :  2018-03-10
*  Description   :  This class provides a bunch of methods which may be useful for the ClockSolver class
*                   for Homework 4, part 1.  Includes the following:
*
*  Notes         :  None right now.  I'll add some as they occur.
*  Warnings      :  None
*  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Revision Histor
*  ---------------
*            Rev      Date     Modified by:  Reason for change/modification
*           -----  ----------  ------------  -----------------------------------------------------------
*  @version 1.0.0  2017-02-28  B.J. Johnson  Initial writing and release
*  @version 1.1.0  2018-03-10  M. McPartlan  Program body filled in
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

import java.lang.Math;

public class Clock {
  /**
  *  Class field definintions go here
  */
  private static final double DEFAULT_TIME_SLICE_IN_SECONDS = 60.0;
  private static final double INVALID_ARGUMENT_VALUE = -1.0;
  private static final double MAXIMUM_DEGREE_VALUE = 360.0;
  private static final double HOUR_HAND_DEGREES_PER_SECOND = 0.00834;
  private static final double MINUTE_HAND_DEGREES_PER_SECOND = 0.1;

  private double totalSeconds = 0;
  private double elapsedTime = 0;
  public double angle = 0;
  private double minuteAngle = 0;
  private double hourAngle = 0;
  private double innerAngle = 0;
  private double outerAngle = 360;
  private double timeSlice = DEFAULT_TIME_SLICE_IN_SECONDS;

  private double minuteIncrement = MINUTE_HAND_DEGREES_PER_SECOND * timeSlice;
  private double hourIncrement = HOUR_HAND_DEGREES_PER_SECOND * timeSlice;

  /**
  *  Constructor goes here
  */
  public Clock() {

  }

  /**
  *  Methods go here
  *
  *  Method to calculate the next tick from the time increment
  *  @return double-precision value of the current clock tick
  */
  public double tick() {
    this.elapsedTime += this.timeSlice;

    this.totalSeconds += this.timeSlice;
    this.minuteAngle = (this.minuteAngle + this.minuteIncrement) % 360.000;
    this.hourAngle = (this.hourAngle + this.hourIncrement) % 360.000;
    this.innerAngle = Math.abs(this.hourAngle - this.minuteAngle);
    this.outerAngle = Math.abs(this.innerAngle - 360);
    // System.out.println("innerAngle: " + innerAngle + " at " + this.toString());
    return this.innerAngle;
  }

  /**
  *  Method to validate the angle argument
  *  @param   argValue  String from the main programs args[0] input
  *  @return  double-precision value of the argument
  *  @throws  NumberFormatException
  */
  public double validateAngleArg( String argValue ) throws NumberFormatException {
    double angleIn = Double.parseDouble(argValue);
    if (angleIn > 360 || angleIn < 0) {
      throw new NumberFormatException("Invalid angle argument!");
    }
    if (angleIn == 180) {
      angleIn = 0;
    } else if (angleIn > 180) {
      angleIn = Math.abs(angleIn - 360);
    }
    this.angle = angleIn;
    System.out.println("Setting angle to: " + this.angle);
    return angleIn;
  }

  /**
  *  Method to validate the optional time slice argument
  *  @param  argValue  String from the main programs args[1] input
  *  @return double-precision value of the argument or -1.0 if invalid
  *  note: if the main program determines there IS no optional argument supplied,
  *         I have elected to have it substitute the string "60.0" and call this
  *         method anyhow.  That makes the main program code more uniform, but
  *         this is a DESIGN DECISION, not a requirement!
  *  note: remember that the time slice, if it is small will cause the simulation
  *         to take a VERY LONG TIME to complete!
  */
  public double validateTimeSliceArg( String argValue ) {
    double slice = Double.parseDouble(argValue);
    if (slice > 1800) {
      System.out.println("Time slice out of range: defaulting to " + DEFAULT_TIME_SLICE_IN_SECONDS + " seconds.");
      slice = DEFAULT_TIME_SLICE_IN_SECONDS;
    }
    this.timeSlice = slice;
    this.minuteIncrement = MINUTE_HAND_DEGREES_PER_SECOND * this.timeSlice;
    this.hourIncrement = HOUR_HAND_DEGREES_PER_SECOND * this.timeSlice;
    System.out.println("Setting slice to: " + this.timeSlice);
    return slice;
  }

  /**
  *  Method to calculate and return the current position of the hour hand
  *  @return double-precision value of the hour hand location
  */
  public double getHourHandAngle() {
    return this.hourAngle;
  }

  /**
  *  Method to calculate and return the current position of the minute hand
  *  @return double-precision value of the minute hand location
  */
  public double getMinuteHandAngle() {
    return this.minuteAngle;
  }

  /**
  *  Method to calculate and return the angle between the hands
  *  @return double-precision value of the angle between the two hands
  */
  public double getHandAngle() {
    double handAngle = 0;
    if (this.innerAngle < this.outerAngle) {
      // handAngle = Math.round(this.innerAngle*100.00)/100.00;
      handAngle = this.innerAngle;
    }
    if (this.innerAngle >= this.outerAngle) {
      // handAngle = Math.round(this.outerAngle*100.00)/100.00;
      handAngle = this.outerAngle;
    }
    return handAngle;
    // return Math.round(this.innerAngle);
    // return this.innerAngle;
  }

  /**
  *  Method to fetch the total number of seconds
  *   we can use this to tell when 12 hours have elapsed
  *  @return double-precision value the total seconds private variable
  */
  public double getTotalSeconds() {
    return this.totalSeconds;
  }

  /**
  *  Method to return a String representation of this clock
  *  @return String value of the current clock
  */
  public String toString() {
    int hour = (int) Math.floor(this.elapsedTime/3600);
    elapsedTime -= hour*3600;
    int minute = (int) Math.floor(this.elapsedTime/60);
    elapsedTime -= minute*60;
    double second = Math.floor(this.elapsedTime*100.00)/100.00;
    elapsedTime = totalSeconds;
    String stringOut = "Time: " + hour + ":" + minute + ":" + second;
    return stringOut;
  }

  /**
  *  The main program starts here
  *  remember the constraints from the project description
  *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
  *  be sure to make LOTS of tests!!
  *  remember you are trying to BREAK your code, not just prove it works!
  */
  public static void main( String args[] ) {

    System.out.println( "\nCLOCK CLASS TESTER PROGRAM\n" +
    "--------------------------\n" );
    System.out.println( "  Creating a new clock: " );
    Clock clock = new Clock();
    System.out.println( "    New clock created: " + clock.toString() );
    System.out.println( "    Testing validateAngleArg()....");
    System.out.print( "      sending '  0 degrees', expecting double value   0.0" );
    try { System.out.println( (0.0 == clock.validateAngleArg( "0.0" )) ? " - got 0.0" : " - no joy" ); }
    catch( Exception e ) { System.out.println ( " - Exception thrown: " + e.toString() ); }

  }
}
