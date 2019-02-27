import java.util.*;
import java.io.*;
public class Maze {
  private char[][] maze;
  private boolean animate;

  private static String test;

  /*Constructor loads a maze text file, and sets animate to false by default.
    1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)
      'S' - the location of the start(exactly 1 per file)
    2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!
    3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
  */
  public Maze(String filename) {
    test = "";
    File mazeFile = new File(filename);
    Scanner scan = new Scanner(mazeFile);
    String line = "";
    if (scan.hasNextLine()) {
      line = scan.nextLine();
    }
    int width = line.length();
    int count = 0;
    while(scan.hasNextLine()){
      count = count + 1;
      String newLine = scan.nextLine();
      test = test + newLine;
    }
    maze = new char[count][width];
  }


  public static void main(String[] args) {
    System.out.println(test);
  }
}


//count amt of lines
