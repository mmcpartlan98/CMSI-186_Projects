/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  File name     :  Ball.java
*  Purpose       :  Defined the methods and properties of a soccer ball with constant acceleration
*  @author       :  M. McPartlan
*  Date written  :  2018-03-20
*  Description   :  This class provides a bunch of methods which may be useful for the SoccerSim class
*  Notes         :  None.
*  Warnings      :  None.
*  Exceptions    :  None.
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Revision History
*  ----------------
*            Rev      Date     Modified by:  Reason for change/modification
*           -----  ----------  ------------  -----------------------------------------------------------
*  @version 1.0.0  2018-03-20  M. McPartlan  Initial drafting - NOT tested
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

import java.lang.Math;

public class Ball {
  /**
  *  Class field definintions go here
  */
  private static final double DEFAULT_TIME_SLICE_IN_SECONDS = 1.0;

  private double xPos = 0;
  private double yPos = 0;
  private double xVel = 0;
  private double yVel = 0;
  private double timeSlice = DEFAULT_TIME_SLICE_IN_SECONDS;
  private String ballID = "Ball-";

  /**
  *  Constructor goes here
  */
  public Ball(double xPosI, double yPosI, double xVelI, double yVelI) {
    this.xPos = xPosI;
    this.yPos = yPosI;
    this.xVel = xVelI;
    this.yVel = yVelI;
  }

  /**
  *  Methods go here
  *
  *  Method to calculate the next tick from the time increment
  *  @return double-precision value of the current clock tick
  */
  public boolean tick() {
    boolean returnVal = true;
    this.xPos = this.xPos + this.xVel * this.timeSlice;
    this.yPos = this.yPos + this.yVel * this.timeSlice;
    this.xVel = this.xVel - this.xVel * 0.01;
    this.yVel = this.yVel - this.yVel * 0.01;
    if (this.xVel < 1 || this.yVel < 1) {
      returnVal = false;
    }
    return returnVal;
  }

  /**
  *  Method to set the timeSlice.
  */
  public void setTimeSlice(double newTimeslice) {
    this.timeSlice = newTimeslice;
  }

  /**
  *  Method to set the ballID.
  */
  public void setBallID(int ballNum) {
    this.ballID = this.ballID + ballNum;
  }


  /**
  *  @return double-precision value of the xPosition
  */
  public double getXPos() {
    return this.xPos;
  }

  /**
  *  @return double-precision value of the yPosition
  */
  public double getYPos() {
    return this.yPos;
  }

  /**
  *  Method to return a String representation of this clock
  *  @return String value of the current clock
  */
  public String toString() {
    String stringOut = this.ballID + ": " + Math.round(this.xPos*100.0)/100.0 + ", " + Math.round(this.yPos*100.0)/100.0;
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

    System.out.println( "\nBALL CLASS TESTER PROGRAM\n" +
    "--------------------------\n" );
    System.out.println( "  Creating a new ball: " );
    Ball ball = new Ball(0, 0, 10, 100);
    ball.setBallID(1);
    ball.setTimeSlice(0.01);
    System.out.println("New ball created: " + ball.toString());
    while (ball.tick()) {
      System.out.println(ball.toString());
    }
  }
}
