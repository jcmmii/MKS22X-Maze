import java.util.*;
import java.io.*;
public class Maze {
  private char[][] maze;
  private boolean animate;

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

  public Maze(String filename) throws FileNotFoundException {
    File mazeFile = new File(filename); //finds File
    Scanner scan = new Scanner(mazeFile); //scan: used to find dimensions of Maze
    Scanner scan2 = new Scanner(mazeFile); //scan2: used to fill in char[][] maze

    int height = 1; //because scan counts how many next lines there are, so assume there is one row by default
    int width = 0;  //even if there is no rows width is automatically 0

    int check = 0;
    while(scan.hasNextLine()){
      String findWidth = scan.nextLine();
      width = findWidth.length();
      check = 1;
      height = height + 1;
    }

    maze = new char[height][width];

    int indexRow = 0;
    while (scan2.hasNextLine()) {
      String val = scan2.nextLine();
      for (int i = 0; i < val.length(); i++) { //copies over chars
        maze[indexRow][i] = val.charAt(i); //outofbounds will never happen, as val.length() == width of maze always
      }
      indexRow++; //moves onto next row as scan2 moves onto next row
    }

    detectES();
  }

  /*Returns the string that represents the maze
  */
  public String toString() {
    String ret = "";
    for (int x = 0; x < maze.length; x++) {
      for (int y = 0; y < maze[x].length; y++) {
        ret = ret + maze[x][y];
      }
      ret = ret + "\n";
    }
    return ret;
  }

  //helper method detecting if maze has exactly 1 E and 1 S
  private void detectES() throws IllegalStateException {
    int checkE = 0;
    int checkS = 0;
    for (int x = 0; x < maze.length; x++) {
      for (int y = 0; y < maze[0].length; y++) {
        if (maze[x][y]=='E') checkE++;
        if (maze[x][y]=='S') checkS++;
      }
    }
    if (checkE != 1 || checkS != 1) throw new IllegalStateException();
  }


  private void wait(int millis){
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
        }
    }

   public void setAnimate(boolean b){
       animate = b;
   }

   public void clearTerminal(){
       //erase terminal, go to top left of screen.
       System.out.println("\033[2J\033[1;1H");
   }

  /*Wrapper Solve Function returns the helper function
    We can assume that the file exists and is valid as the constructor exits when file is not found
    Or when the number of E and S does not equate to 1
  */
  public int solve() {
    findS();
  }

  private void findS() {
    int XcorS, YcorS;
    for (int x = 0; x < maze.length; x++) {
      for (int y = 0; y < maze[0].length; y++) {
        if (maze[x][y] == 'S') {
          XcorS = x;
          YcorS = y;
          }
        }
      }
    maze[XcorS][YcorS] = '@';
  }




  public static void main(String[] args) {
    try {
      Maze test = new Maze("Maze2.txt");
      System.out.println(test);

    }
    catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
  }


//count amt of lines
