/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  File name     :  Fibonacci.java
*  Purpose       :  The main program for the Fibonacci class
*  @see
*  @author       :  Matt McPartlan
*  Date written  :  2018-04-23
*  Description   :  This class prints out the Fibonacci sequence using the BrobInt class
*  Exceptions    :  IllegalArgumentException when the input arguments are noninteger values
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Revision Histor
*  ---------------
*            Rev      Date     Modified by:    Reason for change/modification
*           -----  ----------  --------------  -----------------------------------------------------------
*  @version 1.0.0  2018-04-23  Matt McPartlan  Initial writing and release
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
  // Accepts one argument: Integer n -  the number of Fibbonacci numbers to display.
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
