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
import java.lang.Math;


public class DynamicChangeMaker {

  /// These are the internal fields
  private Tuple _denominations;
  private Tuple _changeValue;
  private Tuple[][] _table;
  private int[] _indeces;

  /**
  *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
  *   and handles that accordingly;  it then checks to see if it's all valid digits.
  *  @param  value  String value to make into a BrobInt
  */
  public DynamicChangeMaker( int denominations[], int centValue ) {
    _indeces = new int[denominations.length];
    for (int i = 0; i < denominations.length; i++) {
      _indeces[i] = i;
    }
    _denominations = verifyDenominations(denominations);
    _changeValue = validateChangeValue(centValue);
    _table = new Tuple[this.getDenominations().length()][this.getChangeValue()];

    for (int i = 0; i < this.getDenominations().length(); i++) {
      System.out.print(_indeces[i]);
    }
    System.out.println();

    for( int i = 0; i < this.getDenominations().length(); i++ ) {
      for( int j = 0; j < this.getChangeValue(); j++ ) {
        int zeros[] = new int[this.getDenominations().length()];
        Arrays.fill(zeros, 0);
        _table[i][j] = new Tuple(zeros);
      }
    }
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to sort the input denominations array
  *  @return  int array of ordered denominations.
  *  @throws  IllegalArgumentException if inputs are invalid.
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public int[] sortDenominations( int denominationsIn[] ) {
    int sortingFlag = 1;
    while (sortingFlag == 1) {
      sortingFlag = 0;
      // Sorts by size least to greatest
      for (int i = denominationsIn.length - 1; i > 0; i--) {
        if (denominationsIn[i] < denominationsIn[i - 1]) {
          int tempDenom = denominationsIn[i - 1];
          int tempIndex = _indeces[i - 1];
          denominationsIn[i - 1] = denominationsIn[i];
          _indeces[i - 1] = _indeces[i];
          denominationsIn[i] = tempDenom;
          _indeces[i] = tempIndex;
          sortingFlag = 1;
        }
      }
    }
    return denominationsIn;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to sort the input denominations array
  *  @return  Tuple array of ordered denominations.
  *  @throws  IllegalArgumentException if inputs are invalid.
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public Tuple verifyDenominations( int denominationsIn[] ) {
    int denominationsOut[] = denominationsIn.clone();
    int input[] = sortDenominations(denominationsOut);
    // Check for repeats
    for (int i = 0; i < input.length - 1; i++) {
      for (int e = i + 1; e < input.length; e++) {
        if (input[i] == input[e]) {
          System.out.println("No repeated denominations allowed!");
          return new Tuple(new int[0]);
        }
      }
      if (input[i] < 0) {
        System.out.println("Cannot make change with negative value coins!");
        return new Tuple(new int[0]);
      } else if (input[i] == 0) {
        System.out.println("Cannot make change with 0.");
        return new Tuple(new int[0]);
      }
    }
    return new Tuple(input);
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to validate the desired value of change
  *  @return  int with value of desired change.
  *  @throws  IllegalArgumentException if input is invalid.
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public Tuple validateChangeValue( int change ) {
    if (change < 0) {
      System.out.println("Cannot make change for negative quantities!");
      return new Tuple(new int[0]);
    }
    Tuple output = new Tuple(1);
    output.setElement(0, change);
    return output;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to validate the desired value of change
  *  @return  int with value of desired change.
  *  @throws  IllegalArgumentException if input is invalid.
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public static Tuple makeChangeWithDynamicProgramming(int denominations[], int centValue) {
    DynamicChangeMaker cm = new DynamicChangeMaker(denominations, centValue);
    if (cm._denominations.isImpossible() || cm._changeValue.isImpossible()) {
      return new Tuple(new int[0]);
    }
    Tuple output = new Tuple(cm.getDenominations().length());

    for (int i = 0; i < cm.getDenominations().length(); i++) {
      for (int j = 0; j < cm.getChangeValue(); j++) {
        if (i == 0) {
          if (cm.getDenominations().getElement(0) == 1) {
            cm._table[i][j].setElement(0, j + 1);
          } else {
            if ((j + 1) % cm.getDenominations().getElement(0) == 0) {
              cm._table[i][j].setElement(0, (j + 1) / cm.getDenominations().getElement(0));
            }
          }
        } else {
          Tuple testTuple = new Tuple(cm.getDenominations().length());
          int remainder = j + 1;
          int total = 0;
          for (int k = i; k >= 0; k--) {
            testTuple.setElement(k, (int) Math.floor(remainder/cm.getDenominations().getElement(k)));
            remainder = remainder % cm.getDenominations().getElement(k);
            total = total + testTuple.getElement(k) * cm.getDenominations().getElement(k);
          }
          //System.out.println("Total: " + total);
          //System.out.println("J + 1: " + (j+1));
          if (total == j + 1) {
            // System.out.println("Writing");
            //System.out.println(testTuple.toString());
            cm._table[i][j] = testTuple;
          }
        }
        //System.out.print(cm._table[i][j].toString());
      }
      //System.out.println();
    }

    for (int i = 0; i < cm.getDenominations().length(); i++) {
      for (int j = 0; j < cm.getChangeValue(); j++) {
        if (cm._table[i][j].total() == 0) {
          cm._table[i][j] = new Tuple(new int[0]);
        }
      }
    }

    int smallest = 0;
    for (int i = 0; i < cm.getDenominations().length(); i++) {
      if (cm._table[smallest][cm.getChangeValue() - 1].isImpossible()) {
        if (smallest != cm.getDenominations().length() - 1) {
          smallest++;
        }
      } else {
        if (cm._table[i][cm.getChangeValue() - 1].total() < cm._table[smallest][cm.getChangeValue() - 1].total()) {
          if (cm._table[i][cm.getChangeValue() - 1].isImpossible() == false) {
            smallest = i;
          }
        }
      }
    }

    if (!cm._table[smallest][cm.getChangeValue() - 1].isImpossible()) {
      for (int i = 0; i < cm.getDenominations().length(); i++) {
        output.setElement(cm._indeces[i], cm._table[smallest][cm.getChangeValue() - 1].getElement(i));
      }
      return output;
    } else {
      return new Tuple(new int[0]);
    }

  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Method to convert the DynamicChangeMaker to a string
  *  @return  String with values of DynamicChangeMaker.
  *  @throws  None
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public String toString() {
    String output = "Denominations: ";
    output = output.concat(this._denominations.toString());
    output = output.concat("\nChange Value: " + this._changeValue.toString());
    return output;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Getter method for _denominations
  *  @return  Tuple _denominations.
  *  @throws  None
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public Tuple getDenominations() {
    return this._denominations;
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  Getter method for _changeValue;
  *  @return  Int _changeValue.
  *  @throws  None
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public int getChangeValue() {
    if (this._changeValue.isImpossible()) {
      return 0;
    }
    return this._changeValue.getElement(0);
  }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  *  the main method redirects the user to the test class
  *  @param  args  String array which contains command line arguments
  *  note:  we don't really care about these
  *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
  public static void main( String[] args ) {
    if (args.length != 2) {
      throw new IllegalArgumentException("Wrong number of args!");
    }

    for (int i = 0; i < args[0].length(); i++) {
      if ((int) args[0].charAt(i) < (int) '0' || (int) args[0].charAt(i) > (int) '9') {
        if ((int) args[0].charAt(i) != (int) ' ' && (int) args[0].charAt(i) != (int) ',') {
          throw new IllegalArgumentException("Argument must be an array of ints, followed by a single int (and NO NEGATIVES)!");
        }
      }
    }

    for (int i = 0; i < args[1].length(); i++) {
      if ((int) args[1].charAt(i) < (int) '0' || (int) args[1].charAt(i) > (int) '9') {
        if ((int) args[1].charAt(i) != (int) ' ' && (int) args[1].charAt(i) != (int) ',') {
          throw new IllegalArgumentException("Argument must be an array of ints, followed by a single int (and NO NEGATIVES)!");
        }
      }
    }

    String[] split = args[0].split(",");
    int[] array = new int[split.length];
    for (int i = 0; i < array.length; i++) {
      array[i] = Integer.parseInt(split[i]);
    }

    Tuple result = makeChangeWithDynamicProgramming(array, Integer.parseInt(args[1]));

    System.out.println("Results: ");
    for (int i = 0; i < result.length(); i++) {
      System.out.println(array[i] + " cent coins: " + result.getElement(i));
    }

    if (result.isImpossible()) {
      System.out.println("Impossible.");
    }

    System.exit( 0 );
  }
}