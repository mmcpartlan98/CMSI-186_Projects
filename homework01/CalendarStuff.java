/**
*  File name     :  CalendarStuff.java
*  Purpose       :  Provides a class with supporting methods for CountTheDays.java program
*  Author        :  B.J. Johnson (prototype)
*  Date          :  2017-01-02 (prototype)
*  Author        :  Matt McPartlan
*  Date          :  2018-01-18
*  Description   :  This file provides the supporting methods for the CountTheDays program which will
*                   calculate the number of days between two dates.  It shows the use of modularization
*                   when writing Java code, and how the Java compiler can "figure things out" on its
*                   own at "compile time".  It also provides examples of proper documentation, and uses
*                   the source file header template as specified in the "Greeter.java" template program
*                   file for use in CMSI 186, Spring 2017.
*  Notes         :  None
*  Warnings      :  None
*  Exceptions    :  None
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Revision History
*  ----------------
*            Rev      Date     Modified by:    Reason for change/modification
*           -----  ----------  --------------  -----------------------------------------------------------
*  @version 1.0.0  2017-01-02  B.J. Johnson    Initial writing and release
*  @version 1.0.1  2017-12-25  B.J. Johnson    Updated for Spring 2018
*  @version 1.1.0  2018-01-18  Matt McPartlan
*/
public class CalendarStuff {

  /**
  * A listing of the days of the week, assigning numbers; Note that the week arbitrarily starts on Sunday
  */
  private static final int SUNDAY     = 0;
  private static final int MONDAY     = SUNDAY    + 1;
  private static final int TUESDAY    = MONDAY    + 1;
  private static final int WEDNESDAY  = TUESDAY    + 1;
  private static final int THURSDAY   = WEDNESDAY    + 1;
  private static final int FRIDAY     = THURSDAY    + 1;
  private static final int SATURDAY   = FRIDAY    + 1;

  /**
  * A listing of the months of the year, assigning numbers; I suppose these could be ENUMs instead, but whatever
  */
  private static final int JANUARY   = 0;
  private static final int FEBRUARY  = JANUARY   + 1;
  private static final int MARCH     = FEBRUARY  + 1;
  private static final int APRIL     = MARCH   + 1;
  private static final int MAY       = APRIL   + 1;
  private static final int JUNE      = MAY   + 1;
  private static final int JULY      = JUNE   + 1;
  private static final int AUGUST    = JULY   + 1;
  private static final int SEPTEMBER = AUGUST   + 1;
  private static final int OCTOBER   = SEPTEMBER   + 1;
  private static final int NOVEMBER  = OCTOBER   + 1;
  private static final int DECEMBER  = NOVEMBER   + 1;
  // you can finish these on your own, too

  /**
  * An array containing the number of days in each month
  *  NOTE: this excludes leap years, so those will be handled as special cases
  *  NOTE: this is optional, but suggested
  */
  private static long[]    days        = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  /**
  * The constructor for the class
  */
  public CalendarStuff() {
    System.out.println( "Constructing CalendarStuff class." );  // replace this with the actual code
  }

  /**
  * A method to determine if the year argument is a leap year or not<br />
  *  Leap years are every four years, except for even-hundred years, except for every 400
  * @param    year  long containing four-digit year
  * @return         boolean which is true if the parameter is a leap year
  */
  public static boolean isLeapYear( long year ) {
    if (year % 4 == 0) {
      if (year % 100 == 0) {
        if (year % 400 == 0) {
          return true;
        } else {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  /**
  * A method to calculate the days in a month, including leap years
  * @param    month long containing month number, starting with "1" for "January"
  * @param    year  long containing four-digit year; required to handle Feb 29th
  * @return         long containing number of days in referenced month of the year
  * notes: remember that the month variable is used as an indix, and so must
  *         be decremented to make the appropriate index value
  */
  public static long daysInMonth( long month, long year ) {
    if (isLeapYear(year) && (int) month == 2) {
      return 29;
    } else {
      return days[(int)month - 1];
    }
  }

  /**
  * A method to determine if two dates are exactly equal
  * @param    month1 long    containing month number, starting with "1" for "January"
  * @param    day1   long    containing day number
  * @param    year1  long    containing four-digit year
  * @param    month2 long    containing month number, starting with "1" for "January"
  * @param    day2   long    containing day number
  * @param    year2  long    containing four-digit year
  * @return          boolean which is true if the two dates are exactly the same
  */
  public static boolean dateEquals( long month1, long day1, long year1, long month2, long day2, long year2 ) {
    boolean returnVal = false;
    if (year1 == year2) {
      if(month1 == month2) {
        if(day1 == day2) {
          return true;
        }
      }
    }
    return returnVal;
  }

  /**
  * A method to compare the ordering of two dates
  * @param    month1 long   containing month number, starting with "1" for "January"
  * @param    day1   long   containing day number
  * @param    year1  long   containing four-digit year
  * @param    month2 long   containing month number, starting with "1" for "January"
  * @param    day2   long   containing day number
  * @param    year2  long   containing four-digit year
  * @return          int    -1/0/+1 if first date is less than/equal to/greater than second
  */
  public static int compareDate( long month1, long day1, long year1, long month2, long day2, long year2 ) {
    double weightedCompare1 = (double)year1*100 + (double)month1 + ((double)day1/100);
    double weightedCompare2 = (double)year2*100 + (double)month2 + ((double)day2/100);
    // System.out.println(weightedCompare1);
    // System.out.println(weightedCompare2);
    if (weightedCompare1 == weightedCompare2) {
      return 0;
    } else if (weightedCompare1 < weightedCompare2) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
  * A method to return whether a date is a valid date
  * @param    month long    containing month number, starting with "1" for "January"
  * @param    day   long    containing day number
  * @param    year  long    containing four-digit year
  * @return         boolean which is true if the date is valid
  * notes: remember that the month and day variables are used as indices, and so must
  *         be decremented to make the appropriate index value
  */
  public static boolean isValidDate( long month, long day, long year ) {
    boolean returnVal = false;
    if(isLeapYear(year)) {
      days[FEBRUARY] = 29;
    }
    if (month > 0 && month < 13) {
      if (day <= days[(int)month - 1] && day > 0) {
        if (year > -9999 && year < 9999) {
          returnVal =  true;
        }
      }
    }
    days[FEBRUARY] = 28;
    return returnVal;
  }

  /**
  * A method to return a string version of the month name
  * @param    month long   containing month number, starting with "1" for "January"
  * @return         String containing the string value of the month (no spaces)
  */
  public static String toMonthString( int month ) {
    String monthReturn = "";
    switch( month - 1 ) {
      case JANUARY:
      monthReturn = "January";
      break;
      case FEBRUARY:
      monthReturn = "February";
      break;
      case MARCH:
      monthReturn = "March";
      break;
      case APRIL:
      monthReturn = "April";
      break;
      case MAY:
      monthReturn = "May";
      break;
      case JUNE:
      monthReturn = "June";
      break;
      case JULY:
      monthReturn = "July";
      break;
      case AUGUST:
      monthReturn = "August";
      break;
      case SEPTEMBER:
      monthReturn = "September";
      break;
      case OCTOBER:
      monthReturn = "October";
      break;
      case NOVEMBER:
      monthReturn = "November";
      break;
      case DECEMBER:
      monthReturn = "December";
      break;
      default: throw new IllegalArgumentException( "Illegal month value given to 'toMonthString()'." );
    }
    return monthReturn;
  }

  /**
  * A method to return a string version of the day of the week name
  * @param    day int    containing day number, starting with "1" for "Sunday"
  * @return       String containing the string value of the day (no spaces)
  */
  public static String toDayOfWeekString( int day ) {
    String dayReturn = "";
    switch( day - 1 ) {
      case SUNDAY:
      dayReturn = "Sunday";
      break;
      case MONDAY:
      dayReturn = "Monday";
      break;
      case TUESDAY:
      dayReturn = "Tuesday";
      break;
      case WEDNESDAY:
      dayReturn = "Wednesday";
      break;
      case THURSDAY:
      dayReturn = "Thursday";
      break;
      case FRIDAY:
      dayReturn = "Friday";
      break;
      case SATURDAY:
      dayReturn = "Saturday";
      break;
      default       : throw new IllegalArgumentException( "Illegal day value given to 'toDayOfWeekString()'." );
    }
    return dayReturn;
  }

  /**
  * A method to return a count of the total number of days between two valid dates
  * @param    month1 long   containing month number, starting with "1" for "January"
  * @param    day1   long   containing day number
  * @param    year1  long   containing four-digit year
  * @param    month2 long   containing month number, starting with "1" for "January"
  * @param    day2   long   containing day number
  * @param    year2  long   containing four-digit year
  * @return          long   count of total number of days
  */
  public static long daysBetween( long month1, long day1, long year1, long month2, long day2, long year2 ) {
    long dayCount = 0;
    int workingYear = 0;
    int workingMonth = 0;
    int workingDay = 0;
    switch (compareDate(month1, day1, year1, month2, day2, year2)) {
      case 1:
      long temp = month1;
      month1 = month2;
      month2 = temp;
      temp = day1;
      day1 = day2;
      day2 = temp;
      temp = year1;
      year1 = year2;
      year2 = temp;
      case -1:
      // Dates are ordered, insert main logic
      int yearsBetween = (int) year2 - (int) year1;
      int[] daysPerYear = new int[yearsBetween + 1];
      for (int yearIndex = 1; yearIndex < daysPerYear.length - 1; yearIndex++) {
        if (isLeapYear(year1 + yearIndex)) {
          daysPerYear[yearIndex] = 1;
        }
        daysPerYear[yearIndex] += 365;
      }
      if (month1 == month2 && year1 == year2) {
        daysPerYear[0] = ((int) day2 - (int) day1);
      } else {
        if (isLeapYear(year1)) {
          days[FEBRUARY] = 29;
        }
        daysPerYear[0] += (int) days[(int) month1 - 1] - (int) day1;
        daysPerYear[daysPerYear.length - 1] += (int) day2;
        if (year1 != year2) {
          for (int monthIndex = (int) month1; monthIndex < 12; monthIndex++) {
            daysPerYear[0] += days[monthIndex];
          }
          if (!isLeapYear(year2)) {
            days[FEBRUARY] = 28;
          } else {
            days[FEBRUARY] = 29;
          }
          // Fix condition if month2 is JANUARY
          for (int monthIndex = (int) month2 - 2; monthIndex >= 0; monthIndex--) {
            daysPerYear[daysPerYear.length - 1] += days[monthIndex];
          }
        }
      }
      for (int index = 0; index < daysPerYear.length; index++) {
        dayCount += daysPerYear[index];
      }
      break;

      case 0:
      return 0;
    }
    // System.out.println();
    // System.out.println("Returned: ");
    // System.out.println(dayCount);
    return dayCount;
  }

}
