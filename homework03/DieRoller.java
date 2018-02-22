/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  File name     :  MainProgLoopDemo.java
*  Purpose       :  Demonstrates the use of input from a command line for use with Yahtzee
*  Author        :  M. McPartlan
*  Date          :  2017-02-14
*  Description   :
*  Notes         :  None
*  Warnings      :  None
*  Exceptions    :  None
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Revision Histor
*  ---------------
*            Rev      Date     Modified by:  Reason for change/modification
*           -----  ----------  ------------  -----------------------------------------------------------
*  @version 1.0.0  2017-02-14  B.J. Johnson  Initial writing and release
*  @version 1.1.0  2018-02-20  M. McPartlan  Created main program logic
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class DieRoller {

  private static int SIDES = 6;
  private static int NUMBER_OF_DICE = 5;

  public static void main( String args[] ) {
    BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );
    System.out.println("Ender number of sides (4 or more): ");
    SIDES = getInputInteger(input, false);
    System.out.println("Ender number of dice (greater than 0): ");
    NUMBER_OF_DICE = getInputInteger(input, false);

    System.out.println( "\n   Welcome to the HighRoller!!\n" );
    showHelp();
    // This line uses the two classes to assemble an "input stream" for the user to type
    // text into the program
    System.out.println("Generating DiceSet: " + SIDES + "." + NUMBER_OF_DICE);
    DiceSet ds = new DiceSet(NUMBER_OF_DICE, SIDES);
    int highScore = 0;
    while( true ) {
      System.out.print( ">> " );
      String inputLine = null;
      try {
        inputLine = input.readLine();
        if(inputLine.length() > 0) {
          if( 'q' == inputLine.charAt(0) ) {
            break;
          } else if (inputLine.equals("roll")) {
            System.out.println("=======================");
            ds.roll();
            System.out.println("=======================");
          } else if (inputLine.equals("help")) {
            showHelp();
          } else if (inputLine.equals("hold")) {
            ds.hold(getInputInteger(input, true));
          } else if (inputLine.equals("release")) {
            ds.release(getInputInteger(input, true));
          } else if (inputLine.equals("rollInd")) {
            ds.rollIndividual(getInputInteger(input, true));
          } else if (inputLine.equals("show")) {
            System.out.println("=======================");
            System.out.println("Current values are \n" + ds.toString());
            System.out.println("Current score is " + ds.sum());
            System.out.println("=======================");
          } else if (inputLine.equals("score")) {
            System.out.println("=======================");
            System.out.println("Current score is " + ds.sum());
            System.out.println("=======================");
          } else if (inputLine.equals("high")) {
            System.out.println("=======================");
            System.out.println("High score is " + highScore);
            System.out.println("=======================");
          } else if (inputLine.equals("save")) {
            if (highScore <= ds.sum()) {
              highScore = ds.sum();
              System.out.println("Saved " + highScore + " as high score.");
            } else {
              System.out.println("Current score is less than high score.");
            }
          } else if (inputLine.equals("load")) {
            highScore = getInputInteger(input, false);
            System.out.println("Loaded " + highScore + " as high score.");
          } else {
            System.out.println("Unrecognized command.");
          }
        }
      }
      catch( IOException ioe ) {
        System.out.println( "Caught IOException" );
      }
    }
  }

  private static void showHelp() {
    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    System.out.println("++++++++++++++++ AVAILABLE COMMANDS ++++++++++++++++");
    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    System.out.println("++ Use 'roll' to roll all dice.                   ++");
    System.out.println("++ Use 'rollInd' to roll all dice.                ++");
    System.out.println("++ Use 'score' to see the sum of all dice.        ++");
    System.out.println("++ Use 'save' to save score as high score.        ++");
    System.out.println("++ Use 'high' to see the current high score.      ++");
    System.out.println("++ Use 'quit' to exit the program.                ++");
    System.out.println("++ Use 'hold' to keep a die's value.              ++");
    System.out.println("++ Use 'release' to keep a die's value.           ++");
    System.out.println("++ Use 'show' to show the current dice values.    ++");
    System.out.println("++ Use 'load' to load a previous high score.      ++");
    System.out.println("++ Use 'help' to repeat this menu.                ++");
    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
  }

  private static int getInputInteger(BufferedReader input, boolean isIndex) {
    String inputBuffer = "";
    boolean validInput = false;
    while (validInput == false) {
      validInput = true;
      System.out.print("Input: ");
      try{
        inputBuffer = input.readLine();
      }catch(IOException e){
        e.printStackTrace();
      }
      if (inputBuffer.length() > 0) {
        for (int i = 0; i < inputBuffer.length(); i++) {
          if (!((int)inputBuffer.charAt(i) > 47 && (int)inputBuffer.charAt(i) < 58 )) {
            System.out.println("Invalid character at letter " + (i + 1) + ".");
            validInput = false;
          }
        }
        if (isIndex == true && validInput == true) {
          if (Integer.parseInt(inputBuffer) < 0 || Integer.parseInt(inputBuffer) >= NUMBER_OF_DICE) {
            validInput = false;
            System.out.println("Index out of bounds.");
          }
        }
      } else {
        validInput = false;
      }
    }
    return Integer.parseInt(inputBuffer);
  }
}
