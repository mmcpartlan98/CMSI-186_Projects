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

public class ClockSolver {
  /**
   *  Class field definintions go here
   */
   private static final double DEFAULT_TIME_SLICE_SECONDS = 60.0;
   private static final double EPSILON_VALUE = 0.1;      // small value for double-precision comparisons

   private double tolerance = 0;
   private double timeSlice = DEFAULT_TIME_SLICE_SECONDS;
   private double angle = 0;

  /**
   *  Constructor
   *  This just calls the superclass constructor, which is "Object"
   */
   public ClockSolver() {
      super();
   }

  /**
   *  Method to handle all the input arguments from the command line
   *   this sets up the variables for the simulation
   */
   public void handleInitialArguments(String args[], Clock clock) {
     // args[0] specifies the angle for which you are looking
     //  your simulation will find all the times in a 12-hour day at which those angles occur
     // args[1] if present will specify a time slice value; if not present, defaults to 60 seconds
     // you may want to consider using args[2] for an "angle window"
      if(args.length == 0 || args.length > 2) {
         System.out.println( "   Wrong number of arguments\n" +
                             "   Usage: java ClockSolver <angle> [timeSlice]\n");
         System.exit( 1 );
      }
      if ( args.length == 2) {
        this.angle = clock.validateAngleArg(args[0]);
        this.timeSlice = clock.validateTimeSliceArg(args[1]);
      } else if (args.length == 1) {
        this.angle = clock.validateAngleArg(args[0]);
        this.timeSlice = clock.validateTimeSliceArg("60");
      }
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
      ClockSolver cs = new ClockSolver();
      Clock clock    = new Clock();
      System.out.println("AnglePre: " + clock.angle);
      cs.handleInitialArguments(args, clock);
      System.out.println("Tolerance: " + cs.tolerance);
      cs.tolerance = EPSILON_VALUE * cs.angle;
      System.out.println("-------------");
      while(clock.getTotalSeconds() <= 43200) {
        // cs.tolerance = EPSILON_VALUE * clock.getHandAngle();
         if (Math.abs(clock.getHandAngle() - clock.angle) <= EPSILON_VALUE) {
           System.out.println(clock.toString());
         }
         // System.out.println("handAngle: " + clock.getHandAngle() + " at " + clock.toString());
         clock.tick();
      }
      System.out.println("-------------");
      System.exit( 0 );
   }
}
