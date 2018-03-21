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
  private static final double EPSILON_VALUE = 0.1;      // small value for double-precision comparisons

  private double collisionRadius = 2 * (4.45 / 12);
  private double timeSlice = DEFAULT_TIME_SLICE_SECONDS;
  private double angle = 0;

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

    if(args.length % 4 != 1) {
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

  private boolean collisionChecker(Ball ball1, Ball ball2) {
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
    for (int i = 0; i < bs.length; i++) {
      System.out.println("Ball-" + i + " XPos: " + bs[i].getXPos());
      System.out.println("Ball-" + i + " YPos: " + bs[i].getYPos());
      System.out.println("Ball-" + i + " XVel: " + bs[i].getXVel());
      System.out.println("Ball-" + i + " YVel: " + bs[i].getYVel());
      System.out.println("Ball-" + i + " timeSlice: " + bs[i].getTimeSlice());
    }
    // Clock clock    = new Clock();
    // cs.handleInitialArguments(args, clock);
    // System.out.println("\n\nRunning simulation with: ");
    // System.out.println("Setting angle to: " + clock.getSoughtAngle());
    // System.out.println("Setting slice to: " + clock.getTimeSlice());
    // System.out.println("Tolerance: " + EPSILON_VALUE);
    // cs.tolerance = EPSILON_VALUE * cs.angle;
    // System.out.println("-------------");
    // while(clock.getTotalSeconds() <= 43200) {
    //   // cs.tolerance = EPSILON_VALUE * clock.getHandAngle();
    //   if (Math.abs(clock.getHandAngle() - clock.getSoughtAngle()) <= EPSILON_VALUE) {
    //     System.out.println(clock.toString());
    //   }
    //   // System.out.println("handAngle: " + clock.getHandAngle() + " at " + clock.toString());
    //   clock.tick();
    // }
    // System.out.println("-------------");
    // System.exit( 0 );
  }
}
