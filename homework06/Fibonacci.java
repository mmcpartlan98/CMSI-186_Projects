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

public class Fibonacci {
  /**
  *  Class field definintions go here
  */

  public Fibonacci() {
    super();
  }

  /**
  *  Method to handle all the input arguments from the command line
  *   this sets up the variables for the simulation
  */
  public static int handleInitialArgument(String arg) {
    for (int i = 0; i < arg.length(); i++) {
      if ((int) arg.charAt(i) < (int) '0' || (int) arg.charAt(i) > (int) '9') {
        throw new IllegalArgumentException("Argument must be a single int!");
      }
    }
    return Integer.parseInt(arg);
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
    BrobInt fibonacci = new BrobInt("0");
    BrobInt nextFib = new BrobInt("1");
    BrobInt output = new BrobInt("1");
    int n = handleInitialArgument(args[0]);

    System.out.println(fibonacci.toString());
    System.out.println(nextFib.toString());
    for (int i = 2; i < n; i++) {
      System.out.println(output.toString());
      fibonacci = new BrobInt(nextFib.toString());
      nextFib = new BrobInt(output.toString());
      output = new BrobInt((fibonacci.add(nextFib)).toString());
    }
    System.exit(0);
  }
}
