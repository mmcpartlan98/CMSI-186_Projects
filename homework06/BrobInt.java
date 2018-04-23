/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* File name  :  BrobInt.java
* Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
* @author    :  B.J. Johnson
* Date       :  2017-04-04
* Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework06.html'>Assignment Page</a>
* Notes      :  None
* Warnings   :  None
*
*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* Revision History
* ================
*   Ver      Date     Modified by:  Reason for change or modification
*  -----  ----------  ------------  ---------------------------------------------------------------------
*  1.0.0  2017-04-04  B.J. Johnson  Initial writing and begin coding
*  1.1.0  2017-04-13  B.J. Johnson  Completed addByt, addInt, compareTo, equals, toString, Constructor,
*                                     validateDigits, two reversers, and valueOf methods; revamped equals
*                                     and compareTo methods to use the Java String methods; ready to
*                                     start work on subtractByte and subtractInt methods
*
*  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;


public class BrobInt {

  public static final BrobInt ZERO     = new BrobInt(  "0" );      /// Constant for "zero"
  public static final BrobInt ONE      = new BrobInt(  "1" );      /// Constant for "one"
  public static final BrobInt TWO      = new BrobInt(  "2" );      /// Constant for "two"
  public static final BrobInt THREE    = new BrobInt(  "3" );      /// Constant for "three"
  public static final BrobInt FOUR     = new BrobInt(  "4" );      /// Constant for "four"
  public static final BrobInt FIVE     = new BrobInt(  "5" );      /// Constant for "five"
  public static final BrobInt SIX      = new BrobInt(  "6" );      /// Constant for "six"
  public static final BrobInt SEVEN    = new BrobInt(  "7" );      /// Constant for "seven"
  public static final BrobInt EIGHT    = new BrobInt(  "8" );      /// Constant for "eight"
  public static final BrobInt NINE     = new BrobInt(  "9" );      /// Constant for "nine"
  public static final BrobInt TEN      = new BrobInt( "10" );      /// Constant for "ten"

  /// Some constants for other intrinsic data types
  ///  these can help speed up the math if they fit into the proper memory space
  public static final BrobInt MAX_INT  = new BrobInt(Integer.valueOf( Integer.MAX_VALUE ).toString() );
  public static final BrobInt MIN_INT  = new BrobInt(Integer.valueOf( Integer.MIN_VALUE ).toString() );
  public static final BrobInt MAX_LONG = new BrobInt(Long.valueOf( Long.MAX_VALUE ).toString() );
  public static final BrobInt MIN_LONG = new BrobInt(Long.valueOf( Long.MIN_VALUE ).toString() );

  /// These are the internal fields
  String internalValue = "";        // internal String representation of this BrobInt
  byte   sign          = 0;         // "0" is positive, "1" is negative
  byte[] byteVersion   = null;      // byte array for storing the string values; uses the reversed string

  /**
  *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
  *   and handles that accordingly;  it then checks to see if it's all valid digits.
  *  @param  value  String value to make into a BrobInt
  */
  public BrobInt( String value ) {
    this.internalValue = value;
    this.byteVersion = this.validateDigits(this.internalValue);
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to validate all digits, construct the byte array, and set the sign of the BrobInt
  *  @return  byte array with padding added in front for potential carry
  *  @throws  IllegalArgumentException if something is hinky
  *  note that there is no return false, because of throwing the exception
  *  note also that this must check for the '+' and '-' sign digits
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public byte[] validateDigits( String internalString) {
    int startOfNumber = 0;
    byte[] byteArray = new byte[internalValue.length() + 1];

    // While the index startOfNumber is NOT a number, increment
    while (((int) internalString.charAt(startOfNumber) < (int) '0' || (int) internalString.charAt(startOfNumber) > (int) '9')) {
      startOfNumber += 1;
    }
    for (int i = 0; i < internalString.length(); i++) {
      if ((int) internalString.charAt(i) < (int) '0' || (int) internalString.charAt(i) > (int) '9') {
        if (((int) internalString.charAt(i) != (int) '-' && (int) internalString.charAt(i) != (int) '+') || i != startOfNumber - 1) {
          throw new IllegalArgumentException("Input contains at least one non-numeric input OR a misplaced sign.");
        }
      }
      if (startOfNumber != 0) {
        if (internalString.charAt(startOfNumber - 1) == '-') {
          this.sign = 1;
        } else {
          this.sign = 0;
        }
      }

      if (this.sign == 1 && i == startOfNumber - 1) {
        // Remove negative sign
        byteArray[i + 1] = (byte) 0;
      } else {
        byteArray[i + 1] = (byte) ((int) internalString.charAt(i) - 48);
      }

    }
    if (this.sign == 1) {
      byte[] tempArray = new byte[byteArray.length - 1];
      for (int i = 0; i < byteArray.length - 1; i++) {
        tempArray[i] = byteArray[i + 1];
      }
      return tempArray;
    }
    return byteArray;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to compare a BrobInt passed as argument to this BrobInt
  *  @param  gint  BrobInt to add to this
  *  @return int   that is one of neg/0/pos if this BrobInt lessThan/equals/greaterThan the argument
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

  public int compareTo( BrobInt gint ) {
  byte byteVersionLengthThis = 0;
    byte byteVersionLengthGint = 0;
    int hasHit = 0;
    for (int i = 0; i < this.byteVersion.length; i++) {
      if (this.byteVersion[i] > 0 || hasHit == 1) {
        hasHit = 1;
        byteVersionLengthThis += 1;
      }
    }
    hasHit = 0;
    for (int i = 0; i < gint.byteVersion.length; i++) {
      if (gint.byteVersion[i] > 0 || hasHit == 1) {
        hasHit = 1;
        byteVersionLengthGint += 1;
      }
    }

    if (this.sign == 0 && gint.sign == 1) {
      return 1;
    } else if (this.sign == 1 && gint.sign == 0) {
      return -1;
    } else if (this.sign == 0 && gint.sign == 0) {
      if ( byteVersionLengthThis > byteVersionLengthGint ) {
        return 1;
      } else if( byteVersionLengthThis < byteVersionLengthGint ) {
        return (-1);
      } else {
        for( int i = 0; i < this.byteVersion.length; i++ ) {
          if(byteVersion[i] > gint.byteVersion[i]) {
            return 1;
          } else if(byteVersion[i] < gint.byteVersion[i]) {
            return (-1);
          }
        }
      }
    } else if (this.sign == 1 && gint.sign == 1) {
      if ( byteVersionLengthThis > byteVersionLengthGint ) {
        return -1;
      } else if ( byteVersionLengthThis < byteVersionLengthGint ) {
        return 1;
      } else {
        for( int i = 0; i < this.byteVersion.length; i++ ) {
          if (byteVersion[i] > gint.byteVersion[i]) {
            return -1;
          } else if (byteVersion[i] < gint.byteVersion[i]) {
            return 1;
          }
        }
      }
    }
    return 0;
  }


  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to check if a BrobInt passed as argument is equal to this BrobInt
  *  @param  gint     BrobInt to compare to this
  *  @return boolean  that is true if they are equal and false otherwise
  *  NOTE: this method performs a similar lexicographical comparison as the "compareTo()" method above
  *        also using the java String "equals()" method -- THAT was easy, too..........
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public boolean equals( BrobInt gint ) {
    return (internalValue.equals( gint.toString() ));
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to add the value of a BrobIntk passed as argument to this BrobInt using byte array
  *  @param  gint         BrobInt to add to this
  *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public BrobInt add( BrobInt gint ) {

    byte localSign = 0;
    byte[] carryArray;
    byte[] sumArray1;
    byte[] sumArray2;
    byte thisSign = this.sign;
    byte gintSign = gint.sign;
    BrobInt output;

    // System.out.println("gint sign: " + gint.sign);
    // System.out.println("this sign: " + this.sign);

    if (gint.sign == 1 && this.sign == 1) {
      // Both are negative. Add magnitudes, final sign is negative.
      localSign = 1;
    }

    if ((gint.sign == 1 && this.sign == 0) || (gint.sign == 0 && this.sign == 1)) {
      // Call subtract function for the two magnitudes and handle signs in subtraction
      // System.out.println(gint.sign);
      if (gint.sign == 1 && this.compareTo(gint.multiply(new BrobInt("-1"))) < 1) {
        // negative gint is greater than positive this, final sign must be negative
        // Subtract magnitudes, final negative sign
        // System.out.println("Path one. ");
        gint.sign = 0;
        output = gint.subtract(this);
        gint.sign = 1;
        // System.out.println(output.toString());
        output.sign = 1;
      } else if (this.sign == 1 && gint.compareTo(this.multiply(new BrobInt("-1"))) > -1) {
        // positive gint is greater than negative this
        // System.out.println("Path two. ");
        this.sign = 0;
        output = gint.subtract(this);
        this.sign = 1;
        output.sign = 0;
      } else if (gint.sign == 1 && this.compareTo(gint.multiply(new BrobInt("-1"))) > -1) {
        // negative gint is less than positive this, final sign negative
        // System.out.println("Path three. ");
        gint.sign = 0;
        output = gint.subtract(this);
        gint.sign = 1;
        output.sign = 1;
      } else {
        // if (this.sign == 1 && gint.compareTo(this.multiply(new BrobInt("-1"))) == -1)
        // positive gint is less than negative this
        // System.out.println("Path four. ");
        this.sign = 0;
        output = this.subtract(gint);
        this.sign = 1;
        output.sign = 1;
      }
    } else {

      if (gint.byteVersion.length >= this.byteVersion.length) {
        carryArray = new byte[gint.byteVersion.length];
        sumArray1 = new byte[gint.byteVersion.length];
        sumArray2 = new byte[gint.byteVersion.length];
      } else {
        carryArray = new byte[this.byteVersion.length];
        sumArray1 = new byte[this.byteVersion.length];
        sumArray2 = new byte[this.byteVersion.length];
      }

      for (int i = 0; i < carryArray.length; i++) {
        carryArray[i] = 0;
      }

      for (int i = gint.byteVersion.length - 1; i >= 0; i--) {
        sumArray1[sumArray1.length - 1 - i] = gint.byteVersion[gint.byteVersion.length - 1 - i];
      }

      for (int i = this.byteVersion.length - 1; i >= 0; i--) {
        sumArray2[sumArray2.length - 1 - i] = this.byteVersion[this.byteVersion.length - 1 - i];
      }

      for (int i = carryArray.length - 1; i >= 0; i--) {
        if (sumArray1[i] + sumArray2[i] + carryArray[i] > 9) {
          if (i != 0) {
            carryArray[i - 1] = 1;
          }
          sumArray1[i] = (byte) (sumArray1[i] + sumArray2[i] - 10);
        } else if (sumArray1[i] + sumArray2[i] + carryArray[i] <= 9) {
          sumArray1[i] = (byte) (sumArray1[i] + sumArray2[i]);
        }
      }

      for (int i = 0; i < carryArray.length; i++) {
        // System.out.print(carryArray[i]);
      }
      // System.out.println();

      for (int i = 0; i < sumArray1.length; i++) {
        sumArray1[i] = (byte) (sumArray1[i] + carryArray[i]);
      }

      String byteString = "";
      int hasHit = 0;

      if (localSign == 1) {
        byteString = byteString.concat( "-" );
      }

      for( int i = 0; i < sumArray1.length; i++ ) {
        if(sumArray1[i] != 0) {
          hasHit = 1;
        }
        if (hasHit == 0 && sumArray1[i] == 0) {
          // Skip this concatenation cycle
        } else {
          byteString = byteString.concat( Byte.toString( sumArray1[i] ) );
        }
      }

      if (byteString.length() == 0 || byteString.equals("-")) {
        byteString = "0";
      }

      output = new BrobInt(byteString);

    }
    this.sign = thisSign;
    gint.sign = gintSign;
    return output;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to subtract the value of a BrobInt passed as argument to this BrobInt using bytes
  *  @param  gint         BrobInt to subtract from this
  *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public BrobInt subtract( BrobInt gint ) {
    byte thisSign = this.sign;
    byte gintSign = gint.sign;
    BrobInt output;
    byte subtractionResult[];
    byte subtractant[];

    if (gint.byteVersion.length > this.byteVersion.length) {
      subtractionResult = new byte[gint.byteVersion.length];
      subtractant = new byte[gint.byteVersion.length];
      for (int i = this.byteVersion.length - 1; i >= 0; i--) {
        subtractionResult[(gint.byteVersion.length - this.byteVersion.length) + i] = this.byteVersion[i];
      }

      // Build the subtractant byte array
      for (int i = gint.byteVersion.length - 1; i >= 0; i--) {
        subtractant[i] = gint.byteVersion[i];
      }
    } else {
      subtractionResult = new byte[this.byteVersion.length];
      subtractant = new byte[this.byteVersion.length];
      // Build the subtractionResult byte array
      for (int i = this.byteVersion.length - 1; i >= 0; i--) {
        subtractionResult[i] = this.byteVersion[i];
      }

      // Build the subtractant byte array
      for (int i = gint.byteVersion.length - 1; i >= 0; i--) {
        subtractant[(this.byteVersion.length - gint.byteVersion.length) + i] = gint.byteVersion[i];
      }
    }

    if (this.sign == 0 && gint.sign == 0) {
      // Positive - positive = depends
      if (this.compareTo(gint) == -1) {
        // Negative
        output = new BrobInt(((gint.subtract(this)).multiply(new BrobInt("-1"))).toString());
        this.sign = thisSign;
        gint.sign = gintSign;
        return output;
      } else {
        // if (this.compareTo(gint) == 1)
        // Positive
        for (int i = subtractionResult.length - 1; i >= 0; i--) {
          if (subtractionResult[i] - subtractant[i] < 0) {
            subtractionResult[i - 1] = (byte) (subtractionResult[i - 1] - 1);
            subtractionResult[i] = (byte) (subtractionResult[i] - subtractant[i] + 10);
          } else if (subtractionResult[i] - subtractant[i] >= 0) {
            subtractionResult[i] = (byte) (subtractionResult[i] - subtractant[i]);
          }
        }
        String byteString = "";
        int hasHit = 0;

        for( int i = 0; i < subtractionResult.length; i++ ) {
          if(subtractionResult[i] != 0) {
            hasHit = 1;
          }
          if (hasHit == 0 && subtractionResult[i] == 0) {
            // Skip this concatenation cycle
          } else {
            byteString = byteString.concat( Byte.toString( subtractionResult[i] ) );
          }
        }

        if (byteString.length() == 0) {
          byteString = "0";
        }
        this.sign = thisSign;
        gint.sign = gintSign;
        return new BrobInt(byteString);
      }
    } else if ((this.sign == 0 && gint.sign == 1)) {
      // Positive - negative = positive
      output = new BrobInt((this.add(gint.multiply(new BrobInt("-1")))).toString());
      this.sign = thisSign;
      gint.sign = gintSign;
      return output;
    } else if (this.sign == 1 && gint.sign == 0) {
      // Negative - positive = negative
      output = new BrobInt((((this.multiply(new BrobInt("-1")).add(gint))).multiply(new BrobInt("-1"))).toString());
      this.sign = thisSign;
      gint.sign = gintSign;
      return output;
    } else {
      // if (this.sign == 1 && gint.sign == 1)
      // Negative - negative = depends
      // Since comparint two negative numbers
      // If this is less than gint it has a larger negative magnitude
      if (this.compareTo(gint) == -1) {
        // Negative
        output = new BrobInt((((this.multiply(new BrobInt("-1"))).subtract(gint.multiply(new BrobInt("-1")))).multiply(new BrobInt("-1"))).toString());
        this.sign = thisSign;
        gint.sign = gintSign;
        return output;
      } else {
        // if (this.compareTo(gint) == 1)
        // Positive
        output = new BrobInt((((gint.multiply(new BrobInt("-1"))).subtract(this.multiply(new BrobInt("-1")))).toString()));
        this.sign = thisSign;
        gint.sign = gintSign;
        return output;
      }
    }
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to multiply the value of a BrobInt passed as argument to this BrobInt
  *  @param  gint         BrobInt to multiply by this
  *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public BrobInt multiply( BrobInt gint ) {
    if (gint.compareTo(new BrobInt("-1")) == 0) {
      if (this.sign == 1) {
        this.sign = 0;
      } else {
        this.sign = 1;
      }
      return this;
    }

    byte localSign = 0;
    if ((this.sign == 1 && gint.sign == 0) || (this.sign == 0 && gint.sign == 1)) {
      localSign = 1;
    }
    BrobInt index = new BrobInt("0");
    BrobInt smallMultiplicand;
    BrobInt revolvingSum = new BrobInt("0");
    BrobInt largeMultiplicand;

    byte tempThisSign = this.sign;
    byte tempGintSign = gint.sign;

    this.sign = 0;
    gint.sign = 0;

    if (this.compareTo(gint) == -1) {
      // New number is larger than old
      smallMultiplicand = new BrobInt(this.toString());
      largeMultiplicand = new BrobInt(gint.toString());
    } else {
      // New number is smaller than old
      smallMultiplicand = new BrobInt(gint.toString());
      largeMultiplicand = new BrobInt(this.toString());
    }
    smallMultiplicand.sign = 0;
    largeMultiplicand.sign = 0;
    this.sign = tempThisSign;
    gint.sign = tempGintSign;

    while (index.compareTo(smallMultiplicand) < 0) {
      revolvingSum = revolvingSum.add(largeMultiplicand);
      index = index.add(new BrobInt("1"));
    }
    revolvingSum.sign = localSign;
    return revolvingSum;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
  *  @param  gint         BrobInt to divide this by
  *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public BrobInt divide( BrobInt gint ) {
    byte localSign = 0;
    BrobInt index = new BrobInt("0");
    BrobInt numerator;
    BrobInt denominator;
    BrobInt revolvingSum = new BrobInt("0");

    if (this.sign == 1 && gint.sign == 0) {
      localSign = 1;
      numerator = new BrobInt((this.multiply(new BrobInt("-1"))).toString());
      denominator = new BrobInt(gint.toString());
    } else if (this.sign == 0 && gint.sign == 1) {
      localSign = 1;
      numerator = new BrobInt(this.toString());
      denominator = new BrobInt((gint.multiply(new BrobInt("-1"))).toString());
    } else {
      numerator = new BrobInt(this.toString());
      denominator = new BrobInt(gint.toString());
    }

    if (gint.compareTo(this) == 0) {
      return new BrobInt("1");
    } else if (gint.compareTo(this) == 1) {
      return new BrobInt("0");
    } else {

      while (numerator.compareTo(revolvingSum.add(denominator)) > -1) {
        revolvingSum = revolvingSum.add(denominator);
        index = index.add(new BrobInt("1"));
      }
      index.sign = localSign;
      return index;
    }
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to get the remainder of division of this BrobInt by the one passed as argument
  *  @param  gint         BrobInt to divide this one by
  *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public BrobInt remainder( BrobInt gint ) {
    BrobInt divisionResult = new BrobInt((this.divide(gint)).toString());
    return this.subtract(divisionResult.multiply(gint));
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to return a String representation of this BrobInt
  *  @return String  which is the String representation of this BrobInt
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public String toString() {
    String byteString = "";
    int hasHit = 0;

    if (this.sign == 1) {
      byteString = byteString.concat( "-" );
    }

    for( int i = 0; i < this.byteVersion.length; i++ ) {
      if(this.byteVersion[i] != 0) {
        hasHit = 1;
      }
      if (hasHit == 0 && this.byteVersion[i] == 0) {
        // Skip these concatenation cycles
      } else {
        byteString = byteString.concat( Byte.toString( this.byteVersion[i] ) );
      }
    }
    if (byteString.length() == 0 || byteString.equals("-")) {
      byteString = "0";
    }
    return byteString;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to display an Array representation of this BrobInt as its bytes
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public void toArray( byte[] d ) {
    System.out.println( Arrays.toString( d ) );
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to return a BrobInt given a long value passed as argument
  *  @param  value         long type number to make into a BrobInt
  *  @return BrobInt  which is the BrobInt representation of the long
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public static BrobInt valueOf( long value ) throws NumberFormatException {
    BrobInt gi = null;
    try {
      gi = new BrobInt(Long.valueOf( value ).toString() );
    }
    catch( NumberFormatException nfe ) {
      System.out.println( "\n  Sorry, the value must be numeric of type long." );
    }
    return gi;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  the main method redirects the user to the test class
  *  @param  args  String array which contains command line arguments
  *  note:  we don't really care about these
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public static void main( String[] args ) {
    try {

      System.out.println(new BrobInt("-999999").compareTo(new BrobInt("-234567")));
      System.out.println(new BrobInt("0").compareTo(new BrobInt("2")));
      System.out.println(new BrobInt("-1").compareTo(new BrobInt("-9")));
      System.out.println(new BrobInt("1000").compareTo(new BrobInt("-1000")));
      System.out.println(new BrobInt("0").compareTo(new BrobInt("0")));
      System.out.println(new BrobInt("1").compareTo(new BrobInt("-9")));
      System.out.println(" ======== ");
      System.out.println(new BrobInt("-1").toString());
      System.out.println(new BrobInt("0").toString());
      System.out.println(new BrobInt("1").toString());

      System.out.println("Adding two positives:");
      BrobInt addPos1 = new BrobInt("1000");
      System.out.println(addPos1.add(new BrobInt("1000")));
      System.out.println("======== Expected: 2000 ========");
      System.out.println();

      System.out.println("Adding two positives:");
      BrobInt addPos5 = new BrobInt("5928374659823645876293847569283659823649582638456298374659836798452938746598347659826948569832674598369586348562034569237659236598369586734986589376589376298456389246598768356847659346592834985723649857239487569384765923874695837495874659837054234596283764598736498576938476598372694587236587643986982739648756938476598327469538746598736498756398476598374659847632587324");
      System.out.println(addPos5.add(new BrobInt("5928374659823645876293847569283659823649582638456298374659836798452938746598347659826948569832674598369586348562034569237659236598369586734986589376589376298456389246598768356847659346592834985723649857239487569384765923874695837495874659837054234596283764598736498576938476598372694587236587643986982739648756938476598327469538746598736498756398476598374659847632587324")));
      System.out.println("======== Expected: 2000 ========");
      System.out.println();

      System.out.println("Adding negative and positive:");
      BrobInt addPos2 = new BrobInt("-1000");
      System.out.println(addPos2.add(new BrobInt("1000")));
      System.out.println("======== Expected: 0 ========");
      System.out.println();

      System.out.println("Adding positive and negative:");
      BrobInt addPos3 = new BrobInt("1000");
      System.out.println(addPos3.add(new BrobInt("-1000")));
      System.out.println("======== Expected: 0 ========");
      System.out.println();

      System.out.println("Adding two negatives:");
      BrobInt addPos4 = new BrobInt("-1000");
      System.out.println(addPos4.add(new BrobInt("-1000")));
      System.out.println("======== Expected: -2000 ========");
      System.out.println();

      // Subtraction tests
      System.out.println("Subtracting two positives:");
      BrobInt subPos1 = new BrobInt("1000");
      System.out.println(subPos1.subtract(new BrobInt("500")));
      System.out.println("======== Expected: 500 ========");
      System.out.println();

      System.out.println("Subtracting positive and negative:");
      BrobInt subPos3 = new BrobInt("1000");
      System.out.println(subPos3.subtract(new BrobInt("-1000")));
      System.out.println("======== Expected: 2000 ========");
      System.out.println();

      System.out.println("Subtracting negative and positive:");
      BrobInt subPos2 = new BrobInt("-1000");
      System.out.println(subPos2.subtract(new BrobInt("1000")));
      System.out.println("======== Expected: -2000 ========");
      System.out.println();

      System.out.println("Subtract two negatives:");
      BrobInt subPos4 = new BrobInt("-1000");
      System.out.println(subPos4.subtract(new BrobInt("-500")));
      System.out.println("======== Expected: -500 ========");
      System.out.println();

      // Multiplication
      System.out.println("Multiplication by -2:");
      BrobInt multipTest1 = new BrobInt("1000");
      System.out.println(multipTest1.multiply(new BrobInt("-2")));
      System.out.println("======== Expected: -2000 ========");
      System.out.println();

      System.out.println("Multiplication by 1:");
      BrobInt multipTest2 = new BrobInt("1000");
      System.out.println(multipTest2.multiply(new BrobInt("1")));
      System.out.println("======== Expected: 1000 ========");
      System.out.println();

      System.out.println("Division by 1:");
      BrobInt divTest3 = new BrobInt("1000");
      System.out.println(divTest3.divide(new BrobInt("1")));
      System.out.println("======== Expected: 1000 ========");
      System.out.println();

      System.out.println("Division by large number:");
      BrobInt divTest2 = new BrobInt("10592837465982364587629384756928365982364958263845629837465983679845293874659834765982694856983267459836958634856203456923765923659836958673498658937658937629845638924659876835684765934659283498572364985723948756938476592387469583749587465983705423459628376459873649857693847659837269458723658764398698273964875693847659832746953874659873649875639847659837465984763258732400");
      System.out.println(divTest2.divide(new BrobInt("234756987364598763987456983765987369587694865928736495876398475693874659873649875683765983764598346587639583847569387465983765985092734695276349576329564978659836458972648956348975628946702765409856729837456928736987634985762984765827659876549527645293876587346587364892563845628964857263895763984765893267498576439856893762598347658972634987563984765893264897653987264958")));
      System.out.println("======== Expected: 500 ========");
      System.out.println();

      System.out.println("Division by -2:");
      BrobInt divTest4 = new BrobInt("1000");
      System.out.println(divTest4.divide(new BrobInt("-2")));
      System.out.println("======== Expected: -500 ========");
      System.out.println();

      System.out.println("Division by -1:");
      BrobInt divTest1 = new BrobInt("1000");
      System.out.println(divTest1.divide(new BrobInt("-1")));
      System.out.println("======== Expected: -1000 ========");
      System.out.println();

      BrobInt inputTest1 = new BrobInt("trash123");
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.exit( 0 );
  }
}
