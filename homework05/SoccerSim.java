/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  File name     :  ClockSolver.java
*  Purpose       :  The main program for the ClockSolver class
*  @see
*  @author       :  B.J. Johnson
*  Date written  :  2017-02-28
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
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

public class SoccerSim {
  /**
  *  Class field definintions go here
  */
  private static final double DEFAULT_TIME_SLICE_SECONDS = 1;

  private static double collisionRadius = 2 * (4.45 / 12);
  private static double timeSlice = DEFAULT_TIME_SLICE_SECONDS;

  /**
  *  Constructor
  *  This just calls the superclass constructor, which is "Object"
  */
  public SoccerSim() {
    super();
  }

  /**
  *  Method to handle all the input arguments from the command line
  *   this sets up the variables for the simulation
  */
  public Ball[] handleInitialArguments(String args[]) {
    double tempX = 0;
    double tempY = 0;
    double tempXV = 0;
    double tempYV = 0;
    double timeSlice = 0;
    int ballCounter = 0;
    Ball ballSack[] = new Ball[Math.round((args.length - 1) / 4)];

    if(args.length % 4 != 1 && args.length > 4) {
      System.out.println( "   Invalid number of arguments. Usage is:\n\n" +
      "   java ClockSolver <xPos> <yPos> <xVel> <yVel> ... <timeSlice>\n");
      System.exit( 1 );
    }
    for (int i = args.length - 1; i >= 0; i--) {
      try {
        if (i == args.length - 1) {
          timeSlice = Double.parseDouble(args[i]);
        } else if (i % 4 == 0) {
          tempX = Double.parseDouble(args[i]);
        } else if (i % 4 == 1) {
          tempY = Double.parseDouble(args[i]);
        } else if (i % 4 == 2) {
          tempXV = Double.parseDouble(args[i]);
        } else if (i % 4 == 3) {
          tempYV = Double.parseDouble(args[i]);
        }

      } catch(NumberFormatException nfe) {
        System.out.println(nfe.getMessage());
        System.out.println( "\n\nNumberFormatException. Usage is: \n" +
        "java ClockSolver <xPos> <yPos> <xVel> <yVel> ... <timeSlice>\n");
        System.exit( 1 );
      }
      if (i % 4 == 0 && i != args.length - 1) {
        ballSack[ballCounter] = new Ball(tempX, tempY, tempXV, tempYV, timeSlice);
        ballCounter += 1;
      }
    }
    return ballSack;
  }

  private static boolean collisionChecker(Ball ball1, Ball ball2) {
    double coreDistance = Math.sqrt(Math.pow((ball1.getXPos() - ball2.getXPos()), 2) + Math.pow((ball1.getYPos() - ball2.getYPos()), 2));
    if (coreDistance <= collisionRadius) {
      return true;
    }
    return false;
  }

  /**
  *  The main program starts here
  *  remember the constraints from the project description
  *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
  *  @param  args  String array of the arguments from the command line
  *                args[0] is the angle for which we are looking
  *                args[1] is the time slice; this is optional and defaults to 60 seconds
  */
  public static void main( String args[] ) {
    SoccerSim cs = new SoccerSim();
    Ball[] bs = cs.handleInitialArguments(args);
    boolean vChecker = false;
    boolean cChecker = false;
    boolean finishedFlag = false;
    while (!vChecker) {
      vChecker = true;
      for (int i = bs.length - 1; i >= 0; i--) {
        System.out.println("Ball " + i + " <" + Math.round(bs[i].getXPos() * 1000.0)/1000.0 + ", " + Math.round(bs[i].getYPos() * 1000.0)/1000.0 + ">  Velocity Vector: <" + Math.round(bs[i].getXVel() * 1000.0)/1000.0 + ", " + Math.round(bs[i].getYVel() * 1000.0)/1000.0 + ">");
        vChecker = !(bs[i].tick());
      }
      System.out.println("--------------------");
      for (int x = 0; x < bs.length; x++) {
        for (int y = x + 1; y < bs.length; y++) {
          cChecker = collisionChecker(bs[x], bs[y]);
          if (cChecker == true && x != y) {
            System.out.println("COLLISION (Ball " + x + " and " + y + ") at:");
            System.out.println("Ball " + x + " <" + Math.round(bs[y].getXPos() * 1000.0)/1000.0 + ", " + Math.round(bs[y].getYPos() * 1000.0)/1000.0 + ">  Velocity Vector: <" + Math.round(bs[y].getXVel() * 1000.0)/1000.0 + ", " + Math.round(bs[y].getYVel() * 1000.0)/1000.0 + ">");
            System.out.println("Ball " + y + " <" + Math.round(bs[x].getXPos() * 1000.0)/1000.0 + ", " + Math.round(bs[x].getYPos() * 1000.0)/1000.0 + ">  Velocity Vector: <" + Math.round(bs[x].getXVel() * 1000.0)/1000.0 + ", " + Math.round(bs[x].getYVel() * 1000.0)/1000.0 + ">");
            finishedFlag = true;
          }
        }
      }
      if (finishedFlag == true) {
        System.exit(1);
      }
    }
    System.out.println("NO COLLISIONS DETECTED");
  }
}
