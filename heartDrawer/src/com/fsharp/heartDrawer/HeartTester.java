/**
 * 
 */
package com.fsharp.heartDrawer;

/**
 * HeartTester.java Tests the HeartDrawer class.
 * 
 * @author FSharp4
 */
public class HeartTester {

  /**
   * Main entry point. Mostly for in-IDE testing. Included for convenience. Draws a heart with
   * radius 14 with default filename.
   * 
   * @param args Unused.
   */
  public static void main(String[] args) {
    // test heart
    String[] testArgs = {"14"};
    HeartDrawer.main(testArgs);
  }
}
