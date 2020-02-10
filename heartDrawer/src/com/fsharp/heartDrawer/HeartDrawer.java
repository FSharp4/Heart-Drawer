/**
 * 
 */
package com.fsharp.heartDrawer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author FSharp4
 *
 */
public class HeartDrawer {

  /**
   * Program entry point
   * 
   * @param args [0] Size of heart in radius–pixels (optional); [1] name of textfile (optional)
   */
  public static void main(String[] args) {

    // Explanation
    if (args.length == 0) {
      System.out.println("HeartDrawer.jar");
      System.out.println(
          "Usage : 'java -jar HeartDrawer.jar [heart size in radius-pixels] [name of textfile]");
      System.out
          .println("Heart size must be specified if name is specified. (Using default size/name.)");
    }

    // Configure heart size
    int size;
    if (args.length >= 1) {
      size = Integer.parseInt(args[0]);
    } else {
      size = 7;
    }

    // Configure file name
    String name;
    if (args.length == 2) {
      name = args[1] + ".txt";
    } else {
      name = "heart.txt";
    }

    // Draw heart shape
    ArrayList<String> heartShape = null;
    try {
      heartShape = drawAHeart(size);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      System.err.println("Oops! No heart was drawn. Here's why: ");
      e.printStackTrace();
      System.exit(1);
    }

    // Configure message
    ArrayList<String> lines = new ArrayList<String>();
    Scanner sc = new Scanner(System.in);
    System.out.print("\rWould you like to write a message? (y/n): ");
    boolean userWrittenMessage = (sc.next().contentEquals("y")) ? true : false;
    if (userWrittenMessage) {

      // Explanation
      System.out.println("Type below to write a message.");
      System.out.println(
          "Press ENTER to start a new line. Try to keep your lines short to avoid sidescrolling!");
      System.out.println("Type 'END' to end your message.\r");

      // Get user message line-by-line
      while (true) {
        String nextLine = sc.nextLine();
        if (nextLine.contentEquals("END")) {
          break;
        } else {
          lines.add(nextLine);
        }
      }
    } else {
      System.out.println("Default message added");
      lines.add("Happy Valentine's Day!");
      lines.add("\r");
      lines.add("From: Me");
      lines.add("\r");
      lines.add("To: You! <3"); // bonus extra heart!
    }

    sc.close();

    // Put all lines into one array-list; start with heart shape
    ArrayList<String> fileLines = heartShape;
    fileLines.add("\r");
    for (String line : lines) {
      fileLines.add(line);
    }

    Path filePath = Paths.get(name);
    try {
      Files.write(filePath, fileLines, StandardCharsets.UTF_8);
    } catch (IOException e) {
      System.err.println("Failed to generate file.");
      e.printStackTrace();
      System.exit(1);
    }

    System.out.println("Done! Textfile can be found at " + filePath.toAbsolutePath().toString());
    System.exit(0);
  }

  /**
   * Draws a heart based on the equation (x^2 + (y - x^2/3) = r^2. Source:
   * <a href="https://www.quora.com/What-is-the-equation-that-gives-you-a-heart-on-the-graph">Here
   * (Second answer)</a>
   * 
   * @param heartSize Size of heart drawn in terms of pixel radius.
   * @return ArrayList containing strings representing a heart
   * @throws Exception Heart is too small
   */
  public static ArrayList<String> drawAHeart(int heartSize) throws Exception {
    // Check heart size
    if (heartSize < 5) {
      if (heartSize < 1) {
        throw new Exception("This heart is too small to draw...");
      }
      System.out.println("This heart will be pretty small...");
    } else if (heartSize > 16) {
      System.out.println("WOW! This heart is huge!");
    }

    //Find points on/near heart curve
    boolean[][] points = new boolean[(int) (heartSize * 2.5)][heartSize * 2 + 1];
    for (int y = 0; y < points.length; y++) {
      for (int x = 0; x < points[y].length; x++) {
        float offsetX = x - heartSize;
        float offsetY = y - heartSize;
        // referencing the equation
        float leftHandSide =
            square(offsetX) + square((float) (offsetY - Math.pow(square(offsetX), 1 / 3.0)));
        int rightHandSide = heartSize * heartSize;

        if (Math.abs(rightHandSide - leftHandSide) < heartSize) { // Size used as margin of 
          points[y][x] = true;                                    // inclusion
        }
      }
    }

    // Assemble these points into printable form
    ArrayList<String> heartShape = new ArrayList<String>();
    for (int y = points.length - 1; y >= 0; y--) {
      StringBuilder sb = new StringBuilder();
      for (int x = 0; x < points[y].length; x++) {
        if (points[y][x]) {
          sb.append(" * ");
        } else {
          sb.append("   ");
        }
      }

      String line = sb.toString();
      heartShape.add(line);
    }

    return heartShape;
  }

  /*
   * Squares a number.
   * For convenience.
   */
  private static float square(float number) {
    return number * number; // for convenience
  }
}
