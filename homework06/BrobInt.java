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
    if (this.internalValue.equals("0")) {
      this.byteVersion = new byte[1];
      this.byteVersion[0] = 0;
    } else {
      this.byteVersion = this.validateDigits(this.internalValue);
    }
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
    // System.out.println("StartOfNumber: " + startOfNumber);
    for (int i = 0; i < internalString.length(); i++) {
      if ((int) internalString.charAt(i) < (int) '0' || (int) internalString.charAt(i) > (int) '9') {
        if (((int) internalString.charAt(i) != (int) '-' && (int) internalString.charAt(i) != (int) '+') || i != startOfNumber - 1) {
          throw new IllegalArgumentException("Input contains at least one non-numeric input OR a misplaced sign.");
        }
      }
      if (startOfNumber != 0) {
        if (internalString.charAt(startOfNumber - 1) == '-') {
          this.sign = 1;
          // System.out.println("Sign set to negative.");
        } else {
          this.sign = 0;
        }
      }

      if (this.sign == 1 && i == startOfNumber - 1) {
        byteArray[i + 1] = (byte) 0;
      } else {
        byteArray[i + 1] = (byte) ((int) internalString.charAt(i) - 48);
      }

    }
    return byteArray;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to compare a BrobInt passed as argument to this BrobInt
  *  @param  gint  BrobInt to add to this
  *  @return int   that is one of neg/0/pos if this BrobInt lessThan/equals/greaterThan the argument
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

  public int compareTo( BrobInt gint ) {
    if (this.sign == 0 && gint.sign == 1) {
      return 1;
    } else if (this.sign == 1 && gint.sign == 0) {
      return -1;
    } else if (this.sign == 0 && gint.sign == 0) {
      if ( byteVersion.length > gint.byteVersion.length ) {
        return 1;
      } else if( byteVersion.length < gint.byteVersion.length ) {
        return (-1);
      } else {
        for( int i = 0; i < byteVersion.length; i++ ) {
          if(byteVersion[i] > gint.byteVersion[i]) {
            return 1;
          } else if(byteVersion[i] < gint.byteVersion[i]) {
            return (-1);
          }
        }
      }
    } else if (this.sign == 1 && gint.sign == 1) {
      if ( byteVersion.length > gint.byteVersion.length ) {
        return -1;
      } else if( byteVersion.length < gint.byteVersion.length ) {
        return (1);
      } else {
        for( int i = 0; i < byteVersion.length; i++ ) {
          if(byteVersion[i] > gint.byteVersion[i]) {
            return -1;
          } else if(byteVersion[i] < gint.byteVersion[i]) {
            return (1);
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
    BrobInt output;

    // System.out.println("gint sign: " + gint.sign);
    // System.out.println("this sign: " + this.sign);

    if (gint.sign == 1 && this.sign == 1) {
      // Both are negative. Add magnitudes, final sign is negative.
      localSign = 1;
    }

    if ((gint.sign == 1 && this.sign == 0) || (gint.sign == 0 && this.sign == 1)) {
      // Call subtract function for the two magnitudes and handle signs in subtraction
      System.out.println(gint.sign);
      if (gint.sign == 1 && this.compareTo(gint.multiply(new BrobInt("-1"))) == -1) {
        // negative gint is greater than positive this, final sign must be negative
        // Subtract magnitudes, final negative sign
        System.out.println("Path one. ");
        gint.sign = 0;
        output = gint.subtract(this);
        gint.sign = 1;
        System.out.println(output.toString());
        output.sign = 1;
      } else if (this.sign == 1 && gint.compareTo(this.multiply(new BrobInt("-1"))) == 1) {
        // positive gint is greater than negative this
        System.out.println("Path two. ");
        this.sign = 0;
        output = gint.subtract(this);
        this.sign = 1;
        output.sign = 0;
      } else if (gint.sign == 1 && this.compareTo(gint.multiply(new BrobInt("-1"))) == 1) {
        // negative gint is less than positive this, final sign negative
        System.out.println("Path three. ");
        gint.sign = 0;
        output = gint.subtract(this);
        gint.sign = 1;
        output.sign = 1;
      } else {
        // if (this.sign == 1 && gint.compareTo(this.multiply(new BrobInt("-1"))) == -1)
        // positive gint is less than negative this
        System.out.println("Path four. ");
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

    return output;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to subtract the value of a BrobInt passed as argument to this BrobInt using bytes
  *  @param  gint         BrobInt to subtract from this
  *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public BrobInt subtract( BrobInt gint ) {
    byte subtractionResult[] = new byte[this.byteVersion.length];
    byte subtractant[] = new byte[this.byteVersion.length];

    for (int i = this.byteVersion.length - 1; i >= 0; i--) {
      subtractionResult[i] = this.byteVersion[i];
    }
    for (int i = gint.byteVersion.length - 1; i >= 0; i--) {
      subtractant[(this.byteVersion.length - gint.byteVersion.length) + i] = gint.byteVersion[i];
    }

    if ((gint.sign == 1 && this.sign == 0) || (gint.sign == 0 && this.sign == 1)) {
      throw new UnsupportedOperationException("\n Attempted mixed sign subtraction (not supported)");

    } else {

      System.out.print("subtractionResult: ");
      for (int i = 0; i < subtractionResult.length; i++) {
        System.out.print(subtractionResult[i]);
      }
      System.out.println();

      System.out.print("subtractant: ");
      for (int i = 0; i < subtractant.length; i++) {
        System.out.print(subtractant[i]);
      }
      System.out.println();


      for (int i = subtractionResult.length - 1; i >= 0; i--) {
        if (subtractionResult[i] - subtractant[i] < 0) {
          subtractionResult[i - 1] = (byte) (subtractionResult[i - 1] - 1);
          subtractionResult[i] = (byte) (subtractionResult[i] - subtractant[i] + 10);
        } else if (subtractionResult[i] - subtractant[i] >= 0) {
          subtractionResult[i] = (byte) (subtractionResult[i] - subtractant[i]);
        }
      }
    }

    System.out.println("subtractionResult (after algorithm): ");
    for (int i = 0; i < subtractionResult.length; i++) {
      System.out.print(subtractionResult[i]);
    }
    System.out.println();

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

    System.out.print("ByteString: ");
    System.out.println(byteString);

    BrobInt output = new BrobInt(byteString);

    return output;
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
      System.out.println("Returning (multiply) with sign: " + this.sign);
      return this;
    }

    byte localSign = 0;
    if ((this.sign == 1 && gint.sign == 0) || (this.sign == 0 && gint.sign == 1)) {
      localSign = 1;
    }
    System.out.println("Bypassing multiplication shortcut.");
    BrobInt index = new BrobInt("0");
    index.sign = localSign;
    BrobInt smallMultiplicand;
    BrobInt revolvingSum = new BrobInt("0");
    BrobInt largeMultiplicand;

    if (this.compareTo(gint) == -1) {
      // New number is larger than old
      smallMultiplicand = this;
      largeMultiplicand = gint;
    } else {
      // New number is smaller than old
      smallMultiplicand = gint;
      largeMultiplicand = this;
    }

    while (index.compareTo(smallMultiplicand) == -1) {
      revolvingSum = revolvingSum.add(largeMultiplicand);
      index = index.add(new BrobInt("1"));
    }

    return revolvingSum;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
  *  @param  gint         BrobInt to divide this by
  *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public BrobInt divide( BrobInt gint ) {
    byte localSign = 0;
    if ((this.sign == 1 && gint.sign == 0) || (this.sign == 0 && gint.sign == 1)) {
      localSign = 1;
    }

    if (gint.compareTo(this) == 0) {
      return new BrobInt("1");
    } else if (gint.compareTo(this) == 1) {
      return new BrobInt("0");
    } else {

      BrobInt index = new BrobInt("0");
      index.sign = localSign;
      BrobInt numerator = this;
      BrobInt denominator = gint;
      BrobInt revolvingSum = new BrobInt("0");

      while (numerator.compareTo(revolvingSum.add(denominator)) == 1) {
        revolvingSum = revolvingSum.add(denominator);
        index = index.add(new BrobInt("1"));
      }

      return index;
    }
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to get the remainder of division of this BrobInt by the one passed as argument
  *  @param  gint         BrobInt to divide this one by
  *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public BrobInt remainder( BrobInt gint ) {
    throw new UnsupportedOperationException( "\n         Sorry, that operation is not yet implemented." );
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
    // System.out.println("Starting Test 1");
    // String test1 = "-14412790971971066401509243150244084984950628410898207";
    // BrobInt testA = new BrobInt(test1);
    // System.out.println();
    // System.out.println(testA.toString());
    // String test2 = "-144127909719710664015092431502440849849506284148982076191826176553";
    // BrobInt testB = new BrobInt(test2);
    // System.out.println(testB.toString());
    // System.out.println();
    //
    // System.out.print(testB.subtract(testA));
    // System.out.println();
    // System.out.println("0144127909719725076806064402568842359092656528233967026820237074760");
    // System.out.println();
    try {

      System.out.println(new BrobInt("-1").compareTo(new BrobInt("1")));
      System.out.println(new BrobInt("-1").compareTo(new BrobInt("-2")));
      System.out.println(new BrobInt("-1").toString());
      System.out.println(new BrobInt("0").toString());
      System.out.println(new BrobInt("1").toString());

      System.out.println("Adding two positives:");
      BrobInt addPos1 = new BrobInt("1000");
      System.out.println(addPos1.add(new BrobInt("1000")));
      System.out.println("======== Expected: 2000 ========");
      System.out.println();

      System.out.println("Adding negative and positive:");
      BrobInt addPos2 = new BrobInt("-1000");
      System.out.println(addPos2.add(new BrobInt("1000")));
      System.out.println("Expected: 0");
      System.out.println();

      System.out.println("Adding positive and negative:");
      BrobInt addPos3 = new BrobInt("1000");
      System.out.println(addPos3.add(new BrobInt("-1000")));
      System.out.println("Expected: 0");
      System.out.println();

      System.out.println("Adding two negatives:");
      BrobInt addPos4 = new BrobInt("-1000");
      System.out.println(addPos4.add(new BrobInt("-1000")));
      System.out.println("======== Expected: -2000 ========");
      System.out.println();

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

      System.out.println("Division by -1:");
      BrobInt divTest1 = new BrobInt("1000");
      System.out.println(divTest1.divide(new BrobInt("-1")));
      System.out.println("======== Expected: -1000 ========");
      System.out.println();

      System.out.println("Division by 1:");
      BrobInt divTest2 = new BrobInt("1000");
      System.out.println(divTest2.divide(new BrobInt("1")));
      System.out.println("======== Expected: 1000 ========");
      System.out.println();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    // try {
    //
    //   System.out.println();
    //   BrobInt compareTest = new BrobInt("-1");
    //   BrobInt compareTest2 = new BrobInt("-1");
    //   System.out.println("CompareTo: " + compareTest.compareTo(new BrobInt("-1")));
    //   System.out.println();
    //
    //   System.out.println("Starting Test 4");
    //   String test4 = "15-605";
    //   BrobInt testD = new BrobInt(test4);
    //   for (int i = 0; i < testD.byteVersion.length; i++) {
    //     System.out.print(testD.byteVersion[i] + " ");
    //   }
    //   System.out.println();
    //   System.out.println(testD.toString());
    //   System.out.println();
    // } catch (Exception e) {
    //   System.out.println(e.getMessage());
    // }
    //
    // try {
    //   System.out.println("Starting Test 5");
    //   String test5 = "945 6095";
    //   BrobInt testE = new BrobInt(test5);
    //   for (int i = 0; i < testE.byteVersion.length; i++) {
    //     System.out.print(testE.byteVersion[i] + " ");
    //   }
    //   System.out.println();
    //   System.out.println(testE.toString());
    //   System.out.println();
    // } catch (Exception e) {
    //   System.out.println(e.getMessage());
    // }
    System.exit( 0 );
  }
}
