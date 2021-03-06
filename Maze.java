import java.util.*;
import java.io.*;
public class Maze {
  private char[][] maze;
  private boolean animate;

  private int startRow, startCol;

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

    while(scan.hasNextLine()){
      String findWidth = scan.nextLine();
      width = findWidth.length(); //takes in width of the next line, sets width of array to that as lengths are consistent
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

  /*Wrapper Solve function returns the helper function
    We can assume that the file exists and is valid as the constructor exits when file is not found
    Or when the number of E and S does not equate to 1
  */
  public int solve() {
    findS();
    return solve(startRow,startCol,0);
  }

  //helper method finding the location of S
  private void findS() {
    int rowS = -1;
    int colS = -1;
    for (int x = 0; x < maze.length; x++) {
      for (int y = 0; y < maze[0].length; y++) {
        if (maze[x][y] == 'S') {
          rowS = x;
          colS = y;
          }
        }
      }
    //instance variables hold the coordinates to be called in the solve() method
    startRow = rowS;
    startCol = colS;
  }

  /*Recursive Solve (helper) function
    A solved maze has a park marked with '@' from S to E
    Returns the number of @ symbols from S to E when the maze is solved
    Returns -1 when the maze has no solution

    Postconditions:
      S is replaced with '@' but the 'E' is NOT.
      All visited spots that were not part of the solution are changed to '.'
      ALl visited spots that are part of the solution are changed to '@'
  */
  private int solve(int row, int col, int count) {
    int temp;
    //animation of the function
    if (animate) {
            clearTerminal();
            System.out.println(this);
            wait(100);
    }
    //returns the number of @ signs when E is finally reached
    if(checkEnd(row,col)) return count;
    //as you go through the maze, you set every tile you go to into an @ sign
    maze[row][col] = '@';
    //checks the previous column
    if (checkSpot(row,col-1)) {
      temp = solve(row,col-1,count+1);
      if (temp != -1) return temp;
    }
    //checks the next row
    if (checkSpot(row+1,col)) {
      temp = solve(row+1,col,count+1);
      if (temp != -1) return temp;
    }
    //checks the previous row
    if (checkSpot(row-1,col)) {
      temp = solve(row-1,col,count+1);
      if (temp != -1) return temp;
    }
    //checks the next column
    if (checkSpot(row,col+1)) {
      temp = solve(row,col+1,count+1);
      if (temp != -1) return temp;
    }
    //if all four sides are not empty spaces then place a period
    //logic: place @ signs on each tile visited
    //       once you hit a wall, and you are surrounded by nonempty spaces, return -1
    //       the tile is then replaced by a .
    //       the previous tile that calls on the current tile via recursion: temp becomes -1
    //       because temp = -1, does not return anything -> goes through rest of cases
    //       it'll eventually reach the end case (if it isn't a fork), returns -1
    //       cycle repeats until it backtracks to the previous fork
    //       takes another path, repeats the cycle if it hits a dead end, if not, keeps going, once it hits E, returns count
    maze[row][col] = '.';
    return -1;
  }

  //helper method checking if the spot is valid
  private boolean checkSpot(int row, int col) {
    if (maze[row][col] == ' ' || maze[row][col] == 'E') {
      return true;
    }
    return false;
  }

  //helper method checking if E is reached
  private boolean checkEnd(int row, int col) {
    if (maze[row][col] == 'E') {
      setAnimate(false);
      return true;
    }
    return false;
  }

  //main method (testing)
  public static void main(String[] args) {
    try {
      Maze test = new Maze("Maze1.txt");
      test.setAnimate(true);
    //  System.out.println(test.solve());

      Maze test2 = new Maze("Maze2.txt");
      test2.setAnimate(true);
    //  System.out.println(test2.solve());

      Maze test3 = new Maze("Maze3.txt");
      test3.setAnimate(true);
    //  System.out.println(test3.solve());

      Maze test4 = new Maze("Maze4.txt");
      test4.setAnimate(true);
    //  System.out.println(test4.solve());

    }
    catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }
}
