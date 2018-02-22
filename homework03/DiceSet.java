/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  File name     :  DiceSet.java
*  Purpose       :  Provides a class describing a set of dice
*  Author        :  M. McPartlan
*  Date          :  2017-02-09
*  Description   :  This class provides everything needed (pretty much) to describe a set of dice.  The
*                   idea here is to have an implementing class that uses the Die.java class.  Includes
*                   the following:
*                   public DiceSet( int k, int n );                  // Constructor for a set of k dice each with n-sides
*                   public int sum();                                // Returns the present sum of this set of dice
*                   public void roll();                              // Randomly rolls all of the dice in this set
*                   public void rollIndividual( int i );             // Randomly rolls only the ith die in this set
*                   public int getIndividual( int i );               // Gets the value of the ith die in this set
*                   public String toString();                        // Returns a stringy representation of this set of dice
*                   public static String toString( DiceSet ds );     // Classwide version of the preceding instance method
*                   public boolean isIdentical( DiceSet ds );        // Returns true iff this set is identical to the set ds
*                   public static void main( String[] args );        // The built-in test program for this class
*
*  Notes         :  Stolen from Dr. Dorin pretty much verbatim, then modified to show some interesting
*                   things about Java, and to add this header block and some JavaDoc comments.
*  Warnings      :  None
*  Exceptions    :  IllegalArgumentException when the number of sides or pips is out of range
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Revision Histor
*  ---------------
*            Rev      Date     Modified by:  Reason for change/modification
*           -----  ----------  ------------  -----------------------------------------------------------
*  @version 1.0.0  2017-02-09  B.J. Johnson  Initial writing and release
*  @version 1.1.0  2017-02-09  M. McPartlan  Filled out functions and made operational
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
public class DiceSet {

  /**
  * private instance data
  */
  private int count;
  private int sides;
  private boolean[] keptDice;
  private Die[] ds = null;

  // public constructor:
  /**
  * constructor
  * @param  count int value containing total dice count
  * @param  sides int value containing the number of sides on each die
  * @throws IllegalArgumentException if one or both arguments don't make sense
  * @note   parameters are checked for validity; invalid values throw "IllegalArgumentException"
  */
  public DiceSet( int count, int sides ) {
    if (count < 1) {
      throw new IllegalArgumentException("nCount parameter out of range.");
    }
    if (sides < 4) {
      throw new IllegalArgumentException("nSides parameter out of range.");
    }
    this.count = count;
    this.sides = sides;
    this.keptDice = new boolean[this.count];
    this.ds = new Die[this.count];
    for (int i = 0; i < this.count; i++) {
      this.keptDice[i] = false;
      this.ds[i] = new Die(this.sides);
    }
  }

  /**
  * @return the sum of all the dice values in the set
  */
  public int sum() {
    int sum = 0;
    for (int i = 0; i < this.count; i++) {
      sum += this.ds[i].getValue();
    }
    return sum;
  }

  /**
  * Randomly rolls all of the dice in this set
  *  NOTE: you will need to use one of the "toString()" methods to obtain
  *  the values of the dice in the set
  */
  public void roll() {
    for (int i = 0; i < this.count; i++) {
      if (this.keptDice[i] == false) {
        this.ds[i].roll();
        System.out.println("Rolled die " + i + ": " + ds[i].getValue());
      } else {
        System.out.println("Held die " + i + ": " + ds[i].getValue());
      }
    }
  }

  /**
  * Keeps the selected dice (indexed from 0) from being rolled again
  * @param dieIndex int of which die to keep
  * @return void.
  * @throws IllegalArgumentException if the index value is out of range.
  */
  public void hold(int dieIndex) {
    if (dieIndex >= this.count) {
      throw new IllegalArgumentException("dieIndex is out of range.");
    } else {
      this.keptDice[dieIndex] = true;
    }
  }

  /**
  * Keeps the selected dice (indexed from 0) from being rolled again
  * @param dieIndex int of which die to keep
  * @return void.
  * @throws IllegalArgumentException if the index value is out of range.
  */
  public void release(int dieIndex) {
    if (dieIndex >= this.count) {
      throw new IllegalArgumentException("dieIndex is out of range.");
    } else {
      this.keptDice[dieIndex] = false;
    }
  }

  /**
  * Randomly rolls a single die of the dice in this set indexed by 'dieIndex'
  * Input is indexed at 0!
  * @param  dieIndex int of which die to roll
  * @return void. Access value of new die with getIndividual( dieIndex ) method
  * @throws IllegalArgumentException if the index is out of range
  */
  public void rollIndividual( int dieIndex ) {
    if (dieIndex >= this.count) {
      throw new IllegalArgumentException("dieIndex is out of range.");
    } else {
      if (this.keptDice[dieIndex] == false) {
        this.ds[dieIndex].roll();
        System.out.println("Rolled die " + dieIndex + ": " + ds[dieIndex].getValue());
      } else {
        System.out.println("Die is held, cannot roll.");
      }
    }
  }

  /**
  * Gets the value of the die in this set indexed by 'dieIndex'
  * @param dieIndex int of which die to roll
  * @throws IllegalArgumentException if the index is out of range
  */
  public int getIndividual( int dieIndex ) {
    if (dieIndex >= this.count) {
      throw new IllegalArgumentException("dieIndex is out of range.");
    }
    return this.ds[dieIndex].getValue();
  }

  /**
  * @return Public Instance method that returns a String representation of the DiceSet instance
  */
  public String toString() {
    String result = "";
    for (int i = 0; i < this.count; i++) {
      result = result + i + "." + this.ds[i].toString() + "\n";
    }
    return result;
  }

  /**
  * @return Class-wide version of the preceding instance method
  */
  public static String toString( DiceSet ds ) {
    String result = "";
    for (int i = 0; i < ds.count; i++) {
      result = result + i + "." + ds.ds[i].toString() + "\n";
    }
    return result;
  }

  /**
  * @return  true iff this set is identical to the set passed as an argument
  */
  public boolean isIdentical( DiceSet ds ) {
    boolean identical = true;
    if (this.count == ds.count && this.sides == ds.sides) {
      for (int i = 0; i < ds.count; i++) {
        if (this.ds[i] != ds.ds[i]) {
          identical = false;
        }
      }
    } else {
      identical = false;
    }
    return identical;
  }
  /**
  * A little test main to check things out
  */
  public static void main( String[] args ) {
    DiceSet ds = new DiceSet(15, 6);
    System.out.println("Created ds");
    DiceSet ds2 = new DiceSet(5, 6);
    System.out.println("Created ds2");
    System.out.println("ds Sum: " + ds.sum());
    ds.roll();
    System.out.println("ds Sum: " + ds.sum());
    System.out.println("ds[3]: " + ds.getIndividual(3));
    ds.rollIndividual(2); // Index for user interface is + 1
    System.out.println("ds[3]: " + ds.getIndividual(3));
    System.out.println("ds Sum: " + ds.sum());
    for (int i = 0; i < ds.count; i++) {
      System.out.println("Die " + i + ": " + ds.getIndividual(i));
    }
    System.out.println("Holding die 4");
    ds.hold(4);
    ds.roll();
    for (int i = 0; i < ds.count; i++) {
      System.out.println("Die " + i + ": " + ds.getIndividual(i));
    }
    ds.release(4);
    ds.roll();
    for (int i = 0; i < ds.count; i++) {
      System.out.println("Die " + i + ": " + ds.getIndividual(i));
    }
    System.out.println(ds.toString());
    System.out.println("isIdentical: " + ds.isIdentical(ds2));
  }

}
