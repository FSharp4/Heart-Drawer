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

  /*
   * Allowable margin between observed point result and actual parts of the heart equation used.
   * (Allows us to have a heart :) )
   */

  public static void main(String[] args) {
    int size;
    if (args.length >= 1) {
      size = Integer.parseInt(args[0]);
    } else {
      size = 7;
    }

    String name;
    if (args.length == 2) {
      name = args[1] + ".txt";
    } else {
      name = "heart.txt";
    }

    ArrayList<String> heartShape = null;
    try {
      heartShape = drawAHeart(size);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      System.err.println("Oops! No heart was drawn. Here's why: ");
      e.printStackTrace();
      System.exit(1);
    }
    ArrayList<String> lines = new ArrayList<String>();
    Scanner sc = new Scanner(System.in);
    System.out.print("\rWould you like to write a message? (y/n): ");
    boolean userWrittenMessage = (sc.next().contentEquals("y")) ? true : false;

    if (userWrittenMessage) {
      System.out.println("Type below to write a message.");
      System.out.println(
          "Press ENTER to start a new line. Try to keep your lines short to avoid sidescrolling!");
      System.out.println("Type 'END' to end your message.\r");
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

    ArrayList<String> fileLines = heartShape;
    fileLines.add("\r");

    for (String line : lines) {
      fileLines.add(line);
    }

    Path filePath = Paths.get(name);
    try {
      Files.write(filePath, fileLines, StandardCharsets.UTF_8);
    } catch (IOException e) {
      // TODO Auto-generated catch block
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
   * @param size Size of heart drawn in terms of pixel radius.
   * @return
   * @throws Exception
   */
  public static ArrayList<String> drawAHeart(int size) throws Exception {
    if (size < 5) {
      if (size < 1) {
        throw new Exception("This heart is too small to draw...");
      }
      System.out.println("This heart will be pretty small...");
    } else if (size > 16) {
      System.out.println("WOW! This heart is huge!");
    }

    int heartRadius = size * size;
    boolean[][] points = new boolean[(int) (size * 2.5)][size * 2 + 1];

    for (int y = 0; y < points.length; y++) {
      for (int x = 0; x < points[y].length; x++) {
        float offsetX = x - size;
        float offsetY = y - size;
        // referencing the equation
        float leftHandSide =
            square(offsetX) + square((float) (offsetY - Math.pow(square (offsetX), 1/3.0)));
        int rightHandSide = size * size;

        if (Math.abs(rightHandSide - leftHandSide) < size) {
          points[y][x] = true;
        }
      }
    }

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

  private static float square(float number) {
    return number * number; // for convenience
  }

}
