/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  File name     :  StringStuff.java
*  Purpose       :  A file full of stuff to do with the Java String class
*  Author        :  B.J. Johnson
*  Author        :  Matt McPartlan
*  Date          :  2017-01-19
*  Description   :  This file presents a bunch of String-style helper methods.  Although pretty much
*                   any and every thing you'd want to do with Strings is already made for you in the
*                   Jave String class, this exercise gives you a chance to do it yourself [DIY] for some
*                   of it and get some experience with designing code that you can then check out using
*                   the real Java String methods [if you want]
*  Notes         :  None
*  Warnings      :  None
*  Exceptions    :  None
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*  Revision History
*  ----------------
*            Rev      Date     Modified by:  Reason for change/modification
*           -----  ----------  ------------  -----------------------------------------------------------
*  @version 1.0.0  2017-01-19  B.J. Johnson  Initial writing and release
*  @version 1.1.0  2017-01-22  M. McPartlan  Filled in methods to make the program actually work
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Set;
import java.util.LinkedHashSet;

public class StringStuff {

  public static String lettersUpper = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static String lettersLower = " abcdefghijklmnopqrstuvwxyz";

  /**
  * Method to determine if a string contains one of the vowels: A, E, I, O, U, and sometimes Y.
  * Both lower and upper case letters are handled.  In this case, the normal English rule for Y means
  * it gets included.
  *
  * @param s String containing the data to be checked for &quot;vowel-ness&quot;
  * @return  boolean which is true if there is a vowel, or false otherwise
  */
  public static boolean containsVowel( String s ) {
    s = s.toLowerCase();
    for (int i = 0; i < s.length(); i++) {
      switch (s.charAt(i)) {
        case 'a':
        case 'e':
        case 'i':
        case 'o':
        case 'u':
        return true;
      }
    }
    return false;
  }

  /**
  * Method to determine if a string is a palindrome.  Does it the brute-force way, checking
  * the first and last, second and last-but-one, etc. against each other.  If something doesn't
  * match that way, returns false, otherwise returns true.
  *
  * @param s String containing the data to be checked for &quot;palindrome-ness&quot;
  * @return  boolean which is true if this a palindrome, or false otherwise
  */
  public static boolean isPalindrome( String s ) {
    int frontEnd = 0;
    int backEnd = s.length() - 1;
    return isPalindromeRecursive(s, frontEnd, backEnd);
  }

  public static boolean isPalindromeRecursive( String s, int left, int right) {
    if (s.charAt(left) != s.charAt(right)) {
      return false;
    } else if (right - left <= 1) {
      return true;
    } else {
      return isPalindromeRecursive( s, left + 1, right - 1);
    }
  }

  /**
  * Method to return the characters in a string that correspond to the &quot;EVEN&quot; index
  * numbers of the alphabet.  The letters B, D, F, H, J, L, N, P, R, T, V, X, and Z are even,
  * corresponding to the numbers 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, and 26.
  *
  * @param s String containing the data to be parsed for &quot;even&quot; letters
  * @return  String containing the &quot;even&quot; letters from the input
  */
  public static String evensOnly( String s ) {
    String evens = "";
    for (int i = 0; i < s.length(); i++) {
      for (int e = 0; e < 27; e++) {
        if ((s.charAt(i) == lettersUpper.charAt(e) || s.charAt(i) == lettersLower.charAt(e)) && e % 2 == 0) {
          evens = evens + s.charAt(i);
        }
      }
    }
    return evens;
  }

  /**
  * Method to return the characters in a string that correspond to the &quot;ODD&quot; index
  * numbers of the alphabet.  The letters A, C, E, G, I, K, M, O, Q, S, U, W, and Y are odd,
  * corresponding to the numbers 1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, and 25.
  *
  * @param s String containing the data to be parsed for &quot;odd&quot; letters
  * @return  String containing the &quot;odd&quot; letters from the input
  */
  public static String oddsOnly( String s ) {
    String odds = "";
    for (int i = 0; i < s.length(); i++) {
      for (int e = 0; e < 27; e++) {
        if ((s.charAt(i) == lettersUpper.charAt(e) || s.charAt(i) == lettersLower.charAt(e)) && e % 2 == 1) {
          odds = odds + s.charAt(i);
        }
      }
    }
    return odds;
  }

  /**
  * Method to return the characters in a string that correspond to the &quot;EVEN&quot; index
  * numbers of the alphabet, but with no duplicate characters in the resulting string.
  *
  * @param s String containing the data to be parsed for &quot;even&quot; letters
  * @return  String containing the &quot;even&quot; letters from the input without duplicates
  */
  public static String evensOnlyNoDupes( String s ) {
    String evensIn = evensOnly(s);
    if (evensIn.length() > 0) {
      String evensOut = "" + evensIn.charAt(0);
      for (int i = 1; i < evensIn.length(); i++) {
        boolean toAdd = true;
        for (int e = 0; e < evensOut.length(); e++) {
          if (evensOut.charAt(e) == evensIn.charAt(i)) {
            toAdd = false;
          }
        }
        if (toAdd) {
          evensOut = evensOut + evensIn.charAt(i);
        }
      }
      return evensOut;
    } else {
      return "";
    }
  }

  /**
  * Method to return the characters in a string that correspond to the &quot;ODD&quot; index
  * numbers of the alphabet, but with no duplicate characters in the resulting string.
  *
  * @param s String containing the data to be parsed for &quot;odd&quot; letters
  * @return  String containing the &quot;odd&quot; letters from the input without duplicates
  */
  public static String oddsOnlyNoDupes( String s ) {
    String oddsIn = oddsOnly(s);
    if (oddsIn.length() > 0) {
      String oddsOut = "" + oddsIn.charAt(0);
      for (int i = 1; i < oddsIn.length(); i++) {
        boolean toAdd = true;
        for (int e = 0; e < oddsOut.length(); e++) {
          if (oddsOut.charAt(e) == oddsIn.charAt(i)) {
            toAdd = false;
          }
        }
        if (toAdd) {
          oddsOut = oddsOut + oddsIn.charAt(i);
        }
      }
      return oddsOut;
    } else {
      return "";
    }
  }

  /**
  * Method to return the reverse of a string passed as an argument
  *
  * @param s String containing the data to be reversed
  * @return  String containing the reverse of the input string
  */
  public static String reverse( String s ) {
    String invOut = "";
    if (s.length() > 0) {
      for (int i = s.length() - 1; i >= 0; i--) {
        invOut = invOut + s.charAt(i);
      }
    }
    return invOut;
  }

  /**
  * Main method to test the methods in this class
  *
  * @param args String array containing command line parameters
  */
  public static void main( String args[] ) {
    String blah = new String( "Blah blah blah" );
    String woof = new String( "BCDBCDBCDBCDBCD" );
    String pal1 = new String( "a" );
    String pal2 = new String( "ab" );
    String pal3 = new String( "aba" );
    String pal4 = new String( "amanaplanacanalpanama" );
    String pal5 = new String( "abba" );
    System.out.println( containsVowel( blah ) );
    System.out.println( containsVowel( woof ) );
    System.out.println( isPalindrome( pal1 ) );
    System.out.println( isPalindrome( pal2 ) );
    System.out.println( isPalindrome( pal3 ) );
    System.out.println( isPalindrome( pal4 ) );
    System.out.println( isPalindrome( pal5 ) );
    System.out.println( "evensOnly()        returns: " + evensOnly( "REHEARSALSZ" ) );
    System.out.println( "evensOnly()        returns: " + evensOnly( "REhearSALsz" ) );
    System.out.println( "evensOnlyNoDupes() returns: " + evensOnlyNoDupes( "REhearSALsz" ) );
    System.out.println( "oddsOnly()         returns: " + oddsOnly( "xylophones" ) );
    System.out.println( "oddsOnly()         returns: " + oddsOnly( "XYloPHonES" ) );
    System.out.println( "oddsOnlyNoDupes()  returns: " + oddsOnlyNoDupes( "XYloPHonES" ) );
    System.out.println( "reverse()          returns: " + reverse( "REHEARSALSZ" ) );
  }
}
