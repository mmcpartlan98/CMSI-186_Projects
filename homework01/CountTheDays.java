
/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  CountTheDays.java
 *  Purpose       :  Counts the number of days between two dates.
 *  Author        :  Matt McPartlan
 *  Date          :  01/23/2018
 *  Description   :  Second CMSI 186 assignment.
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  -----------------
 *   Rev      Date     Modified by:    Reason for change/modification
 *  -----  ----------  ------------    --------------------------------------------------------------------
 *  1.0.0  01/23/2018  Matt McPartlan  First writing
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
public class CountTheDays extends CalendarStuff {
   public static void main( String args[] ) {
     System.out.print("Days between inputs: ");
     System.out.println(daysBetween(Long.parseLong(args[0]), Long.parseLong(args[1]), Long.parseLong(args[2]), Long.parseLong(args[3]), Long.parseLong(args[4]), Long.parseLong(args[5])));
   }
}
